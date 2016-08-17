package cn.tjuscs.oj.rungcov;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.ncfxy.FaultLocalization.FaultLocalization;

import cn.tjuscs.oj.cmdHelper.ExecuteWindowsCommand;
import cn.tjuscs.oj.cmdHelper.JavaOperateFiles;

public class rungcov {

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		rungcov r = new rungcov();
		r.runAndGetMat("123456", "2800");
	}

	/**
	 * 运行并生成该程序的覆盖矩阵
	 *
	 * @param sid
	 *            代码编号
	 * @param pid
	 *            问题编号
	 * @throws NumberFormatException
	 * @throws IOException
	 * @since TOJ_Plus_Plus　Ver 1.0-SNAPSHOT
	 */
	public void runAndGetMat(String sid, String pid)
			throws NumberFormatException, IOException {

		String workpath = new File("./").getCanonicalPath();
		System.out.println(workpath);
		String dataPath = "./data/toj_problem_" + pid;
		dataPath = new File(dataPath).getCanonicalPath();
		String casenumFileName = dataPath + "\\splitedTestCases\\" + pid
				+ "_total.txt";
		String inputFileName = dataPath + "\\splitedTestCases\\" + pid + "_";
		String outputFileName = dataPath + "\\splitedTestCases\\output";
		String srcFileDir = dataPath + "\\programs\\commit_id_" + sid + "\\";
		String srcFileName = dataPath + "\\programs\\commit_id_" + sid + "\\"
				+ sid;
		String compileHelperPath = workpath + "\\" + "compile_helper.bat ";

		FileReader cnst = new FileReader(casenumFileName);
		BufferedReader fin = new BufferedReader(cnst);
		int casenum = Integer.valueOf(fin.readLine()).intValue();
		fin.close();

		// 编译文件，使用-ftest-coverage -fprofile-arcs参数
		ExecuteWindowsCommand.execute(compileHelperPath
				+ (dataPath + "\\programs\\commit_id_" + sid) + " " + sid
				+ " g++");

		for (int i = 0; i < casenum; i++) {
			JavaOperateFiles.deleteFile(srcFileDir + sid + ".gcda");
			ExecuteWindowsCommand.execute(srcFileName + ".exe < "
					+ inputFileName + i + ".in > " + outputFileName + i
					+ ".out");
			// ExecuteWindowsCommand.execute("gcov "+srcFileName+".cpp");
			ExecuteWindowsCommand.execute(compileHelperPath
					+ (dataPath + "\\programs\\commit_id_" + sid) + " " + sid
					+ " gcov");
			JavaOperateFiles.deleteFile(srcFileDir+sid+"_"+i+".cpp.gcov");
			JavaOperateFiles.renameFile(srcFileDir, sid + ".cpp.gcov", sid
					+ "_" + i + ".cpp.gcov");
		}
		List<Integer> testResult = compareAndCombine((dataPath
				+ "\\splitedTestCases\\" + pid), (outputFileName), casenum);
		getMatrixFromGcov(srcFileDir, sid, casenum, testResult);
	}

	/**
	 * 遍历gcov文件生成覆盖矩阵coverage_matrix.txt
	 *
	 * @param sourceDir
	 * @param programName
	 * @param caseNum
	 * @param testResult
	 * @throws IOException
	 * @since TOJ_Plus_Plus　Ver 1.0-SNAPSHOT
	 */
	private void getMatrixFromGcov(String sourceDir, String programName,
			int caseNum, List<Integer> testResult) throws IOException {
		// programName 使用sid
		String outputFileName = sourceDir + "coverage_matrix.txt";
		StringBuffer outputBuffer = new StringBuffer();
		outputBuffer.append("#Ver_# " + programName + '\n');
		outputBuffer.append("#NOTS# " + caseNum + '\n');
		List<String> executableLines = getExecutableLines(sourceDir
				+ programName + "_1.cpp.gcov");
		outputBuffer.append("#LOES# ");
		for (String s : executableLines) {
			outputBuffer.append(s + " ");
		}
		outputBuffer.append("\n");
		outputBuffer.append("#NOES# " + executableLines.size() + "\n");
		outputBuffer.append("#NOF_# 0\n");
		outputBuffer.append("#LOFS# 0\n");
		for (int i = 0; i < caseNum; i++) {
			outputBuffer.append("#CASE#" + getString(i) + "#R"
					+ testResult.get(i) + "# ");
			String fileName = sourceDir + programName + "_" + i + ".cpp.gcov";
			try (Scanner cin = new Scanner(new File(fileName));) {
				while (cin.hasNext()) {
					String line = cin.nextLine();
					if (line.length() < 16)
						continue;
					if (!(line.charAt(13) == ' ' && line.charAt(14) == '0')) {
						if (line.charAt(9) == ':' && line.charAt(15) == ':'
								&& line.charAt(8) != '-') {
							if (line.charAt(8) != '#') {
								outputBuffer.append("1 ");
							} else {
								outputBuffer.append("0 ");
							}
						}
					}
				}
				outputBuffer.append("\n");
			}
		}
		try (FileOutputStream outputStream = new FileOutputStream(new File(
				outputFileName));) {
			outputStream.write(outputBuffer.toString().getBytes());
			;
		}
		FaultLocalization localization = new FaultLocalization(outputFileName);
		List<Integer> sus = localization.getSuspiciousList();
		System.out.println("代码行的可疑值排序为:");
		System.out.println(sus.toString());
	}

	/**
	 * 将一个整数补全到5位,不足的前边补零
	 */
	private String getString(int x) {
		if (x < 10)
			return "0000" + x;
		else if (x < 100)
			return "000" + x;
		else if (x < 1000)
			return "00" + x;
		else if (x < 10000)
			return "0" + x;
		else
			return "" + x;
	}

	/**
	 * 遍历gcov生成文件，得到代码的可执行代码行列表
	 *
	 * @param gcovFile
	 * @return
	 * @throws FileNotFoundException
	 * @since TOJ_Plus_Plus　Ver 1.0-SNAPSHOT
	 */
	private List<String> getExecutableLines(String gcovFile)
			throws FileNotFoundException {
		Scanner cin = new Scanner(new File(gcovFile));
		List<String> list = new ArrayList<String>();
		while (cin.hasNext()) {
			String line = cin.nextLine();
			if (!(line.charAt(13) == ' ' && line.charAt(14) == '0')) {
				if (line.charAt(9) == ':' && line.charAt(15) == ':'
						&& line.charAt(8) != '-') {
					String s = line.substring(10, 15);
					s = s.replace(' ', '0');
					list.add(s);
				}
			}
		}
		cin.close();
		return list;
	}

	/**
	 * 对比所有测试用例的结果，0表示测试成功，1表示测试失败
	 * 
	 * @return List<Integer> 返回测试结果的数组
	 * @param expectResultBasePath
	 * @param actualResultBasePath
	 * @param caseNum
	 * @since TOJ_Plus_Plus　Ver 1.0-SNAPSHOT
	 */
	private List<Integer> compareAndCombine(String expectResultBasePath,
			String actualResultBasePath, int caseNum)
			throws FileNotFoundException, IOException {
		StringBuffer buffer = new StringBuffer();
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < caseNum; i++) {
			// 0表示成功，1表示失败
			if (JavaOperateFiles.compareTwoFile(expectResultBasePath + "_" + i
					+ ".out", actualResultBasePath + i + ".out")) {
				list.add(0);
			} else {
				list.add(1);
			}
		}
		return list;
	}

}
