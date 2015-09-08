package kr.co.d2net.service;

import kr.co.d2net.dto.Workflow;

public interface FileMoveService {
	public void contentCopy(Workflow workflow) throws Exception;
	public void captionDownload(Workflow workflow) throws Exception;
}
