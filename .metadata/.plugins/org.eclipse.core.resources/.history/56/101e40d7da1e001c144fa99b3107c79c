package control;

public class XMLDeserializerProxy implements control.XMLDeserializer {
  private String _endpoint = null;
  private control.XMLDeserializer xMLDeserializer = null;
  
  public XMLDeserializerProxy() {
    _initXMLDeserializerProxy();
  }
  
  public XMLDeserializerProxy(String endpoint) {
    _endpoint = endpoint;
    _initXMLDeserializerProxy();
  }
  
  private void _initXMLDeserializerProxy() {
    try {
      xMLDeserializer = (new control.XMLDeserializerServiceLocator()).getXMLDeserializer();
      if (xMLDeserializer != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)xMLDeserializer)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)xMLDeserializer)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (xMLDeserializer != null)
      ((javax.xml.rpc.Stub)xMLDeserializer)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public control.XMLDeserializer getXMLDeserializer() {
    if (xMLDeserializer == null)
      _initXMLDeserializerProxy();
    return xMLDeserializer;
  }
  
  public model.User deserializeWithXML(java.lang.String XMLuser) throws java.rmi.RemoteException{
    if (xMLDeserializer == null)
      _initXMLDeserializerProxy();
    return xMLDeserializer.deserializeWithXML(XMLuser);
  }
  
  
}