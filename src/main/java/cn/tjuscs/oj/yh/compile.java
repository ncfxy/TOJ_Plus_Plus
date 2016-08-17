package cn.tjuscs.oj.yh;

import cn.tjuscs.oj.cmdHelper.ExecuteWindowsCommand;
import cn.tjuscs.oj.yh.ChangeFile;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

//|------------------------------------------------------------------------------|
//| now this class can compile .c, .cpp, .src                                    |
//| 	1. if you have set the system parameter of g++, just use compile is OK.  |
//| 	2. if not, you should use the set_gpp_path to set the g++ path.          |
//| 	3. remember clear the gpp_path in needed.                                |
//| make the class is more strong. it is convenient for user to use the class.   |
//|------------------------------------------------------------------------------|

public class compile {
	
	//.../.../g++, the path of g++
	private static String gpp_path = "";
	//exe file default work dictionary
	private static String work_dir = "";
	
	//if you are lazy to give the disti_path, you can set this parameter
	public static void set_work_dir(String path){
		work_dir = path;
		
		if(check()){
			String md_cmd = "md " + work_dir;
			ExecuteWindowsCommand.execute(md_cmd);
		}
		else{
			String md_cmd = "mkdir -p " + work_dir;
			ExecuteLinuxCommand.execute(md_cmd);
		}
		
	}
	
	public static void clear_work_dir(){
		work_dir = "";
		System.out.println("Successful clear the work dictionary.");
	}
	
