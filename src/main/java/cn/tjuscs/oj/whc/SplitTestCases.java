package cn.tjuscs.oj.whc;
import java.util.List;

public interface SplitTestCases {

		/**
		 * 初始化类，传入输入文件路径出文件路径、正确代码路徿
		 */
		public void init(String inputFilePath, String outputFilePath, String rightProgramPath);
		/**
		 * @return 返回列拆分后的输入文仿
		 */
		public List<String> getSplitInputFilesResult();
		/**
		 * @return 返回列拆分后的输出文仿
		 */
		public List<String> getSplitOutputFilesResult();
		
	
}
