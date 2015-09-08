package kr.co.d2net.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import kr.co.d2net.commons.SpringApplication;
import kr.co.d2net.commons.dto.Content;
import kr.co.d2net.commons.dto.Status;
import kr.co.d2net.commons.dto.Workflow;
import kr.co.d2net.commons.exceptions.ServiceException;
import kr.co.d2net.commons.utils.CmsRequestService;
import kr.co.d2net.commons.utils.XmlStream;
import kr.co.d2net.ftp.FtpTransferControl;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.ListenerList;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransferJobManager {

	private static Display display;
	private static WorkerThread thread;
	final static Logger logger = LoggerFactory.getLogger(TransferJobManager.class);

	private static class WorkerThread extends Thread {

		public final static String SEC = "second";
		public final static String MIN = "minute";
		private Display display;
		private ListenerList transferListeners;
		private ListenerList waitingListeners;
		private ListenerList completedListeners;
		private SpringApplication springApplication;
		private CmsRequestService cmsRequestService;
		private XmlStream xmlStream;

		public WorkerThread(Display display) {
			this.display = display;

			transferListeners = new ListenerList();
			waitingListeners = new ListenerList();
			completedListeners = new ListenerList();
			
			if(springApplication == null) {
				springApplication = SpringApplication.getInstance();
				xmlStream = (XmlStream)springApplication.get("xmlStream");
			}
		}

		public void addTransferPropertyChangeListener(IPropertyChangeListener listener) {
			transferListeners.add(listener);
		}
		public void addWaitingPropertyChangeListener(IPropertyChangeListener listener) {
			waitingListeners.add(listener);
		}
		public void addCompletedPropertyChangeListener(IPropertyChangeListener listener) {
			completedListeners.add(listener);
		}

		@SuppressWarnings("unused")
		public void removePropertyChangeListener(IPropertyChangeListener listener) {
			transferListeners.remove(listener);
			waitingListeners.remove(listener);
			completedListeners.remove(listener);
		}

		protected void transferPropertyChange(String propertyName, Object oldValue, Object newValue) {
			Object[] listeners = this.transferListeners.getListeners();
			for (int i = 0; i < listeners.length; ++i) {
				((IPropertyChangeListener)listeners[i]).propertyChange(
						new PropertyChangeEvent(this, propertyName, oldValue, newValue)
				);
			}
		}
		protected void waitingPropertyChange(String propertyName, Object oldValue, Object newValue) {
			Object[] listeners = this.waitingListeners.getListeners();
			for (int i = 0; i < listeners.length; ++i) {
				((IPropertyChangeListener)listeners[i]).propertyChange(
						new PropertyChangeEvent(this, propertyName, oldValue, newValue)
				);
			}
		}
		protected void completedPropertyChange(String propertyName, Object oldValue, Object newValue) {
			Object[] listeners = this.completedListeners.getListeners();
			for (int i = 0; i < listeners.length; ++i) {
				((IPropertyChangeListener)listeners[i]).propertyChange(
						new PropertyChangeEvent(this, propertyName, oldValue, newValue)
				);
			}
		}

		public void run() {
			
			cmsRequestService = (CmsRequestService)springApplication.get("cmsReqService");
			
			while (true) {
				synchronized (this) {
					try {
						this.wait(1000);
					}
					catch (InterruptedException ex) {
						break;
					}
				}
				display.asyncExec(new Runnable() {
					public void run() {

						if(!FtpTransferControl.currTransfer.isEmpty()) {
							Map<String, Object> currList = new HashMap<String, Object>();
							boolean completed = false;
							while(true) {
								Status status = FtpTransferControl.currTransfer.get();
								
								if(status.getJobState().equals("S") || status.getJobState().equals("C") || status.getJobState().equals("E")) {
									completed = true;
								}
								if(logger.isDebugEnabled()) {
									logger.debug("state 	: "+status.getJobState());
									logger.debug("completed : "+completed);
								}
								if(status.getJobState().equals("S") || status.getJobState().equals("I")) {
									currList.put(status.getEqId(), status);
								} else {
									currList.remove(status.getEqId());
								}
								
								if(FtpTransferControl.currTransfer.isEmpty()) {
									break;
								}
							}
							
							// 전송중인 작업이 있을경우 갱신한다.
							if(!currList.isEmpty())
								transferPropertyChange(SEC, null, currList);

							/*
							 * 전송에러 및 완료가 되었을경우 대기 및 완료의 리스트를 갱신한다.
							 */
							if(completed) {

								/* 대기중인 목록 업데이트 */
								List<Content> waitList = new ArrayList<Content>();
								try {
									String xml = cmsRequestService.findTransfers("<workflow><gubun>W</gubun></workflow>");
									Workflow workflow = (Workflow)xmlStream.fromXML(xml);
									
									waitList = workflow.getContentList();
									waitingPropertyChange(SEC, null, waitList);
								} catch (ServiceException e) {
									e.printStackTrace();
								}


								/* 완료된 목록 업데이트 */
								List<Content> compList = new ArrayList<Content>();
								try {
									String xml = cmsRequestService.findTransfers("<workflow><gubun>C</gubun></workflow>");
									Workflow workflow = (Workflow)xmlStream.fromXML(xml);
									
									compList = workflow.getContentList();
									completedPropertyChange(SEC, null, compList);
								} catch (ServiceException e) {
									e.printStackTrace();
								}
								
							}
						}
					}
				});
			}
		}
	}

	public static void main(String[] args) {
		display = new Display();
		Shell shell = new Shell(display);
		shell.setSize(800, 600);
		mainframe(shell);

		TransferJobManager transferJobManager = new TransferJobManager();
		transferJobManager.groupLayout(display, shell, SWT.NONE);

		shell.pack();
		shell.open();

		thread.start();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

		thread.interrupt();
		try {
			thread.join();
		}
		catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		display.dispose();
	}

	public void groupLayout(Display display, Composite parent, int style) {

		Group topGroup;
		Group leftGroup;
		Group rightGroup;

		topGroup = new Group(parent, style);
		topGroup.setText("진행중인 작업현황");
		topGroup.setBounds(10, 10, 765, 110);

		TransferTable transferTable = new TransferTable(topGroup, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION | SWT.SCROLL_LINE);

		leftGroup = new Group(parent, style);
		leftGroup.setText("대기중인 작업현황");
		leftGroup.setBounds(10, 130, 375, 425);
		WaitingTable waitingTable = new WaitingTable(leftGroup, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION);

		rightGroup = new Group(parent, style);
		rightGroup.setText("완료된 작업현황");
		rightGroup.setBounds(400, 130, 375, 425);
		CompletedTable completedTable = new CompletedTable(rightGroup, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION);

		// Listener 등록
		thread = new WorkerThread(display);
		thread.addTransferPropertyChangeListener(transferTable);
		thread.addWaitingPropertyChangeListener(waitingTable);
		thread.addCompletedPropertyChangeListener(completedTable);
	}

	private static class TransferTable implements IPropertyChangeListener {
		private Table table;
		private TableColumn column1;
		private TableColumn column2;
		private TableColumn column3;
		private TableColumn column4;
		private TableColumn column5;

		public TransferTable(Composite parent, int style) {
			table = new Table(parent, style);
			table.setBounds(10, 20, 745, 85);
			table.setHeaderVisible(true);
			table.setLinesVisible(false);

			column1 = new TableColumn(table, SWT.CENTER);
			column1.setText("장비ID");
			column1.setWidth(150);

			column2 = new TableColumn(table, SWT.CENTER);
			column2.setText("프로그램명");
			column2.setWidth(170);

			column3 = new TableColumn(table, SWT.CENTER);
			column3.setText("사업자명");
			column3.setWidth(100);

			column4 = new TableColumn(table, SWT.CENTER);
			column4.setText("프로파일명");
			column4.setWidth(200);

			column5 = new TableColumn(table, SWT.CENTER);
			column5.setText("진행현황");
			column5.setWidth(100);
		}

		// 이벤트를 받았을때 실행
		public void propertyChange(PropertyChangeEvent event) {
			Map<String, Object> statues = (Map<String, Object>)event.getNewValue();

			if(!display.isDisposed()) {
				table.removeAll();
			}
			
			Iterator<String> it = statues.keySet().iterator();
			while(it.hasNext()) {
				String key = it.next();
				final Status status = (Status)statues.get(key);
				
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(new String[] {
						status.getEqId(), status.getPgmNm(),  status.getCompany(), status.getProFlNm()
				});

				table.addListener(SWT.PaintItem, new Listener() {
					public void handleEvent(Event event) {
						if (event.index == 4) {

							GC gc = event.gc;

							Color foreground = gc.getForeground();
							Color background = gc.getBackground();
							gc.setForeground(display.getSystemColor(SWT.COLOR_RED));
							gc.setBackground(display.getSystemColor(SWT.COLOR_YELLOW));
							int width = (column5.getWidth() - 1) * status.getProgress() / 100;
							gc.fillGradientRectangle(event.x, event.y, width, event.height, true);
							Rectangle rect2 = new Rectangle(event.x, event.y, width - 1, event.height - 1);
							gc.drawRectangle(rect2);
							gc.setForeground(display.getSystemColor(SWT.COLOR_LIST_FOREGROUND));
							/*
							String text = transfer.getProgress() + "%";
							Point size = event.gc.textExtent(text);
							int offset = Math.max(0, (event.height - size.y) / 2);
							gc.drawText(text, event.x + 2, event.y + offset, true);
							*/
							gc.setForeground(background);
							gc.setBackground(foreground);

							if(status.getProgress()==100 && status.getJobState().equals("C")) {
								gc.setForeground(display.getSystemColor(SWT.COLOR_WHITE));
								gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
								gc.drawRectangle(rect2);
								gc.fillGradientRectangle(event.x, event.y, width, event.height, false);
							}
						}
					}
				});
			}
		}
	}

	private static class WaitingTable implements IPropertyChangeListener {
		private Table table;
		private TableColumn column1;
		private TableColumn column2;
		private TableColumn column3;
		private TableColumn column4;

		public WaitingTable(Composite parent, int style) {
			table = new Table(parent, style);
			table.setBounds(10, 20, 354, 400);
			table.setHeaderVisible(true);
			table.setLinesVisible(true);

			column1 = new TableColumn(table, SWT.CENTER);
			column1.setText("프로그램명");
			column1.setWidth(150);
			
			column2 = new TableColumn(table, SWT.CENTER);
			column2.setText("사업자명");
			column2.setWidth(100);
			
			column3 = new TableColumn(table, SWT.CENTER);
			column3.setText("Bit");
			column3.setWidth(50);

			column4 = new TableColumn(table, SWT.CENTER);
			column4.setText("코덱");
			column4.setWidth(50);
		}

		// 이벤트를 받았을때 실행
		public void propertyChange(PropertyChangeEvent event) {
			final List<Content> waitList = (List<Content>)event.getNewValue();

			if(!display.isDisposed()) {
				table.removeAll();
			}
			for(Content content : waitList) {
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(new String[] {
						content.getPgmNm(), content.getCompany(), content.getBitRate(), content.getProFlnm()
				});
			}
		}
	}

	private static class CompletedTable implements IPropertyChangeListener {
		private Table table;
		private TableColumn column1;
		private TableColumn column2;
		private TableColumn column3;
		private TableColumn column4;

		public CompletedTable(Composite parent, int style) {
			table = new Table(parent, style);
			table.setBounds(10, 20, 354, 400);
			table.setHeaderVisible(true);
			table.setLinesVisible(true);

			column1 = new TableColumn(table, SWT.CENTER);
			column1.setText("프로그램명");
			column1.setWidth(150);
			
			column2 = new TableColumn(table, SWT.CENTER);
			column2.setText("사업자명");
			column2.setWidth(100);
			
			column3 = new TableColumn(table, SWT.CENTER);
			column3.setText("Bit");
			column3.setWidth(50);

			column4 = new TableColumn(table, SWT.CENTER);
			column4.setText("코덱");
			column4.setWidth(50);
		}

		// 이벤트를 받았을때 실행
		public void propertyChange(PropertyChangeEvent event) {
			final List<Content> compList = (List<Content>)event.getNewValue();

			if(!display.isDisposed()) {
				table.removeAll();
			}
			for(Content content : compList) {
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(new String[] {
						content.getPgmNm(), content.getCompany(), content.getBitRate(), content.getProFlnm()
				});
			}
		}
	}

	public static void mainframe(final Shell shell) {
		shell.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent e) {
				FtpTransferControl.cancelled = true;
			}
		});
	}

}
