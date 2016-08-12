package cn.tjuscs.oj.dividing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import cn.tjuscs.oj.cmdHelper.ExecuteWindowsCommand;

public class KindEOF extends FileKind{
	public final int MAX_LINE = 100000;
	public String[] ipt = new String[MAX_LINE];
	public String[] opt = new String[MAX_LINE];
	public String[] tmp = new String[MAX_LINE];
	public String tmpFileName = "tempFile.in";
	public String Split = "split";
	public int IFLen,OFLen,curInLen,curOutLen,prvInLen,prvOutLen,FileIndex;
	public List<String> DonePaths;
	
	public KindEOF(String sourcePath, String outputFilePath, String targetPath, String rightProPath) {
		super(sourcePath, outputFilePath, targetPath, rightProPath);
		// TODO Auto-generated constructor stub
	}

	public KindEOF(String pid, String sid) throws IOException {
		// TODO Auto-generated constructor stub
		super(pid, sid);
	}

	@Override
	int judgeKind() throws IOException {
		// TODO Auto-generated method stub
		return EOF_Kind;
	}

	@Override
	boolean splitFile() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		String  ExName = compile(this.rightProPath);
		IFLen = OFLen = curInLen = curOutLen = prvInLen = prvOutLen = FileIndex = 0;
		FileReader ifst = new FileReader(sourceFilePath);
		FileReader ofst = new FileReader(outputFilePath);
		BufferedReader IFin = new BufferedReader(ifst);
		BufferedReader OFin = new BufferedReader(ofst);
		while((ipt[IFLen]=IFin.readLine()) != null){
			IFLen++;
		}
		while((opt[OFLen]=OFin.readLine()) != null){
			OFLen++;
		}
		String arg = new String("");
		
		arg = ipt[0];
		curInLen = 1;
		for(int i=1;i<=IFLen;i++){
			curOutLen = 0;
			for(;curInLen<i;curInLen++){
				arg = arg + "\n" + ipt[curInLen];
			}
			
			BufferedWriter fout = new BufferedWriter(new FileWriter(tmpFileName));
			fout.write(arg);
			fout.flush();
			fout.close();
			fout = new BufferedWriter(new FileWriter("tempFile.out"));
			fout.close();
			
			ExecuteWindowsCommand.execute(ExName+" < tempFile.in > tempFile.out" + "\n");
//			Thread.sleep(1000);
			Character terminate;
			terminate = 3;
			ExecuteWindowsCommand.execute(terminate.toString());
			BufferedReader getOut = new BufferedReader(new FileReader("tempFile.out"));
			while( (tmp[curOutLen]=getOut.readLine()) != null ){
				System.out.println(tmp[curOutLen]);
				curOutLen++;
			}
//			System.out.println(curOutLen);
			if(curOutLen > prvOutLen && isprefix()){
				fout = new BufferedWriter(new FileWriter(Split+FileIndex+".out"));
				DonePaths.add(new String(Split+FileIndex+".out"));
				for(;prvInLen < curInLen;prvInLen++){
					fout.write(ipt[prvInLen]);
					fout.write("\n");
					fout.flush();
				}
				FileIndex++;
				prvOutLen = curOutLen;
				System.out.println("A file is created");
			}
			getOut.close();
		}
		IFin.close();
		OFin.close();
		BufferedWriter num = new BufferedWriter(new FileWriter(this.targetFilePath + "\\" + this.pid + "_total.txt"));
		num.write(String.valueOf(FileIndex));
		num.flush();
		num.close();
		//return DonePaths;
		return false;
	}
	public List<String> getPathes(){
		return this.DonePaths;
	}
	
	public boolean isprefix(){
		for(int i=0;i<curOutLen;i++){
			if(!opt[i].equals(tmp[i])) return false;
		}
		return true;
	}
}
