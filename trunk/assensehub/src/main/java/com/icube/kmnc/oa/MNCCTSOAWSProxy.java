package com.icube.kmnc.oa;

public class MNCCTSOAWSProxy implements com.icube.kmnc.oa.MNCCTSOAWS {
  private String _endpoint = null;
  private com.icube.kmnc.oa.MNCCTSOAWS mNCCTSOAWS = null;
  
  public MNCCTSOAWSProxy() {
    _initMNCCTSOAWSProxy();
  }
  
  public MNCCTSOAWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initMNCCTSOAWSProxy();
  }
  
  private void _initMNCCTSOAWSProxy() {
    try {
      mNCCTSOAWS = (new com.icube.kmnc.oa.MNCCTSOAWSServiceLocator()).getMNCCTSOAWSPort();
      if (mNCCTSOAWS != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)mNCCTSOAWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)mNCCTSOAWS)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (mNCCTSOAWS != null)
      ((javax.xml.rpc.Stub)mNCCTSOAWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.icube.kmnc.oa.MNCCTSOAWS getMNCCTSOAWS() {
    if (mNCCTSOAWS == null)
      _initMNCCTSOAWSProxy();
    return mNCCTSOAWS;
  }
  
  public int requestJob(java.lang.String srcVideoPath, java.lang.String srcMetaPath, java.lang.String convertProfileName, java.lang.String transferProfileName, java.lang.String callerName, java.lang.String userID, java.lang.String extra1, java.lang.String extra2, java.lang.String extra3) throws java.rmi.RemoteException{
    if (mNCCTSOAWS == null)
      _initMNCCTSOAWSProxy();
    return mNCCTSOAWS.requestJob(srcVideoPath, srcMetaPath, convertProfileName, transferProfileName, callerName, userID, extra1, extra2, extra3);
  }
  
  public void getJobStatus(int jobID, javax.xml.rpc.holders.IntHolder _return, javax.xml.rpc.holders.IntegerWrapperHolder jobStatus, javax.xml.rpc.holders.IntegerWrapperHolder jobProgress, javax.xml.rpc.holders.IntegerWrapperHolder errorOut) throws java.rmi.RemoteException{
    if (mNCCTSOAWS == null)
      _initMNCCTSOAWSProxy();
    mNCCTSOAWS.getJobStatus(jobID, _return, jobStatus, jobProgress, errorOut);
  }
  
  public java.lang.String[] getConvertProfileList() throws java.rmi.RemoteException{
    if (mNCCTSOAWS == null)
      _initMNCCTSOAWSProxy();
    return mNCCTSOAWS.getConvertProfileList();
  }
  
  public java.lang.String[] getTransferProfileList() throws java.rmi.RemoteException{
    if (mNCCTSOAWS == null)
      _initMNCCTSOAWSProxy();
    return mNCCTSOAWS.getTransferProfileList();
  }
  
  public java.lang.String getMNCFTPSvr(java.lang.String callerName) throws java.rmi.RemoteException{
    if (mNCCTSOAWS == null)
      _initMNCCTSOAWSProxy();
    return mNCCTSOAWS.getMNCFTPSvr(callerName);
  }
  
  public int requestJobEx(java.lang.String srcVideoPath, java.lang.String srcMetaPath, java.lang.String convertProfileName, java.lang.String transferProfileName, java.lang.String callerName, java.lang.String userID, java.lang.String extra1, java.lang.String extra2, java.lang.String extra3, java.lang.String programID, java.lang.String programCode) throws java.rmi.RemoteException{
    if (mNCCTSOAWS == null)
      _initMNCCTSOAWSProxy();
    return mNCCTSOAWS.requestJobEx(srcVideoPath, srcMetaPath, convertProfileName, transferProfileName, callerName, userID, extra1, extra2, extra3, programID, programCode);
  }
  
  public int cancelJob(int jobID) throws java.rmi.RemoteException{
    if (mNCCTSOAWS == null)
      _initMNCCTSOAWSProxy();
    return mNCCTSOAWS.cancelJob(jobID);
  }
  
  
}