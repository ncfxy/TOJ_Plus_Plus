package sfm.diveof.shifengming;

import java.lang.Process;
import java.util.Scanner;
import java.io.*;
>>>>>>> refs/remotes/ncfxy/master:src/main/java/sfm/diveof/shifengming/divFile.java

public class DivFile {
	String CodePath,ExePath,InputPath,OutPutPath;
	List<String> DonePaths;

	public static String GetCodePath(){
		return new String("");
	}
	public static String GetInputPath(){
		return new String("");
	}
	public static String GetOutputPath(){
		return new String("");
	}
	public static String CandB(String CodePath){
		return new String("");
	}
	
	public static List<String> divfile(){
		CodePath = GetCodePath();
		InputPath = GetInputPath();
		OutputPath = GetOutputPath();
		ExePath = CandB(CodePath);
		if( (DonePaths = DivT.divInput(InputPath,OutputPath,ExePath)) != null ) return DonePaths;
		if( (DonePaths = DivSentry.divInput(InputPath,OutputPath,ExePath)) != null ) return DonePaths;
		DonePaths = DivEOF.divInput(InputPath,OutputPath,ExePath);
		return DonePaths;
	}
	
}

