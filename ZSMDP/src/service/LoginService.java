/**
 * LoginService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package service;

public interface LoginService extends java.rmi.Remote {
    public model.User login(java.lang.String user, java.lang.String password) throws java.rmi.RemoteException;
}
