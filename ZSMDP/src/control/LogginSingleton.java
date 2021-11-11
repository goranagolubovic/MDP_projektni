package control;

import java.util.ArrayList;
import java.util.List;

public class LogginSingleton {
	    private static LogginSingleton instance = null;
	    private List<String>activeUsers;
	    private LogginSingleton()
	    {
	       activeUsers=new ArrayList<>();
	    }
	    public static LogginSingleton getInstance()
	    {
	        // To ensure only one instance is created
	        if (instance == null) {
	            instance = new LogginSingleton();
	        }
	        return instance;
	    }
		public List<String> getActiveUsers() {
			return activeUsers;
		}
		public void setActiveUsers(List<String> activeUsers) {
			this.activeUsers = activeUsers;
		}
		public static void setInstance(LogginSingleton instance) {
			LogginSingleton.instance = instance;
		}
	
}
