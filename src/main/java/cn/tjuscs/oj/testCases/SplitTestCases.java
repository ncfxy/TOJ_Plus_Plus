package cn.tjuscs.oj.testCases;

import java.util.List;

public interface SplitTestCases {

	/**
	 * 初始化类，传入输入文件路径、输出文件路径、正确代码存放路径
	 */
	public void init(String inputFilePath, String outputFilePath, String rightProgramPath);
	/**
	 * @return 返回拆分后的输入文件列表
	 */
	public List<String> getSplitInputFilesResult();
	/**
	 * @return 返回拆分后的输出文件列表
	 */
	public List<String> getSplitOutputFilesResult();
	
}