	//if you can't use g++ directly in the CMD.exe, so set this
	public static void set_gpp_path(String path){
		if(path.endsWith(".exe") || path.endsWith("g++") || path.endsWith("gcc")){
			gpp_path = path;
		}
		else if(check()){
			if(path.endsWith("bin")){
				gpp_path = path + "\\g++";
			}
			else if(path.endsWith("bin\\")){
				gpp_path = path + "g++";
			}
			else if(path.endsWith("MinGW")){
				gpp_path = path + "\\bin\\g++";
			}
			else if(path.endsWith("MinGW\\")){
				gpp_path = path + "bin\\g++";
			}
			else if(path.endsWith("MinGW32")){
				gpp_path = path + "\\bin\\g++";
			}
			else if(path.endsWith("MinGW32\\")){
				gpp_path = path + "bin\\g++";
			}
			else if(path.endsWith("MinGW64")){
				gpp_path = path + "\\bin\\g++";
			}
			else if(path.endsWith("MinGW64\\")){
				gpp_path = path + "bin\\g++";
			}
			else{
				System.out.println("Warning: failed setting the gpp_path");
				//JOptionPane.showMessageDialog(null, "failed to set the gpp_path", "WARN", JOptionPane.ERROR_MESSAGE);
			}
		}
		else{
			if(path.endsWith("bin")){
				gpp_path = path + "/g++";
			}
			else if(path.endsWith("bin/")){
				gpp_path = path + "g++";
			}
			else if(path.endsWith("MinGW")){
				gpp_path = path + "/bin/g++";
			}
			else if(path.endsWith("MinGW/")){
				gpp_path = path + "bin/g++";
			}
			else if(path.endsWith("MinGW32")){
				gpp_path = path + "/bin/g++";
			}
			else if(path.endsWith("MinGW32/")){
				gpp_path = path + "bin/g++";
			}
			else if(path.endsWith("MinGW64")){
				gpp_path = path + "/bin/g++";
			}
			else if(path.endsWith("MinGW64/")){
				gpp_path = path + "bin/g++";
			}
			else{
				System.out.println("Warning: failed setting the gpp_path");
				//JOptionPane.showMessageDialog(null, "failed to set the gpp_path", "WARN", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	//clear the gpp_path
	public static void clear_gpp_path(){
		gpp_path = "";
		System.out.println("Successful clear the gpp path");
		//JOptionPane.showMessageDialog(null, "successful clear", "", JOptionPane.OK_OPTION);
	}
	
	//check which system is, only for windows or linux system 
	private static boolean check(){
		String os = System.getProperties().getProperty("os.name");
		if(os.startsWith("win") || os.startsWith("Win")){
			return true;
		}
		return false;
	}
	
	//distinguish the kind of file, .c, .cpp, .src
	private static int check_file_kind(String file_path){
		if(file_path.endsWith(".c") || file_path.endsWith(".C")){
			return 1;
		}
		else if(file_path.endsWith(".cpp") || file_path.endsWith(".CPP")){
			return 2;
		}
		else if(file_path.endsWith(".src") || file_path.endsWith(".SRC")){
			return 3;
		}
		return 0;
	}
	
	//main compile function
	public static String compile(String file_path, String desti_path) {
		//check message
		System.out.println("Please ensure you have set the gpp_path"
				+ " or your computer can directly use g++ command in the CMD.exe");
		
		if(desti_path != ""){
			if(check()){
				String dir_cmd = "md " + desti_path;
				ExecuteWindowsCommand.execute(dir_cmd);
			}
			else{
				String dir_cmd = "mkdir -p " + desti_path;
				ExecuteLinuxCommand.execute(dir_cmd);
			}
		}
		
		//check file kind
		int filekind = check_file_kind(file_path);
		
		//-------------------------------------------.c file or .cpp file-------------------------------------------
		if(filekind == 1 || filekind == 2){
			String from = file_path;
			String to = desti_path;
			String filename = "";
			int dot_index = from.lastIndexOf('.');
			int filename_index;
			if(check()){
				filename_index = from.lastIndexOf('\\');
			}
			else{
				filename_index = from.lastIndexOf('/');
			}
			filename = from.substring(filename_index + 1, dot_index);
			if(to == ""){
				to = from.substring(0, dot_index);
			}
			else{
				if(to.endsWith("\\") || to.endsWith("/")){
					to += filename;
				}
				else if(check()){
					to += ("\\" + filename);
				}
				else{
					to += ('/' + filename);
				}
			}
			String compileCmd = "";
			//windows
			if(check()){
				if(gpp_path == ""){
					compileCmd = "g++ " + from + " -o " + to;
				}
				else{
					compileCmd = gpp_path + " " + from + " -o " + to;
				}
				String retCmd = ExecuteWindowsCommand.execute(compileCmd);
				String errorMsg = "\nThe result:\n" 
						+ retCmd
						+ "\nAny problems in the process:\n"
						+ "\t1.check the gpp_path\n"
						+ "\t2.check the file_path\n";
				System.out.println(errorMsg);
			}
			//linux
			else{
				if(gpp_path != ""){
					compileCmd = "g++ -o " + to + " " + from;
				}
				else{
					compileCmd = gpp_path + " -o " + to + " " + from;
				}
				String retCmd = ExecuteLinuxCommand.execute(compileCmd);
				String errorMsg = "\nThe result:\n" 
						+ retCmd
						+ "\nAny problems in the process:\n"
						+ "\t1.check the gpp_path\n"
						+ "\t2.check the file_path\n";
				System.out.println(errorMsg);
			}
			return to;
		}
		
		//-------------------------------------------.src file-------------------------------------------
		else if(filekind == 3){
			String filename = "";
			int src_dot = file_path.lastIndexOf('.');
			int src_filename;
			if(check()){
				src_filename = file_path.lastIndexOf('\\');
			}
			else{
				src_filename = file_path.lastIndexOf('/');
			}
			filename = file_path.substring(src_filename + 1, src_dot);
			String src_tmp = file_path.substring(0, src_dot);
			String src_newfile = src_tmp + "_src.cpp";
			ChangeFile.changefile(file_path, src_tmp);
			String from = src_newfile;
			String to = desti_path;
			if(to == ""){
				to = src_tmp;
			}
			else{
				if(to.endsWith("\\") || to.endsWith("/")){
					to += filename;
				}
				else if(check()){
					to += ("\\" + filename);
				}
				else{
					to += ("/" + filename);
				}
			}
			//windows
			if(check()){
				String src_cmd;
				//String src_cmd = "copy " + file_path + " " + src_newfile;
				//String src_ret = ExecuteWindowsCommand.execute(src_cmd);
				String compileCmd = "";
				if(gpp_path == ""){
					compileCmd = "g++ " + from + " -o " + to;
				}
				else{
					compileCmd = gpp_path + " " + from + " -o " + to;
				}
				String retCmd = ExecuteWindowsCommand.execute(compileCmd);
				String errorMsg = "\nThe result:\n" 
						+ retCmd
						+ "\nAny problems in the process:\n"
						+ "\t1.check the gpp_path\n"
						+ "\t2.check the file_path\n";
				System.out.println(errorMsg);
				src_cmd = "del " + src_newfile;
				retCmd = ExecuteWindowsCommand.execute(src_cmd);
			}
			//linux
			else{
				String src_cmd = "cp " + file_path + " " + src_newfile;
				String src_ret = ExecuteLinuxCommand.execute(src_cmd);
				String compileCmd = "";
				if(gpp_path != ""){
					compileCmd = "g++ -o " + to + " " + from;
				}
				else{
					compileCmd = gpp_path + " -o " + to + " " + from;
				}
				String retCmd = ExecuteLinuxCommand.execute(compileCmd);
				String errorMsg = "\nThe result:\n" 
						+ retCmd
						+ "\nAny problems in the process:\n"
						+ "\t1.check the gpp_path\n"
						+ "\t2.check the file_path\n";
				System.out.println(errorMsg);
				src_cmd = "del " + src_newfile;
				retCmd = ExecuteLinuxCommand.execute(src_cmd);
			}
			return to;
		}
		else{
			String errorMsg = "\nAny problems in the process:\n"
					+ "\t1.check the gpp_path\n"
					+ "\t2.check the file_path\n";
			System.out.println(errorMsg);
			return "";
		}
	}

	//overload function
	public static String compile(String file_path){
		System.out.println(file_path);
		return compile(file_path, work_dir);
	}
	
	//overload function
	public static String compile(String file_path, String desti_path, String GPP_path){
		set_gpp_path(GPP_path);
		return compile(file_path, desti_path);
	}
}
