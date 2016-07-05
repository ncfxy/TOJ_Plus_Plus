package cn.tjuscs.oj.wyh;

import java.io.BufferedReader;
import java.io.File;

import javax.management.relation.RoleUnresolved;

import cn.tjuscs.oj.cmdHelper.ExecuteWindowsCommand;

public class CompareFile {
	protected String f1;
	protected String f2;
	protected File file1;
	protected File file2;
	protected String url = System.getProperty("user.dir");

    //增加，减少或者改动
    boolean isc = false;
    boolean isa = false;
    boolean isd = false;
    
   //reStrings中显示增加，减少或者改动信息的行数
    int a = -1;
    int d = -1;
    int c = -1;
    
    //修改开始的行数在字符串中的位置，第一个文件结束的行数在字符串中的位置，第二个文件结束的行数在字符串中的位置
    int cStart = -1;
    int cFirst = -1;
    int cSecond = -1;
    
    //diff工具返回的结果
    String re;
    String [] reStrings;
    
	protected CompareFile(String f1, String f2){
		this.f1 = url + "\\acmdata\\src\\" + f1;
		this.f2 = url + "\\acmdata\\src\\" + f2;
		file1 = new File(f1);
		file2 = new File(f2);
	}
	
	private String compare(){
		return ExecuteWindowsCommand.execute( url + "\\src\\main\\java\\cn\\tjuscs\\oj\\wyh\\diff.exe" + " " + f1 + ".src" + " " + f2 + ".src");
	}
	
	public void judge(){
		re = this.compare();
		System.out.println(re);
		reStrings = re.split("\n");
		for(int k = 0; k < reStrings.length; k++){
			String str1 = reStrings[k];
			if(str1.charAt(0) != '<' && str1.charAt(0) != '>' && str1.charAt(0) != '-'){
				for(int i = 0; i < str1.length();i++){
					if(str1.charAt(i) == 'd'){
						isd = true;
						d = k;
						break;
					}
					
					if (str1.charAt(i) == 'a') {
						isa = true;
						a = k;
						break;
					}
					
					if(str1.charAt(i) == 'c'){
						isc = true;
						c = k;
						cFirst = i - 1;
						cSecond = i + 1;
						break;
					}
				}
			}
			
		}
		
		
		if(isa == true){
			System.out.println("文件行数增加");
			System.out.println("增加内容为");
			for(int i = a + 1; i < reStrings.length; i++){
				if(c > a){
					if(reStrings[i].charAt(0) == '>' && (i < c) ){
						String str = reStrings[i].substring(2);
						System.out.println(str);
					}
				}else{
					if(reStrings[i].charAt(0) == '>'){
						String str = reStrings[i].substring(2);
						System.out.println(str);
					}
				}
			}
		}
		
		if(isd == true){
			System.out.println("文件行数减少");
			System.out.println("减少内容为");
			for(int i = d + 1; i < reStrings.length; i++){
				if(c > d){
					if(reStrings[i].charAt(0) == '<' && (i < c) ){
						String str = reStrings[i].substring(2);
						System.out.println(str);
					}
				}else{
					if(reStrings[i].charAt(0) == '>'){
						String str = reStrings[i].substring(2);
						System.out.println(str);
					}
				}
			}
		}
		
		if(isc == true){
			System.out.println("文件有修改");
			this.handleChange();
		}
	}
	
	public void handleChange(){
		int i;
		for(i = 0; i < cFirst; i++){
			if(reStrings[c].charAt(i) == ','){
				System.out.println("第一个文件的第" + reStrings[c].substring(0, i) + "行到第" + reStrings[c].substring(i+1, cFirst+1) +"行" );
				break;
			}
		}
		if(i == cFirst){
			System.out.println("第一个文件的第" + reStrings[c].substring(0, cFirst+1) + "行" );
		}
		
		for(i = c + 1; ; i++){
			if(reStrings[i].charAt(0) != '<')
				break;
			System.out.println(reStrings[i]);
		}  
		
		int n;
		for(n = cSecond; n < reStrings[c].length(); n++){
			if(reStrings[c].charAt(n) == ','){
				System.out.println("第二个文件的第" + reStrings[c].substring(cSecond, n) + "行到第" + reStrings[c].substring(n+1, reStrings[c].length()) +"行" );
				break;
			}
		}
		if(n == reStrings[c].length()){
			System.out.println("第二个文件的第" + reStrings[c].substring(cSecond, reStrings[c].length()) + "行" );
		}
		for(n = i + 1; n < reStrings.length && reStrings[n].charAt(0) == '>'; n++){
			System.out.println(reStrings[n]);
		}
	}
}
