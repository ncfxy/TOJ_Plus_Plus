package cn.tjuscs.oj.rungcov;
import java.util.*;

import cn.tjuscs.oj.cmdHelper.ExecuteWindowsCommand;

import java.io.*;

public class rungcov {
	public static void runAndGetMat(int sid, int pid) throws NumberFormatException, IOException{
		System.out.println("haha");
		//sid = "1131123";
		//pid = "1007";
		String dataPath = "./data/toj_problem"+pid;
		String casenumFileName = dataPath+"/splitedTestCases/caseNum.in";
		String inputFileName = dataPath+"/splitedTestCases/test";
		String outputFileName = dataPath+"/splitedTestCases/output";
		String srcFileName = dataPath+"/programs/commit_id_"+sid+"/"+sid;
		FileReader cnst = new FileReader(casenumFileName);
		BufferedReader fin = new BufferedReader(cnst);
		int casenum = Integer.valueOf(fin.readLine()).intValue();
		ExecuteWindowsCommand CMD = null;
		ExecuteWindowsCommand.execute("g++ "+srcFileName+".cpp -o "+srcFileName + ".exe -ftest-coverage -fprofile-arcs");
		for(int i=1;i<=casenum;i++){
			ExecuteWindowsCommand.execute(srcFileName+".exe < "+inputFileName+i+".in > "+outputFileName+i+".out");
			ExecuteWindowsCommand.execute("gcov "+srcFileName+".cpp");
			ExecuteWindowsCommand.execute("ren "+srcFileName+".cpp.gcov "+srcFileName+i+".cpp.gcov");
		}
		
		ExecuteWindowsCommand.execute("python ./pyscripts/compfile.py "+(dataPath+"/programs/commit_id_"+sid+"/outputs.csv ")+(dataPath+"/splitedTestCases/ans")+(outputFileName)+casenum);
		ExecuteWindowsCommand.execute("python ./pyscripts/getMatrixFromGcov.py "+srcFileName+" 1 "+casenum);
	}
}
