package server;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;

public class Report implements Serializable{

	private static final long serialVersionUID = 1L;
	private String fileName;
	private byte[] fileContent;
	private String username;
	private String time;
	private long fileSize;
	
	public Map<String, Object> prepareJson() {
		return Map.of(
				"userName", username, "time", time, "fileSize", fileSize);
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public byte[] getFileContent() {
		return fileContent;
	}
	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(fileContent);
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + (int) (fileSize ^ (fileSize >>> 32));
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Report other = (Report) obj;
		if (!Arrays.equals(fileContent, other.fileContent))
			return false;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (fileSize != other.fileSize)
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	public Report(String fileName, byte[] fileContent, String username, String time, long fileSize) {
		super();
		this.fileName = fileName;
		this.fileContent = fileContent;
		this.username = username;
		this.time = time;
		this.fileSize = fileSize;
	}
	public Report() {
		super();
	}
	
}
