package cn.tjuscs.oj.rungcov;
import java.util.*;

import cn.tjuscs.oj.cmdHelper.ExecuteWindowsCommand;

import java.io.*;

public class rungcov {
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		runAndGetMat("testid","2800");
	}
	public static void runAndGetMat(String sid, String pid) throws NumberFormatException, IOException{
		System.out.println("haha");
		//sid = "1131123";
		//pid = "1007";
		String dataPath = "./data/toj_problem_"+pid;
		dataPath = new File(dataPath).getCanonicalPath();
		String casenumFileName = dataPath+"/splitedTestCases/2800_total.txt";
		String inputFileName = dataPath+"/splitedTestCases/2800_";
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
		
		ExecuteWindowsCommand.execute("python ./linux和python脚本/compfile.py "+(dataPath+"/programs/commit_id_"+sid+"/outputs.csv ")+(dataPath+"/2800.out")+(outputFileName)+casenum);
		ExecuteWindowsCommand.execute("python ./linux和python脚本/getMatrixFromGcov.py "+srcFileName+" 1 "+casenum);
	}
}
