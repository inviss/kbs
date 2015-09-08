/**
 * ServiceNavigatorService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package kr.co.d2net.gate2.soap;

public interface ServiceNavigatorService extends javax.xml.rpc.Service {
    public java.lang.String getServiceNavigatorPortAddress();

    public kr.co.d2net.gate2.soap.Navigator getServiceNavigatorPort() throws javax.xml.rpc.ServiceException;

    public kr.co.d2net.gate2.soap.Navigator getServiceNavigatorPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
