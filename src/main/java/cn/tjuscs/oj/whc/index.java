package cn.tjuscs.oj.whc;

import java.io.IOException;

public class index {

	public static void main(String[] args) throws IOException{
		
		// TODO Auto-generated method stub
		String infile = "D:\\application\\eclipse\\WorkSpace\\TOJ_Plus_Plus\\src\\main\\java\\data\\1007.in";
		String outfile = "D:\\application\\eclipse\\WorkSpace\\TOJ_Plus_Plus\\src\\main\\java\\data\\1007.out";
		String target = "D:\\application\\eclipse\\WorkSpace\\TOJ_Plus_Plus\\src\\main\\java\\splitedFiles";
		String rightPro = "D:\\application\\eclipse\\WorkSpace\\TOJ_Plus_Plus\\src\\main\\java\\rightPro";
		
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
