package cn.tjuscs.oj.testCases;

import java.util.List;

public interface SplitTestCases {

	/**
	 * 初始化类，传入输入文件路径�?输出文件路径、正确代码路�?
	 */
	public void init(String inputFilePath, String outputFilePath, String rightProgramPath);
	/**
	 * @return 返回�?��列拆分后的输入文�?
	 */
	public List<String> getSplitInputFilesResult();
	/**
	 * @return 返回�?��列拆分后的输出文�?
	 */
	public List<String> getSplitOutputFilesResult();
	
}
