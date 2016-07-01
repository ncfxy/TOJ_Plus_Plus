package helloworld;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import cn.tjuscs.oj.cmdHelper.ExecuteWindowsCommand;
import cn.tjuscs.oj.cmdHelper.StreamGobbler;

public class ExecuteLinuxCommand {
	private static final Logger LOGGER = Logger.getLogger("yunhao");
	
	public static Logger getLogger(){
		return LOGGER;
	}
	
	private void ExecuteLinuxCommand(){
		
	}
	
	public static String execute(String command){
		Runtime rn = Runtime.getRuntime();
		Process p = null;
		StringBuilder results = new StringBuilder();
		try{
			String newCommand = command;
			System.out.println(newCommand);
			p = rn.exec(newCommand);
			new StreamGobbler(p.getInputStream(), "STDOUT", results).start();
			new StreamGobbler(p.getErrorStream(), "ERROR", results).start();
			
			int exitValue = p.waitFor();
			ExecuteLinuxCommand.getLogger().log(Level.INFO, "execute Linux Command success! exitValue = " + exitValue);
		}
		catch (IOException e){
			ExecuteLinuxCommand.getLogger().log(Level.WARNING, "execute Linux Command error");
			ExecuteLinuxCommand.getLogger().log(Level.SEVERE, "error", e);
		}
		catch (InterruptedException e){
			ExecuteLinuxCommand.getLogger().log(Level.SEVERE, "error", e);;
		}
		return results.toString();
	}
	
}