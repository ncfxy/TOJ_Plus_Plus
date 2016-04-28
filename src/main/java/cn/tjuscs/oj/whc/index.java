package cn.tjuscs.oj.whc;

import java.io.IOException;

public class index {

	public static void main(String[] args){
		// TODO Auto-generated method stub
		String sourcePath = "";
		String targetPath = "";
		String rightProPath = "C:\\Users\\dell\\Desktop\\ojTest\\bin\\Debug";
		String standardInputFilePath = "C:\\Users\\dell\\Desktop\\data\\1007.in";
		String standardOutputFilePath = "C:\\Users\\dell\\Desktop\\data\\1007.out";
		String rightProExe = "C:\\Users\\dell\\Desktop\\ojTest\\bin\\Debug\\ojTest.exe";
		FileKind fileKind = new KindT(sourcePath, targetPath, rightProPath);
		
		try {
			int kind = fileKind.judgeKind(standardInputFilePath, standardOutputFilePath, rightProExe);
			if(kind == fileKind.Sentinel_Kind){
				System.out.println("Sentinal!");
			}
			else{
				System.out.println("Unknow!");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
