/**
 * MNCCTSOAWS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.icube.kmnc.oa;

public interface MNCCTSOAWS extends java.rmi.Remote {
    public int requestJob(java.lang.String srcVideoPath, java.lang.String srcMetaPath, java.lang.String convertProfileName, java.lang.String transferProfileName, java.lang.String callerName, java.lang.String userID, java.lang.String extra1, java.lang.String extra2, java.lang.String extra3) throws java.rmi.RemoteException;
    public void getJobStatus(int jobID, javax.xml.rpc.holders.IntHolder _return, javax.xml.rpc.holders.IntegerWrapperHolder jobStatus, javax.xml.rpc.holders.IntegerWrapperHolder jobProgress, javax.xml.rpc.holders.IntegerWrapperHolder errorOut) throws java.rmi.RemoteException;
    public java.lang.String[] getConvertProfileList() throws java.rmi.RemoteException;
    public java.lang.String[] getTransferProfileList() throws java.rmi.RemoteException;
    public java.lang.String getMNCFTPSvr(java.lang.String callerName) throws java.rmi.RemoteException;
    public int requestJobEx(java.lang.String srcVideoPath, java.lang.String srcMetaPath, java.lang.String convertProfileName, java.lang.String transferProfileName, java.lang.String callerName, java.lang.String userID, java.lang.String extra1, java.lang.String extra2, java.lang.String extra3, java.lang.String programID, java.lang.String programCode) throws java.rmi.RemoteException;
    public int cancelJob(int jobID) throws java.rmi.RemoteException;
}
