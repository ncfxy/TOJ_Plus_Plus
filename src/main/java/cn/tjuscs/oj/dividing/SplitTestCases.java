package cn.tjuscs.oj.dividing;
import java.util.List;

public interface SplitTestCases {

		/**
		 * ��ʼ���࣬���������ļ�·�����ļ�·������ȷ����·��
		 */
		public void init(String inputFilePath, String outputFilePath, String rightProgramPath);
		/**
		 * @return �����в�ֺ�������ķ�
		 */
		public List<String> getSplitInputFilesResult();
		/**
		 * @return �����в�ֺ������ķ�
		 */
		public List<String> getSplitOutputFilesResult();
		
	
}
