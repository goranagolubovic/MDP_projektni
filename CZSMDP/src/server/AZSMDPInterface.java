package server;

import java.nio.file.Path;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AZSMDPInterface extends Remote {

	boolean uploadReport(String pathOfFile) throws RemoteException;
//arhiviraj izvjestaj
	void downloadReport(String pathOfFile) throws RemoteException;
}
