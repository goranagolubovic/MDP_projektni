package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class AZSMDPServer implements AZSMDPInterface {

	public static final String PATH = "resources";
	public static final String UPLOAD_PATH = "uploading";
	private Gson gson = new Gson();
	private static Properties properties;

	public static void main(String[] args) throws Exception {
		properties = new Properties();
		try {
			FileInputStream fw = new FileInputStream("properties/config.properties");
			properties.load(fw);
		} catch (FileNotFoundException e) {
			Logger.getLogger(AZSMDPServer.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		} catch (IOException e) {
			Logger.getLogger(AZSMDPServer.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		System.setProperty("java.security.policy", properties.getProperty("PATH_SERVERPOLICY"));
		System.setProperty("java.rmi.server.hostname", properties.getProperty("HOST_ADDRESS"));
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			AZSMDPServer server = new AZSMDPServer();
			AZSMDPInterface stub = (AZSMDPInterface) UnicastRemoteObject.exportObject(server, 0);
			LocateRegistry.createRegistry(1099);
			Naming.rebind(properties.getProperty("NAMING_PATH"), stub);
			System.out.println("AZSMDP server started.");
		} catch (Exception ex) {
			Logger.getLogger(AZSMDPServer.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

	@Override
	public boolean uploadReport(Report report) throws RemoteException {
		if (!report.getFileName().endsWith(".pdf")) {
			return false;
		}
		File destFile = new File(properties.getProperty("UPLOAD_PATH") + File.separator + report.getFileName());
		File serFile = new File(
				properties.getProperty("UPLOAD_PATH") + File.separator + report.getFileName().replace(".pdf", ".json"));
		try {
			Files.write(destFile.toPath(), report.getFileContent());
			String jsonContent = gson.toJson(report.prepareJson());
			Files.writeString(serFile.toPath(), jsonContent);
			return true;
		} catch (IOException e) {
			Logger.getLogger(AZSMDPServer.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		return false;
	}

	@Override
	public Report downloadReport(String pathOfFile) throws IOException {
		File file = new File(properties.getProperty("UPLOAD_PATH") + File.separator + pathOfFile);
		if (!file.exists()) {
			return null;
		}
		byte[] fileContent = Files.readAllBytes(file.toPath());
		File jsonFile = new File(file.toPath().toString().replace(".pdf", ".json"));
		JsonObject content = gson.fromJson(Files.readString(jsonFile.toPath()), JsonObject.class);
		return new Report(pathOfFile, fileContent, content.get("userName").getAsString(),
				content.get("time").getAsString(), content.get("fileSize").getAsLong());
	}

	@Override
	public List<String> getReportNames() throws RemoteException {
		try {
			return Files.list(Paths.get(properties.getProperty("UPLOAD_PATH"))).map(Path::getFileName)
					.map(Object::toString).filter(name -> name.endsWith(".pdf")).collect(Collectors.toList());
		} catch (IOException e) {
			Logger.getLogger(AZSMDPServer.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		return null;
	}

}
