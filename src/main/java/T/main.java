package T;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.sql.Date;
import java.sql.Time;

import com.sun.xml.internal.bind.v2.runtime.RuntimeUtil.ToStringAdapter;

import cn.tjuscs.oj.cmdHelper.ExecuteWindowsCommand;

public class main{
	
	//if begains with " case "#": "
	//GG
	public static final int MAX_LINE = 100000;
	public static String[] ipt = new String[MAX_LINE];
	public static String[] opt = new String[MAX_LINE];
	public static String[] tmp = new String[MAX_LINE];
	public static String tmpFiIFLename = "tempFile.in";
	public static String Split = "split";
	public static int IFLen,OFLen,curInLen,curOutLen,prvInLen,prvOutLen,FileIndex;
	
	
	public static void main (String[] args) throws IOException, InterruptedException{
		long a = System.currentTimeMillis();
		IFLen = OFLen = curInLen = curOutLen = prvInLen = prvOutLen = FileIndex = 0;	
		//get in, out, exe
		String inFilename, outFilename, exeFilename;
		inFilename = "G:\\SI\\oj\\acmdata\\data\\1065.in";
		outFilename = "G:\\SI\\oj\\acmdata\\data\\1065.out";
		exeFilename = "G:\\SI\\oj\\acmdata\\1065T\\1052.exe";
		
		//judgefirst
		//if ipt is null at last
		//should throw a exception
		BufferedReader inBR = new BufferedReader(new InputStreamReader(new FileInputStream(inFilename)));
		ipt[IFLen ++] = inBR.readLine();
		BufferedReader outBR = new BufferedReader(new InputStreamReader(new FileInputStream(outFilename)));
		while((opt[OFLen] = outBR.readLine()) != null) OFLen ++;
		int firstNum = getFirst(ipt[0]);
		if(!(firstNum > 0))
			return;
		long b = System.currentTimeMillis();
		
		ipt[0] = changeFirstNum(firstNum, ipt[0], firstNum - 1);
		ExecuteWindowsCommand cmd = null;		
		cmd.execute("echo " + ipt[0] + " > changeFirstNumInputFile.txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter("changeFirstNumInputFile.txt", true));
		while((ipt[IFLen] = inBR.readLine()) != null){
			//cmd.execute("echo " + ipt + " >> changeFirstNumInputFile.txt ");
			//timelimit error
			bw.write(ipt[IFLen]);
			bw.newLine();
			IFLen++;
		}
		bw.flush();
		bw.close();
		long c = System.currentTimeMillis();
		
		//use the new input file to check answer
		//if not right return 
		//get in to a file
		cmd.execute(exeFilename + " < changeFirstNumInputFile.txt > changeFirstNumOutputFile.txt");
		String newOutputFile = "changeFirstNumOutputFile.txt";
		BufferedReader newOBR = new BufferedReader(new FileReader(newOutputFile));
		BufferedReader oldOBR = new BufferedReader(new FileReader(outFilename));
		String news = null, olds = null;
		while((news = newOBR.readLine()) != null && (olds = oldOBR.readLine()) != null){
			if(!news.equals(olds)){
				System.out.println("not T");
				return ;
			}
		}
		newOBR.close();
		oldOBR.close();
		long d = System.currentTimeMillis();
		
		ipt[0] = changeFirstNum(getFirst(ipt[0]), ipt[0], 1);
		String arg = new String(ipt[0]);
		curInLen = 1;
		prvInLen = 1;
		for(int i = 1; i <= IFLen; i ++){
 			for(; curInLen <= i; curInLen ++){
				arg = arg + "\n" + ipt[curInLen];
			}
			
			BufferedWriter fout = new BufferedWriter(new FileWriter(tmpFiIFLename));
			fout.write(arg);
			fout.flush();
			fout.close();
			fout = new BufferedWriter(new FileWriter("tempFile.out"));
			
			fout.close();
			
			cmd.execute(exeFilename + " < tempFile.in > tempFile.out");
//			Thread.sleep(1000);
//			Character terminate;
//			terminate = 3;
//			cmd.execute(terminate.toString());
			BufferedReader getOut = new BufferedReader(new FileReader("tempFile.out"));
			while( (tmp[curOutLen]=getOut.readLine()) != null ){
//				System.out.println("in while" + tmp[curOutLen]);
				curOutLen ++;
			}
//			System.out.println(curOutLen);
			if(curOutLen > prvOutLen && isprefix()){
				fout = new BufferedWriter(new FileWriter(Split+FileIndex+".in"));
				fout.write(ipt[0]);
				for(; prvInLen < curInLen; prvInLen ++){
					fout.write("\n");
					fout.write(ipt[i]);
					fout.flush();
				}
				fout.close();
				fout = new BufferedWriter(new FileWriter(Split + FileIndex + ".out"));
				for(; prvOutLen < curOutLen; prvOutLen ++){
					fout.write(opt[prvOutLen]);
					fout.write("\n");
					fout.flush();
				}
				fout.close();
				FileIndex++;
//				System.out.println("A file is created");
				arg = "1";
			}
			getOut.close();
		}
	long e = System.currentTimeMillis();
	System.out.println(String.valueOf(b - a));
	System.out.println(String.valueOf(c - a));
	System.out.println(String.valueOf(d - a));
	System.out.println(String.valueOf(e - a));
		return;
	}
	
	private static String changeFirstNum(int firstNum, String s, int aimNum) {
		
		String afterSub = null, beforeSub = null;
		beforeSub = beforeSub.valueOf(firstNum);
		afterSub = afterSub.valueOf(aimNum);
//		System.out.println(beforeSub + " " + afterSub);
		char[] c = s.toCharArray();
		int lena = afterSub.length(), lenb = beforeSub.length();
		for(int i = 0; i < lena; i ++){
			c[i] = afterSub.charAt(i);
		}
		for(int i = lena; i < lenb; i ++)
			c[i] = ' ';
		return (new String(c));
	}
	
	private static int getFirst(String string) {
		int num = 0;
		int len = string.length();
		char temp = ' ';
		for(int i = 0; i < len && i < 9; i ++){
			temp = string.charAt(i);
			if(charIsNum(temp)){
				num = num * 10 + temp - '0';
			}
			else
				return -1;
		}
		return num;
	}
	
	private static boolean charIsNum(char s){
		if(s <= '9' && s >= '0')
			return true;
		else
			return false;
	}
	
	public static boolean isprefix(){
		for(int i = prvOutLen; i < curOutLen; i ++){
			if(!opt[i].equals(tmp[i])) return false;
		}
		return true;
	}
}