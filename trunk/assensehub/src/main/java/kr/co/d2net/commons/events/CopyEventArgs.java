package kr.co.d2net.commons.events;

import kr.co.d2net.dto.TransferHisTbl;


public class CopyEventArgs extends EventArgs {
	private TransferHisTbl transferHisTbl;
	
	public CopyEventArgs(TransferHisTbl transferHisTbl) {
		this.transferHisTbl = transferHisTbl;
	}

	public TransferHisTbl getTransferHisTbl() {
		return transferHisTbl;
	}
	
}
