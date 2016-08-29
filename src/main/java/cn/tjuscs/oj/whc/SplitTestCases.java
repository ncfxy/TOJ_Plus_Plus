package cn.tjuscs.oj.whc;
import java.util.List;

public interface SplitTestCases {

	
	public void init(String inputFilePath, String outputFilePath, String rightProgramPath);
	
	public List<String> getSplitInputFilesResult();
	
	public List<String> getSplitOutputFilesResult();
	
}
