package cn.tjuscs.oj.yh;

import cn.tjuscs.oj.cmdHelper.ExecuteWindowsCommand;
import javax.swing.JOptionPane;

public class Main {
	//.../.../g++,ֻ֧��g++������
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
	
	//�жϲ���ϵͳ�����ͣ�ֻ֧��windows��linuxϵͳ
	private static boolean check(){
		String os = System.getProperties().getProperty("os.name");
		if(os.startsWith("win") || os.startsWith("Win")){
			return true;
		}
		return false;
	}
	
	//���뷽��
	public static String compile(String str){
		String from = str;
		String to = "";
		String compileCmd = "";
		
		for (int i = 0; i < from.length(); i++){
			if(from.charAt(i) == '.') break;
			to += from.charAt(i);
		}
		
		//windows����ϵͳ
		if(check()){
			if(compilorPath == ""){
				compileCmd += "g++ " + from + " -o " + to;
			}
			else{
				compileCmd += compilorPath + " " + from + " -o " + to;
			}
			String retCmd = ExecuteWindowsCommand.execute(compileCmd);
			if(retCmd != ""){
				String errorMsg = "����δ�ɹ������ܳ��ֵ����⣺\n"
						+ "1.��ϵͳδ����g++��ϵͳ��������\n"
						+ "2.��Ŀ¼��δ�ҵ��ļ�\n"
						+ "3.compilorPathδ������ȷ\n"
						+ "4.����д����\n";
				JOptionPane.showMessageDialog(null, errorMsg, "WARN", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		//linux����ϵͳ
		else{
			if(compilorPath == ""){
				compileCmd += "g++ -o " + to + " " + from;
			}
			else{
				compileCmd += compilorPath + " -o " + to + " " + from;
			}
			
			String retCmd = ExecuteLinuxCommand.execute(compileCmd);
			if(retCmd != ""){
				String errorMsg = "����δ�ɹ������ܳ��ֵ����⣺\n"
						+ "1.��ϵͳδ����g++��ϵͳ��������\n"
						+ "2.��Ŀ¼��δ�ҵ��ļ�\n"
						+ "3.compilorPathδ������ȷ\n"
						+ "4.����д����\n";
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
