package firsttest;

import java.io.IOException;

public class callexe {
	
	public void callExe(String id) throws IOException, InterruptedException
	{
		Process process = Runtime.getRuntime().exec(id);
		process.destroy();
//		try {
//			process = rt.exec(id);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println("error in open exe");
//		}
//		
//		Process process = Runtime.getRuntime().exec("");
//		process.destroy();
//		System.out.println("after wati");
//		process.waitFor();
//		System.out.println("after wati");
	}
}