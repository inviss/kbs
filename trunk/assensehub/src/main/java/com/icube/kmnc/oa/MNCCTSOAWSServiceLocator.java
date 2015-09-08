/**
 * MNCCTSOAWSServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.icube.kmnc.oa;

public class MNCCTSOAWSServiceLocator extends org.apache.axis.client.Service implements com.icube.kmnc.oa.MNCCTSOAWSService {

    public MNCCTSOAWSServiceLocator() {
    }


    public MNCCTSOAWSServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public MNCCTSOAWSServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for MNCCTSOAWSPort
    private java.lang.String MNCCTSOAWSPort_address = "http://10.110.80.23:8080/MNCCTSOA/MNCCTSOAWS";

    public java.lang.String getMNCCTSOAWSPortAddress() {
        return MNCCTSOAWSPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String MNCCTSOAWSPortWSDDServiceName = "MNCCTSOAWSPort";

    public java.lang.String getMNCCTSOAWSPortWSDDServiceName() {
        return MNCCTSOAWSPortWSDDServiceName;
    }

    public void setMNCCTSOAWSPortWSDDServiceName(java.lang.String name) {
        MNCCTSOAWSPortWSDDServiceName = name;
    }

    public com.icube.kmnc.oa.MNCCTSOAWS getMNCCTSOAWSPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(MNCCTSOAWSPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getMNCCTSOAWSPort(endpoint);
    }

    public com.icube.kmnc.oa.MNCCTSOAWS getMNCCTSOAWSPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.icube.kmnc.oa.MNCCTSOAWSPortBindingStub _stub = new com.icube.kmnc.oa.MNCCTSOAWSPortBindingStub(portAddress, this);
            _stub.setPortName(getMNCCTSOAWSPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setMNCCTSOAWSPortEndpointAddress(java.lang.String address) {
        MNCCTSOAWSPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.icube.kmnc.oa.MNCCTSOAWS.class.isAssignableFrom(serviceEndpointInterface)) {
                com.icube.kmnc.oa.MNCCTSOAWSPortBindingStub _stub = new com.icube.kmnc.oa.MNCCTSOAWSPortBindingStub(new java.net.URL(MNCCTSOAWSPort_address), this);
                _stub.setPortName(getMNCCTSOAWSPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("MNCCTSOAWSPort".equals(inputPortName)) {
            return getMNCCTSOAWSPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://oa.kmnc.icube.com/", "MNCCTSOAWSService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://oa.kmnc.icube.com/", "MNCCTSOAWSPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("MNCCTSOAWSPort".equals(portName)) {
            setMNCCTSOAWSPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
