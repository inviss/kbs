package kr.co.d2net.gate1.soap;

public class NavigatorProxy implements kr.co.d2net.gate1.soap.Navigator {
  private String _endpoint = null;
  private kr.co.d2net.gate1.soap.Navigator navigator = null;
  
  public NavigatorProxy() {
    _initNavigatorProxy();
  }
  
  public NavigatorProxy(String endpoint) {
    _endpoint = endpoint;
    _initNavigatorProxy();
  }
  
  private void _initNavigatorProxy() {
    try {
      navigator = (new kr.co.d2net.gate1.soap.ServiceNavigatorServiceLocator()).getServiceNavigatorPort();
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
  
  public kr.co.d2net.gate1.soap.Navigator getNavigator() {
    if (navigator == null)
      _initNavigatorProxy();
    return navigator;
  }
  
  public java.lang.String soapTest(java.lang.String arg0) throws java.rmi.RemoteException{
    if (navigator == null)
      _initNavigatorProxy();
    return navigator.soapTest(arg0);
  }
  
  public java.lang.String archiveSearch(java.lang.String arg0) throws java.rmi.RemoteException{
    if (navigator == null)
      _initNavigatorProxy();
    return navigator.archiveSearch(arg0);
  }
  
  
}