package kr.co.d2net.soap;

public class NavigatorProxy implements kr.co.d2net.soap.Navigator {
  private String _endpoint = null;
  private kr.co.d2net.soap.Navigator navigator = null;
  
  public NavigatorProxy() {
    _initNavigatorProxy();
  }
  
  public NavigatorProxy(String endpoint) {
    _endpoint = endpoint;
    _initNavigatorProxy();
  }
  
  private void _initNavigatorProxy() {
    try {
      navigator = (new kr.co.d2net.soap.ServiceNavigatorServiceLocator()).getServiceNavigatorPort();
      if (navigator != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)navigator)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)navigator)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (navigator != null)
      ((javax.xml.rpc.Stub)navigator)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public kr.co.d2net.soap.Navigator getNavigator() {
    if (navigator == null)
      _initNavigatorProxy();
    return navigator;
  }
  
  public java.lang.String saveFileIngest(java.lang.String arg0) throws java.rmi.RemoteException{
    if (navigator == null)
      _initNavigatorProxy();
    return navigator.saveFileIngest(arg0);
  }
  
  public java.lang.String findTransfers(java.lang.String arg0) throws java.rmi.RemoteException{
    if (navigator == null)
      _initNavigatorProxy();
    return navigator.findTransfers(arg0);
  }
  
  public java.lang.String saveStatus(java.lang.String arg0) throws java.rmi.RemoteException{
    if (navigator == null)
      _initNavigatorProxy();
    return navigator.saveStatus(arg0);
  }
  
  public java.lang.String soapTest(java.lang.String arg0) throws java.rmi.RemoteException{
    if (navigator == null)
      _initNavigatorProxy();
    return navigator.soapTest(arg0);
  }
  
  public java.lang.String saveReports(java.lang.String arg0) throws java.rmi.RemoteException{
    if (navigator == null)
      _initNavigatorProxy();
    return navigator.saveReports(arg0);
  }
  
  public java.lang.String saveContents(java.lang.String arg0) throws java.rmi.RemoteException{
    if (navigator == null)
      _initNavigatorProxy();
    return navigator.saveContents(arg0);
  }
  
  public java.lang.String findDailySchedule(java.lang.String arg0) throws java.rmi.RemoteException{
    if (navigator == null)
      _initNavigatorProxy();
    return navigator.findDailySchedule(arg0);
  }
  
  public java.lang.String findContents(java.lang.String arg0) throws java.rmi.RemoteException{
    if (navigator == null)
      _initNavigatorProxy();
    return navigator.findContents(arg0);
  }
  
  
}