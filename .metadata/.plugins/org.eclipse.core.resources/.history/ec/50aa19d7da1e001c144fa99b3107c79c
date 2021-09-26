/**
 * XMLDeserializerServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package control;

public class XMLDeserializerServiceLocator extends org.apache.axis.client.Service implements control.XMLDeserializerService {

    public XMLDeserializerServiceLocator() {
    }


    public XMLDeserializerServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public XMLDeserializerServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for XMLDeserializer
    private java.lang.String XMLDeserializer_address = "http://localhost:8080/LoginControlService/services/XMLDeserializer";

    public java.lang.String getXMLDeserializerAddress() {
        return XMLDeserializer_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String XMLDeserializerWSDDServiceName = "XMLDeserializer";

    public java.lang.String getXMLDeserializerWSDDServiceName() {
        return XMLDeserializerWSDDServiceName;
    }

    public void setXMLDeserializerWSDDServiceName(java.lang.String name) {
        XMLDeserializerWSDDServiceName = name;
    }

    public control.XMLDeserializer getXMLDeserializer() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(XMLDeserializer_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getXMLDeserializer(endpoint);
    }

    public control.XMLDeserializer getXMLDeserializer(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            control.XMLDeserializerSoapBindingStub _stub = new control.XMLDeserializerSoapBindingStub(portAddress, this);
            _stub.setPortName(getXMLDeserializerWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setXMLDeserializerEndpointAddress(java.lang.String address) {
        XMLDeserializer_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (control.XMLDeserializer.class.isAssignableFrom(serviceEndpointInterface)) {
                control.XMLDeserializerSoapBindingStub _stub = new control.XMLDeserializerSoapBindingStub(new java.net.URL(XMLDeserializer_address), this);
                _stub.setPortName(getXMLDeserializerWSDDServiceName());
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
        if ("XMLDeserializer".equals(inputPortName)) {
            return getXMLDeserializer();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://control", "XMLDeserializerService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://control", "XMLDeserializer"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("XMLDeserializer".equals(portName)) {
            setXMLDeserializerEndpointAddress(address);
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
