package service;

public class LoginServiceProxy implements service.LoginService {
  private String _endpoint = null;
  private service.LoginService loginService = null;
  
  public LoginServiceProxy() {
    _initLoginServiceProxy();
  }
  
  public LoginServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initLoginServiceProxy();
  }
  
  private void _initLoginServiceProxy() {
    try {
      loginService = (new service.LoginServiceServiceLocator()).getLoginService();
      if (loginService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)loginService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)loginService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (loginService != null)
      ((javax.xml.rpc.Stub)loginService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public service.LoginService getLoginService() {
    if (loginService == null)
      _initLoginServiceProxy();
    return loginService;
  }
  
  public model.User login(java.lang.String user, java.lang.String password) throws java.rmi.RemoteException{
    if (loginService == null)
      _initLoginServiceProxy();
    return loginService.login(user, password);
  }
  
  public java.lang.String[] getUsersForSelectedStation(java.lang.String station) throws java.rmi.RemoteException{
    if (loginService == null)
      _initLoginServiceProxy();
    return loginService.getUsersForSelectedStation(station);
  }
  
  public java.lang.String[] getStations() throws java.rmi.RemoteException{
    if (loginService == null)
      _initLoginServiceProxy();
    return loginService.getStations();
  }
  
  
}