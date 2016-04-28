
public class DivFile {
	String CodePath,ExePath,InputPath,OutPutPath;
	List<String> DonePaths;

	public static String GetCodePath(){
		return String("");
	}
	public static String GetInputPath(){
		return String("");
	}
	public static String GetOutputPath(){
		return String("");
	}
	public static String CandB(String CodePath){
		return String("")
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

