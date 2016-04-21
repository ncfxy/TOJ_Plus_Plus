package cn.tjuscs.oj.cmdHelper;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;


public class Cmd {
	private String[] CMD = {"cmd"}; 
	private PrintWriter output;
	
	
	Process p = null;
	

	public Cmd(OutputStream outStream) {
		try {
			p = Runtime.getRuntime().exec(CMD);
			if (outStream != null) {
				new Thread(new SyncPipe(p.getErrorStream(), outStream)).start();
	            new Thread(new SyncPipe(p.getInputStream(), outStream)).start(); 
			}
			else {
				new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
	            new Thread(new SyncPipe(p.getInputStream(), System.out)).start(); 
			}
			
			output = new PrintWriter(p.getOutputStream());
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	

	public void operate(String command){
		output.println(command);
		output.flush();
	}
}
