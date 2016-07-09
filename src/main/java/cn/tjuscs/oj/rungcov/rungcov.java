package cn.tjuscs.oj.rungcov;

import java.util.*;

import cn.tjuscs.oj.cmdHelper.ExecuteWindowsCommand;

import java.io.*;

public class rungcov {
	public static void main(String [] argvs) throws NumberFormatException, IOException{
		String casenumFileName = "caseNum.txt";
		String inputFileName = "test";
		String outputFileName = "output";
		String srcFileName = "demo";
		FileReader cnst = new FileReader(casenumFileName);
		BufferedReader fin = new BufferedReader(cnst);
		int casenum = Integer.valueOf(fin.readLine()).intValue();
		ExecuteWindowsCommand CMD = null;
		ExecuteWindowsCommand.execute("g++ "+srcFileName+".cpp -o "+srcFileName + ".exe -ftest-coverage -fprofile-arcs");
		for(int i=1;i<=casenum;i++){
			ExecuteWindowsCommand.execute(srcFileName+".exe < "+inputFileName+i+".txt > "+outputFileName+i+".txt");
			ExecuteWindowsCommand.execute("gcov "+srcFileName+".cpp");
			ExecuteWindowsCommand.execute("ren "+srcFileName+".cpp.gcov "+srcFileName+i+".cpp.gcov");
		}   
		CMD.execute("python newgetMatrixFromGcov.py "+srcFileName+" 1 "+casenum);
	}
}
