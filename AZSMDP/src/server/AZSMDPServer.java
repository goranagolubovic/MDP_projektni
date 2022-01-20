package server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.stream.Collectors;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class AZSMDPServer implements AZSMDPInterface {

	public static final String PATH = "resources";
	public static final String UPLOAD_PATH = "uploading";
	private Gson gson = new Gson();

	public static void main(String[] args) throws Exception {
		System.setProperty("java.security.policy", PATH + File.separator + "server_policyfile.txt");
		System.setProperty("java.rmi.server.hostname","127.0.0.1");
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			AZSMDPServer server = new AZSMDPServer();
			AZSMDPInterface stub = (AZSMDPInterface) UnicastRemoteObject.exportObject(server, 0);
			LocateRegistry.createRegistry(1099);
			Naming.rebind("//127.0.0.1/AZSMDPServer", stub);
			System.out.println("AZSMDP server started.");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public boolean uploadReport(Report report) throws RemoteException {
		if (!report.getFileName().endsWith(".pdf")) {
			return false;
		}
		File destFile = new File(UPLOAD_PATH + File.separator + report.getFileName());
		File serFile = new File(UPLOAD_PATH + File.separator + report.getFileName().replace(".pdf", ".json"));
		try {
			Files.write(destFile.toPath(), report.getFileContent());
			String jsonContent = gson.toJson(report.prepareJson());
			Files.writeString(serFile.toPath(), jsonContent);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Report downloadReport(String pathOfFile) throws IOException {
		File file = new File(UPLOAD_PATH + File.separator + pathOfFile);
		if (!file.exists()) {
			return null;
		}
		byte[] fileContent = Files.readAllBytes(file.toPath());
		File jsonFile = new File(pathOfFile.replace(".pdf", ".json"));
		JsonObject content = gson.fromJson(Files.readString(jsonFile.toPath()), JsonObject.class);
		return new Report(pathOfFile, fileContent, content.get("userName").getAsString(),
				content.get("time").getAsString(), content.get("fileSize").getAsLong());
	}

	@Override
	public List<String> getReportNames() throws RemoteException {
		try {
			return Files.list(Paths.get(UPLOAD_PATH)).map(Path::getFileName).map(Object::toString)
					.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
