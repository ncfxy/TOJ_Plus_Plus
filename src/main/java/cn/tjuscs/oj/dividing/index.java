package cn.tjuscs.oj.dividing;

import java.io.File;
import java.io.IOException;

public class index {

	public static void main(String[] args) throws IOException{
		
		
//		String temp = "./\\data";
//		File test = new File(temp);
//		System.out.println(test.getCanonicalPath());
		// TODO Auto-generated method stub
		
		String pid = new String("2800");
		String sid = new String("testid");
		boolean result = dividing (pid, sid);
		if(result == false)
			System.out.println("defeated");
	}
	
	public static boolean dividing(String pid, String sid) throws IOException{
					
		int kind;
		
		//尝试按照T的格式处理文件。
		try {
			KindT kindt = new KindT(pid, sid);
			kind = kindt.judgeKind();
			if(kind == kindt.T_Kind){
				kindt.splitFile();
				return true;
			}
			else{
				System.out.println("NOT T");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//尝试按照哨兵的格式处理文件。
		try {
			KindSentinel kindSentinel = new KindSentinel(pid, sid);
			kind = kindSentinel.judgeKind();
			if(kind == kindSentinel.Sentinel_Kind){
				System.out.println("is sentinel");
				kindSentinel.splitFile();
				return true;
			}
			else{
				System.out.println("NOT Sentinel");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//尝试按照EOF处理文件
		try {
			KindEOF kindeof = new KindEOF(pid, sid);
			kind = kindeof.judgeKind();
			if(kind == kindeof.EOF_Kind){
				kindeof.splitFile();
				return true;
			}
			else{
				System.out.println("NOT EOF");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
}
