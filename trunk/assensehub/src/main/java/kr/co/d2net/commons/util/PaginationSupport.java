package kr.co.d2net.commons.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PaginationSupport<T> {

	//한페이지당 게시물 수
	public final static int PAGESIZE = 10;    

	private int pageSize = PAGESIZE;    

	//최대 페이지 수
	private int pageIndexCount = 10;    

	private int pageCount ;    

	private List<T> items = new LinkedList<T>();

	//전체 페이지 수
	private int totalCount;    

	private int[] indexs = new int[0];    

	//시작페이지
	private int startIndex = 1;    

	public PaginationSupport() {
	}
	
	public PaginationSupport(List<T> items,int totalCount){    
		setPageSize(PAGESIZE);    
		setTotalCount(totalCount);    
		setItems(items);    
		setStartIndex(1);    
	}    

	public PaginationSupport(List<T> items,int totalCount,int startIndex){    
		setPageSize(PAGESIZE);    
		setTotalCount(totalCount);    
		setItems(items);    
		setStartIndex(startIndex);    
	}    

	public PaginationSupport(List<T> items,int totalCount,int startIndex,int pageSize){    
		setPageSize(pageSize);    
		setTotalCount(totalCount);    
		setItems(items);    
		setStartIndex(startIndex);    
	}    

	public PaginationSupport(List<T> items,int totalCount,int startIndex,int pageSize,int pageIndexCount){    
		setPageIndexCount(pageIndexCount);    
		setPageSize(pageSize);    
		setTotalCount(totalCount);    
		setItems(items);    
		setStartIndex(startIndex);          
	}    

	public int[] getIndexs() {return indexs;}    
	public void setIndexs(int[] indexs) {this.indexs = indexs;}    

	public List<T> getItems() {return items;}    
	public void setItems(List<T> items) {this.items = items;}    

	public int getPageSize() {return pageSize;}    
	public void setPageSize(int pageSize) {this.pageSize = pageSize;}    

	public int getStartIndex() {return startIndex;}    
	public void setStartIndex(int startIndex) {    
		if(startIndex >= this.pageCount) this.startIndex = this.pageCount;    
		else if(startIndex<=0) this.startIndex = 1;    
		else this.startIndex = startIndex;    
		
		if(getPageIndexCount()>this.pageCount){    
			setPageIndexCount(this.pageCount);    
		}    

		indexs = new int[getPageIndexCount()];    
		int istart = this.startIndex-getPageIndexCount()/2+(getPageIndexCount()%2>0?0:1);  
		int iend = this.startIndex+getPageIndexCount()/2;
		if(istart<=0){    
			istart =1;    
			iend = getPageIndexCount();    
		}    
		if(iend>this.pageCount){    
			iend = this.pageCount;    
			istart = this.pageCount - getPageIndexCount()+1;    
		}    
		for (int i = 0; i < iend-istart+1; i++) {       
			indexs[i]= istart+i;    
		}     
	}    

	public int getTotalCount() {return totalCount;}    
	public void setTotalCount(int totalCount) {    
		if(totalCount>0){    
			this.totalCount = totalCount;    
			this.pageCount = totalCount/pageSize + (totalCount%pageSize>0?1:0);    
		}else{    
			this.totalCount = 0;    
		}    
	}    

	public int getPageCount() {return pageCount;}    
	public void setPageCount(int pageCount) {this.pageCount = pageCount ;}    

	public int getPageIndexCount() {return pageIndexCount;}    
	public void setPageIndexCount(int pageIndexCount) {this.pageIndexCount = pageIndexCount;}    

	public int getNextIndex() {       
		int nextIndex = getStartIndex() + 1;     
		if (nextIndex > pageCount)       
			return pageCount;       
		else      
			return nextIndex;       
	}       

	public int getPreviousIndex() {       
		int previousIndex = getStartIndex() - 1;       
		if (previousIndex <= 0)       
			return 1;       
		else      
			return previousIndex;       
	}    

	public int getFirstIndex(){    
		return 1;    
	}    

	public int getLastIndex(){    
		return getPageCount();    
	}    


	public static void main(String[] args) {    

		List<String> items = new ArrayList<String>();    
		for (int i = 0; i < 10; i++) {    
			items.add("------------->" + i);    
		}    

		int totalCount = 200;    
		int pageSize = 10;    
		int startIndex = 2;    
		int pageIndexCount = 10;    

		PaginationSupport<String> ps = new PaginationSupport<String>(items, totalCount,    
				startIndex, pageSize, pageIndexCount);    

		for (int i = 0; i < ps.getItems().size(); i++) {    
			System.out.println(ps.getItems().get(i));    
		}    
		System.out.println("전체 아이템 수: " + ps.getTotalCount());    
		System.out.println("시작 페이지 수: " + ps.getStartIndex());    
		System.out.println("전체 페이지 수: " + ps.getPageCount());    
		System.out.println("시작 페이지" + ps.getFirstIndex());    
		System.out.println("이전 페이지" + ps.getPreviousIndex());    

		for (int i = 0; i < ps.getIndexs().length; i++) {    
			System.out.println(ps.getIndexs()[i]);    
		}    

		System.out.println("다음 페이지 수" + ps.getNextIndex());    
		System.out.println("마지막 페이지 수" + ps.getLastIndex());    

	}
}
