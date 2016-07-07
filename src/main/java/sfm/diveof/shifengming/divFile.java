package sfm.diveof.shifengming;

import java.io.IOException;
import java.util.List;

public class divFile {
	String CodePath, ExePath, InputPath, OutputPath;
	List<String> DonePaths;


	public String GetCodePath() {
		return new String("");
	}

	public String GetInputPath() {
		return new String("");
	}

	public String GetOutputPath() {
		return new String("");
	}

	public String CandB(String CodePath){
		return new String("");
	}

	public List<String> divfile() throws IOException, InterruptedException {
		CodePath = GetCodePath();
		InputPath = GetInputPath();
		OutputPath = GetOutputPath();
		ExePath = CandB(CodePath);
		if ((DonePaths = DivT.divInput(InputPath, OutputPath, ExePath)) != null)
			return DonePaths;
		if ((DonePaths = DivSentry.divInput(InputPath, OutputPath, ExePath)) != null)
			return DonePaths;
		DonePaths = DivEOF.divInput(InputPath, OutputPath, ExePath);
		return DonePaths;
	}

}
