package kr.co.d2net.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import kr.co.d2net.dto.Workflow;
import kr.co.d2net.utils.Utility;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class FileMoveServiceImpl implements FileMoveService {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MessageSource messageSource;


	/**
	 * mp4파일을 copy하는 method.
	 */
	@Override
	public void contentCopy(Workflow workflow) throws Exception {

		if(StringUtils.isBlank(workflow.getVideoLocation()))
			throw new Exception("VideoLocation is blank.");

		if(StringUtils.isBlank(workflow.getWatchFolder()))
			throw new Exception("WatchFolder is blank.");

		File filein  = null;
		File fileout = null;

		BufferedInputStream  fin  = null;
		BufferedOutputStream fout = null;
		
		String tmpWatchFolder = workflow.getWatchFolder().replaceAll("\\\\", "/");

		String tmpCcFileNm = workflow.getCcFileName();
		tmpCcFileNm = tmpCcFileNm.substring(tmpCcFileNm.indexOf(""), tmpCcFileNm.lastIndexOf("."));

		try {
			if(Utility.getOs().equals("win")){
				//KBS 요청사항 VideoLocation에 드라이브 정보가 올수도 있고 안올수도 있음 - 20141121
				//KBS VideoLocation에 드라이브 정보가 무조건 오게 되었음.
				filein  = new File(workflow.getVideoLocation());
				//임시파일저장을 위해 .tmp로 저장함.
				fileout = new File(tmpWatchFolder, workflow.getProgramId() + "_" + tmpCcFileNm + ".mp4.tmp");
			}else{
				filein  = new File("");
				fileout = new File("");
			}

			if(filein.exists()){
				if(!fileout.getParentFile().exists()) 
					FileUtils.forceMkdir(fileout.getParentFile());

				int r = 0;
				byte[] b = new byte[10240];

				fin  = new BufferedInputStream(new FileInputStream(filein));
				fout = new BufferedOutputStream(new FileOutputStream(fileout));

				while( (r = fin.read(b)) != -1) {
					fout.write(b, 0, r);
				}

				fout.close();

				//File convertFileNm = new File(workflow.getWatchFolder(), workflow.getProgramId() + "_" + tmpCcFileNm + ".mp4");
				File convertFileNm = new File(tmpWatchFolder, workflow.getProgramId() + "_" + tmpCcFileNm + ".mp4");
				fileout.renameTo(convertFileNm);

			}else{
				throw new Exception("File not found.");
			}
		} catch (Exception e) {
			logger.error("Exception",e);
			throw new Exception("contentCopy error");
		} finally {
			if(fin != null) fin.close();
			if(fout != null) fout.close();
		}

	}



	/**
	 * smi파일을 url 다운로드 한뒤, copy하는 method. 
	 */
	@Override
	public void captionDownload(Workflow workflow) throws Exception {
		if(StringUtils.isBlank(workflow.getCcFileName())) {
			throw new Exception("ccFileName is blank.");
		}

		InputStream webIS = null;
		FileOutputStream fo = null;
		try {
			try {
				URL url = new URL(workflow.getCcLocation()); //image on server
				webIS = url.openStream();
			} catch (Exception e) {
				logger.error("Exception",e);
				throw new Exception("caption url can't read");
			}

			if(webIS != null) {
				File fileObj = new File(workflow.getWatchFolder(), workflow.getProgramId()  + "_" + workflow.getCcFileName());

				if(!fileObj.getParentFile().exists())
					FileUtils.forceMkdir(fileObj.getParentFile());

				fo = new FileOutputStream(fileObj);

				try {
					int c=0;
					do {
						c = webIS.read();
						if(c!=-1) fo.write((byte)c);
					}while(c!=-1);
				} catch (Exception e) {
					throw new Exception("ParentFile path can't read");
				}

			}
		} catch (Exception e) {
			logger.error("Exception",e);
			throw new Exception("captionDownload error");
		} finally {
			if(webIS != null) webIS.close();
			if(fo != null) fo.close();
		}

	}

}
