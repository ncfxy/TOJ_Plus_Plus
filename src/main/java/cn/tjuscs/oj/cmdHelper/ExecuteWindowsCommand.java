package cn.tjuscs.oj.cmdHelper;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ncfxy
 * This class is a utility class. It will help other class execute 
 * windows command.
 *
 */
public class ExecuteWindowsCommand {
	private static final Logger LOGGER = Logger.getLogger("ncfxy");
	
	public static Logger getLogger() {
		return LOGGER;
	}

	private ExecuteWindowsCommand() {

	}

	/**
	 * @param command the windows command which will execute.
	 * @return A string that contains all the output when the windows
	 * 			command runs.
	 */
	public static String execute(String command) {
		Runtime rn = Runtime.getRuntime();
		Process p = null;
		StringBuilder results = new StringBuilder();
		try {
			String newCommand = "cmd /c " + command;
			System.out.println(newCommand);
			p = rn.exec(newCommand);
			new StreamGobbler(p.getInputStream(), "STDOUT", results).start();
			new StreamGobbler(p.getErrorStream(), "ERROR", results).start();

			int exitValue = p.waitFor();
			ExecuteWindowsCommand.getLogger()
					.log(Level.INFO,
							"execute Windows Command success! exitValue = "
									+ exitValue);
		} catch (IOException e) {
			ExecuteWindowsCommand.getLogger()
					.log(Level.WARNING, "execute Windows Command error");
			ExecuteWindowsCommand.getLogger().log(Level.SEVERE, "error", e);
		} catch (InterruptedException e) {
			ExecuteWindowsCommand.getLogger().log(Level.SEVERE, "error", e);
		}
		return results.toString();
	}

}
