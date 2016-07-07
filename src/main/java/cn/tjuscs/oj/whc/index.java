package cn.tjuscs.oj.whc;

import java.io.File;
import java.io.IOException;

public class index {

	public static void main(String[] args) throws IOException{
		
		
		String temp = "\\data";
		File test = new File(temp);
		System.out.println(test.getCanonicalPath());
		// TODO Auto-generated method stub
		
		
		//所有的文件目录都是一致的。
		String infile = "\\data\\toj_problem_1007\\1007.in";
		String outfile = "\\data\\toj_problem_1007\\1007.out";
		String target = "\\data\\toj_problem_1007\\splitedTestCases";
		//文件名中利用sid唯一确定program的地址。
		String rightPro = "\\data\\toj_problem_1007\\programs\\commit_id_1131123\\1131123.exe";
		
		KindSentinel kindSentinel = new KindSentinel(infile, outfile, target, rightPro);
		int kind;
		try {
			kind = kindSentinel.judgeKind();
			if(kind == kindSentinel.Sentinel_Kind){
				kindSentinel.splitFile();
			}
			else
				System.out.println("NO");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
