
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
import java.sql.Date;
import java.sql.Time;

import com.sun.xml.internal.bind.v2.runtime.RuntimeUtil.ToStringAdapter;

public class Doudiv{
	
	//if begains with " case "#": "
	//GG
	public static final int MAX_LINE = 100000;
	public static String[] ipt = new String[MAX_LINE];
	public static String[] opt = new String[MAX_LINE];
	public static String[] tmp = new String[MAX_LINE];
	public static String tmpFiIFLename = "tempFile.in";
	public static String Split = "split";
	public static int IFLen,OFLen,curInLen,curOutLen,prvInLen,prvOutLen,FileIndex;
	public static void doudiv (String[] args) throws IOException, InterruptedException{
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
		ipt[IFLen++] = inBR.readLine();
		int firstNum = getFirst(ipt[0]);
		if(!(firstNum > 0))
			return;
		
		ipt[0] = changeFirstNum(firstNum, ipt[0], firstNum - 1);
		ExecuteWindowsCommand cmd = null;		
		cmd.execute("echo " + ipt + " > changeFirstNumInputFile.txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter("changeFirstNumInputFile.txt", true));
		while((ipt[IFLen] = inBR.readLine()) != null){
			//cmd.execute("echo " + ipt + " >> changeFirstNumInputFile.txt ");
			//timelimit error
			bw.write(ipt[IFLen]);
			bw.newLine();
			IFLen++;
		}
		bw.close();
		
		//use the new input file to check answer
		//if not right return 
		//get in to a file
		cmd.execute(exeFilename + " < changeFirstNumInputFile.txt > changeFirstNumOutputFile.txt");
		cmd.execute("taskkill /f /im " + exeFilename);
		String newOutputFile = "changeFirstNumOutputFile.txt";
		BufferedReader newOBR = new BufferedReader(new FileReader(newOutputFile));
		BufferedReader oldOBR = new BufferedReader(new FileReader(outFilename));
		String news = null, olds = null;
		while((news = newOBR.readLine()) != null && (olds = oldOBR.readLine()) != null){
			if(!news.equals(olds)){
				return ;
			}
		}
		newOBR.close();
		oldOBR.close();
		
		ipt[0] = changeFirstNum(firstNum, ipt[0], 1);
		String arg = new String(ipt[0]);
		prvInLen = 1;
		for(int i=1;i<=IFLen;i++){
			curOutLen = 0;
			for(;curInLen<i;curInLen++){
				arg = arg + "\n" + ipt[curInLen];
			}
			
			BufferedWriter fout = new BufferedWriter(new FileWriter(tmpFiIFLename));
			fout.write(arg);
			fout.flush();
			fout.close();
			fout = new BufferedWriter(new FileWriter("tempFile.out"));
			fout.close();
			
			cmd.execute("apb.exe < tempFile.in > tempFile.out" + "\n");
			Thread.sleep(1000);
			Character terminate;
			terminate = 3;
			cmd.execute(terminate.toString());
			BufferedReader getOut = new BufferedReader(new FileReader("tempFile.out"));
			while( (tmp[curOutLen]=getOut.readLine()) != null ){
				System.out.println(tmp[curOutLen]);
				curOutLen++;
			}
			System.out.println(curOutLen);
			if(curOutLen > prvOutLen && isprefix()){
				fout = new BufferedWriter(new FileWriter(Split+FileIndex+".out"));
				fout.write(ipt[0]);
				for(;prvInLen < curInLen;prvInLen++){
					fout.write(ipt[prvInLen]);
					fout.write("\n");
					fout.flush();
				}
				FileIndex++;
				prvOutLen = curOutLen;
				System.out.println("A file is created");
				arg = ipt[0];
			}
			getOut.close();
		}
		
		
		
		
		
		//for(0 -> IFLength())
		//fileout(add) string till output equals for last time
		//if not equals until end   return 0 && delete created files
//		inBR.close();
//		inBR = new BufferedReader(new InputStreamReader(new FileInputStream(inFilename)));
//		String tmpString = new String("");
//		tmpString = inBR.readLine();
//		tmpString = changeFirstNum(getFirst(tmpString), tmpString, 1);
//		int num = 0;
//		String brOut = "outAfterDividing";
//		String brIn = "inAfterDividing";
//		(new FileOutputStream(brIn + String.valueOf(num) + ".txt")).flush();
//		(new FileOutputStream(brIn + String.valueOf(num) + ".txt")).close();
//		BufferedWriter outBW = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(brIn + String.valueOf(num) + ".txt")));
//		outBW.write(tmpString);
//		while((tmpString = inBR.readLine()) != null){
//			outBW.newLine();
//			outBW.write(tmpString);
//		}
//		outBW.flush();
//		cmd.execute(exeFilename + " < " + brIn + String.valueOf(num) + ".txt > " + brOut + String.valueOf(num) + ".txt");
//		cmd.execute("taskkill /f /im " + exeFilename);
		
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
		for(int i=0;i<curOutLen;i++){
			if(!opt[i].equals(tmp[i])) return false;
		}
		return true;
	}
}