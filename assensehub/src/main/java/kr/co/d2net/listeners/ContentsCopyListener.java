package kr.co.d2net.listeners;

import java.io.File;

import kr.co.d2net.commons.events.CopyEventArgs;
import kr.co.d2net.commons.events.IEventHandler;
import kr.co.d2net.dto.TransferHisTbl;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component(value="contentsCopyListener")
public class ContentsCopyListener implements IEventHandler<CopyEventArgs> {
	
	private Log logger = LogFactory.getLog(ContentsCopyListener.class);

	@Override
	public void eventReceived(Object sender, CopyEventArgs event) {
		try {
			TransferHisTbl transferHisTbl = event.getTransferHisTbl();
			File srcFile = new File("/mnt2"+transferHisTbl.getFlPath(), transferHisTbl.getOrgFileNm()+"."+transferHisTbl.getFlExt());
			if(logger.isInfoEnabled()) {
				logger.info("caption mp3 file location: "+srcFile.getAbsolutePath());
			}
			/*
			 * 2015-02-12
			 * 파일을 저장하는 동안은 확장자를 .tmp로 생성하고
			 * 완료 후 .mp3로 변경하도록 함.
			 */
			if(srcFile.exists()) {
				File destTemp = new File("/mnt2"+"/caption", transferHisTbl.getWrkFileNm()+".tmp");
				FileUtils.copyFile(srcFile, destTemp);

				Thread.sleep(3000L);
				
				if(destTemp.exists()) {
					if(logger.isInfoEnabled()) {
						logger.info("the mp3 file is copied. path: "+srcFile.getAbsolutePath());
					}
					
					try {
						File destFile = new File("/mnt2"+"/caption", transferHisTbl.getWrkFileNm()+"."+transferHisTbl.getFlExt());
						destTemp.renameTo(destFile);
					} catch (Exception e) {
						logger.error("The tmp file cann't rename to mp3.");
						logger.error(e);
					}
				} else {
					logger.error("tmp file not exist - "+destTemp.getAbsolutePath());
				}
			}
		} catch (Exception e) {
			logger.error("caption file copy error", e);
		}
		
	}

}
