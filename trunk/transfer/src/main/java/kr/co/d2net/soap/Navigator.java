/**
 * Navigator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package kr.co.d2net.soap;

public interface Navigator extends java.rmi.Remote {
    public java.lang.String saveFileIngest(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String findTransfers(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String saveStatus(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String soapTest(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String saveReports(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String saveContents(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String findDailySchedule(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String findContents(java.lang.String arg0) throws java.rmi.RemoteException;
}
