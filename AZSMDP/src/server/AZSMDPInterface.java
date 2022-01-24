package server;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface AZSMDPInterface extends Remote {

	boolean uploadReport(Report report) throws RemoteException;
	Report downloadReport(String pathOfFile) throws RemoteException, IOException;
	List<String> getReportNames() throws RemoteException;
}
