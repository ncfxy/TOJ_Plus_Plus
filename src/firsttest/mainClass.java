package firsttest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class mainClass
{
	public static void main(String[] args) throws IOException, InterruptedException
	{
		String str1 = "G:/test.exe";
		System.out.println(str1);
		Process pr = null;
		try {
			pr = Runtime.getRuntime().exec("cmd.exe /c start " + str1);
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("123");
			e.printStackTrace();
			
		}
		
		pr.waitFor();
		//Process pr = Runtime.getRuntime().exec("test.exe 1 2");
		BufferedReader br = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		String s = new String(); 
		s = br.readLine();
		while(s != null)
		{
			System.out.println(s);
			s = br.readLine();
		}
		br.close();
	}
}

//package firsttest;
//
//import java.io.IOException;
//
//import firsttest.callexe;
//
//public class mainClass {
//
//	public static void main(String[] args) throws IOException, InterruptedException {
//		// TODO Auto-generated method stub
////		
////		callexe ce = new callexe();
////		String s = "test.exe";
////		ce.callExe(s);
//		
//		Process pr = Runtime.getRuntime().exec("test.exe");
////		BufferedReader br = new BufferedReader(new InputStreamReader(pr.getInputStream()));  
////        String s = br.readLine();  
////        String temp = "" ;  
////        while(null != s ){  
////            if(!"".equals(s.trim()))  temp = s;  
////            System.out.println(s);  
////            s = br.readLine();  
////        }  
////        br.close();  
////        //导致当前线程等待，如果必要，一直要等到由该 Process 对象表示的进程已经终止。  
////        pr.waitFor();   
////        //此 Process 对象表示的子进程的出口值。根据惯例，值 0 表示正常终止。  
////		System.out.println("1");
////		//test.test();
//	}
//
//}

//import java.io.BufferedReader;  
//import java.io.IOException;  
//import java.io.InputStreamReader;  
//import javax.swing.JOptionPane; 
//
//public class mainClass {  
//    public final static int END_MARK = 0;  
//  
//    /** 
//     * 使用Runtime对象的exec方法，运行cmd命令。 
//     */  
//    public static void main(String[] args) {  
//        Runtime rt = Runtime.getRuntime();  
//        try {         
//            Process pr = rt.exec("ping www.hao123.com "); //运行cmd命令  
//            BufferedReader br = new BufferedReader(new InputStreamReader(pr.getInputStream()));  
//            String s = br.readLine();  
//            String temp = "" ;  
//            while(null != s ){  
//                if(!"".equals(s.trim()))  temp = s;  
//                System.out.println(s);  
//                s = br.readLine();  
//            }  
//            br.close();  
//            //导致当前线程等待，如果必要，一直要等到由该 Process 对象表示的进程已经终止。  
//            pr.waitFor();   
//            //此 Process 对象表示的子进程的出口值。根据惯例，值 0 表示正常终止。  
//            if (END_MARK == pr.exitValue()) {  
//                JOptionPane.showMessageDialog(null, temp );  
//            }  
//        } catch (IOException e) {  
//            e.printStackTrace();  
//        } catch (InterruptedException e) {  
//            e.printStackTrace();  
//        }  
//    }  
//}  