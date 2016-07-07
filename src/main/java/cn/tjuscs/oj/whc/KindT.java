package cn.tjuscs.oj.whc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import cn.tjuscs.oj.cmdHelper.ExecuteWindowsCommand;

public class KindT extends FileKind {
	public final int MAX_LINE = 100000;
	public String[] inputFileLines = new String[MAX_LINE];
	public String[] outputFileLines = new String[MAX_LINE];
	public String[] tmp = new String[MAX_LINE];
	public String tmpFileName = "tempFile.in";
	public String Split = "split";
	public int inputFileRowLength, outputFileRowLength, curInLen, curOutLen, prvInLen, prvOutLen, FileIndex;

	public KindT(String sourcePath, String outputFilePath, String targetPath, String rightProPath) {
		super(sourcePath, outputFilePath, targetPath, rightProPath);
		// TODO Auto-generated constructor stub
	}

	@Override
	int judgeKind() throws IOException {
		// TODO Auto-generated method stub

		inputFileRowLength = outputFileRowLength = curInLen = curOutLen = prvInLen = prvOutLen = FileIndex = 0;
		// get in, out, exe
		sourceFilePath = "G:\\SI\\oj\\acmdata\\data\\1065.in";
		outputFilePath = "G:\\SI\\oj\\acmdata\\data\\1065.out";
		String exeFilename = compileFile();

		// judgefirst
		// if ipt is null at last
		// should throw a exception
		BufferedReader inputBufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFilePath)));
		inputFileLines[inputFileRowLength++] = inputBufferedReader.readLine();
		BufferedReader outputBufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(outputFilePath)));
		while ((outputFileLines[outputFileRowLength] = outputBufferedReader.readLine()) != null)
			outputFileRowLength++;
		int firstNum = getFirst(inputFileLines[0]);
		if (!(firstNum > 0))
			return Unknow_Kind;
		//long b = System.currentTimeMillis();

		// 修改inputFile的第一行的第一个数减1
		inputFileLines[0] = changeFirstNum(firstNum, inputFileLines[0], firstNum - 1);
		ExecuteWindowsCommand.execute("echo " + inputFileLines[0] + " > changeFirstNumInputFile.txt");
		BufferedWriter bufferWriter = new BufferedWriter(new FileWriter("changeFirstNumInputFile.txt", true));
		while ((inputFileLines[inputFileRowLength] = inputBufferedReader.readLine()) != null) {
			// cmd.execute("echo " + ipt + " >> changeFirstNumInputFile.txt ");
			// timelimit error
			bufferWriter.write(inputFileLines[inputFileRowLength]);
			bufferWriter.newLine();
			inputFileRowLength++;
		}
		bufferWriter.flush();
		bufferWriter.close();
		//long c = System.currentTimeMillis();

		// use the new input file to check answer
		// if not right return
		// get in to a file
		ExecuteWindowsCommand.execute(exeFilename + " < changeFirstNumInputFile.txt > changeFirstNumOutputFile.txt");
		String newOutputFile = "changeFirstNumOutputFile.txt";
		BufferedReader newOBR = new BufferedReader(new FileReader(newOutputFile));
		BufferedReader oldOBR = new BufferedReader(new FileReader(outputFilePath));
		String news = null, olds = null;
		while ((news = newOBR.readLine()) != null && (olds = oldOBR.readLine()) != null) {
			if (!news.equals(olds)) {
				System.out.println("not T");
				return Unknow_Kind;
			}
		}
		newOBR.close();
		oldOBR.close();
		//long d = System.currentTimeMillis(); //d-c stands for the cost time
		return T_Kind;
	}

	@Override
	public boolean splitFile() throws IOException, InterruptedException{
		// TODO Auto-generated method stub
		long a = System.currentTimeMillis();
		String exeFilename = compileFile();
		inputFileLines[0] = changeFirstNum(getFirst(inputFileLines[0]), inputFileLines[0], 1);
		String arg = new String(inputFileLines[0]);
		curInLen = 1;
		prvInLen = 1;
		for (int i = 1; i <= inputFileRowLength; i++) {
			for (; curInLen <= i; curInLen++) {
				arg = arg + "\n" + inputFileLines[curInLen];
			}
			
			BufferedWriter fout = new BufferedWriter(new FileWriter(tmpFileName));
			fout.write(arg);
			fout.flush();
			fout.close();
			fout = new BufferedWriter(new FileWriter("tempFile.out"));

			fout.close();

			ExecuteWindowsCommand.execute(exeFilename + " < tempFile.in > tempFile.out");
			// Thread.sleep(1000);
			// Character terminate;
			// terminate = 3;
			// cmd.execute(terminate.toString());
			BufferedReader getOut = new BufferedReader(new FileReader("tempFile.out"));
			while ((tmp[curOutLen] = getOut.readLine()) != null) {
				// System.out.println("in while" + tmp[curOutLen]);
				curOutLen++;
			}
			// System.out.println(curOutLen);
			if (curOutLen > prvOutLen && isprefix()) {
				fout = new BufferedWriter(new FileWriter(Split + FileIndex + ".in"));
				fout.write(inputFileLines[0]);
				for (; prvInLen < curInLen; prvInLen++) {
					fout.write("\n");
					fout.write(inputFileLines[i]);
					fout.flush();
				}
				fout.close();
				fout = new BufferedWriter(new FileWriter(Split + FileIndex + ".out"));
				for (; prvOutLen < curOutLen; prvOutLen++) {
					fout.write(outputFileLines[prvOutLen]);
					fout.write("\n");
					fout.flush();
				}
				fout.close();
				FileIndex++;
				// System.out.println("A file is created");
				arg = "1";
			}
			getOut.close();
		}
		long e = System.currentTimeMillis(); // e-a stands for cost time
		return true;
//		System.out.println(String.valueOf(b - a));
//		System.out.println(String.valueOf(c - a));
//		System.out.println(String.valueOf(d - a));
//		System.out.println(String.valueOf(e - a));
	}

	/**
	 * 把第一行的第一个个数从firstNum修改成aimNum
	 * 
	 * @param firstNum
	 *            第一行的第一个数
	 * @param s
	 *            input文件的第一个行
	 * @param aimNum
	 *            第一个数要修改成aimNum
	 * @return
	 */
	private String changeFirstNum(int firstNum, String s, int aimNum) {

		String afterSub = null, beforeSub = null;
		beforeSub = String.valueOf(firstNum);
		afterSub = String.valueOf(aimNum);
		// System.out.println(beforeSub + " " + afterSub);
		char[] c = s.toCharArray();
		int lena = afterSub.length(), lenb = beforeSub.length();
		for (int i = 0; i < lena; i++) {
			c[i] = afterSub.charAt(i);
		}
		for (int i = lena; i < lenb; i++)
			c[i] = ' ';
		return (new String(c));
	}

	private int getFirst(String string) {
		int num = 0;
		int len = string.length();
		char temp = ' ';
		for (int i = 0; i < len && i < 9; i++) {
			temp = string.charAt(i);
			if (charIsNum(temp)) {
				num = num * 10 + temp - '0';
			} else
				return -1;
		}
		return num;
	}

	private boolean charIsNum(char s) {
		if (s <= '9' && s >= '0')
			return true;
		else
			return false;
	}

	public boolean isprefix() {
		for (int i = prvOutLen; i < curOutLen; i++) {
			if (!outputFileLines[i].equals(tmp[i]))
				return false;
		}
		return true;
	}
	
}
