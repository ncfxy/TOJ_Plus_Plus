package cn.tjuscs.oj.wyh;

import java.io.BufferedReader;
import java.io.File;

import cn.tjuscs.oj.cmdHelper.ExecuteWindowsCommand;

public class CompareFile {
//	protected String file1;
//	protected String file2;
//	protected CompareFile(String fileUrl1, String fileUrl2){
//		file1 = fileUrl1;
//		file2 = fileUrl2;
//	}
	
	public static String compare(String file1, String file2){
		return ExecuteWindowsCommand.execute("diff.exe" + " " + file1 + ".src" + " " + file2 +  ".src");
	}
}
