package helloworld;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import cn.tjuscs.oj.cmdHelper.ExecuteWindowsCommand;
import javax.swing.JOptionPane;

public class Main {
	//.../.../g++,只支持g++编译器
	private static String compilorPath = "";
	
//	public static void main(String[] args) {
////		System.out.println("=os.name:"+System.getProperties().getProperty("os.name"));  
////		System.out.println("=file.separator:"+System.getProperties().getProperty("file.separator"));
//		
////		String s = "yunhao/haha/huhu/test.cpp";
////		System.out.println('\n');
////		String ret = compile("C:\\Users\\yunhao\\Desktop\\test1.cpp");
//		return;
//	}
	
	//判断操作系统的类型，只支持windows或linux系统
	private static boolean check(){
		String os = System.getProperties().getProperty("os.name");
		if(os.startsWith("win") || os.startsWith("Win")){
			return true;
		}
		return false;
	}
	
	//编译方法
	public static String compile(String str){
		String from = str;
		String to = "";
		String compileCmd = "";
		
		for (int i = 0; i < from.length(); i++){
			if(from.charAt(i) == '.') break;
			to += from.charAt(i);
		}
		
		//windows操作系统
		if(check()){
			if(compilorPath == ""){
				compileCmd += "g++ " + from + " -o " + to;
			}
			else{
				compileCmd += compilorPath + " " + from + " -o " + to;
			}
			String retCmd = ExecuteWindowsCommand.execute(compileCmd);
			if(retCmd != ""){
				String errorMsg = "编译未成功。可能出现的问题：\n"
						+ "1.该系统未配置g++的系统环境变量\n"
						+ "2.在目录下未找到文件\n"
						+ "3.compilorPath未配置正确\n"
						+ "4.命令写错了\n";
				JOptionPane.showMessageDialog(null, errorMsg, "WARN", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		//linux操作系统
		else{
			if(compilorPath == ""){
				compileCmd += "g++ -o " + to + " " + from;
			}
			else{
				compileCmd += compilorPath + " -o " + to + " " + from;
			}
			
			String retCmd = ExecuteLinuxCommand.execute(compileCmd);
			if(retCmd != ""){
				String errorMsg = "编译未成功。可能出现的问题：\n"
						+ "1.该系统未配置g++的系统环境变量\n"
						+ "2.在目录下未找到文件\n"
						+ "3.compilorPath未配置正确\n"
						+ "4.命令写错了\n";
				JOptionPane.showMessageDialog(null, errorMsg, "WARN", JOptionPane.ERROR_MESSAGE);	
			}
		}
		
		return to;
	}
	
	public static void setPath(String MinGW_Path){
		compilorPath = MinGW_Path;
	}
	
	public static void ClearPath(){
		compilorPath = "";
	}
	
}
