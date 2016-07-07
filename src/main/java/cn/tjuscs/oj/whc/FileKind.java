package cn.tjuscs.oj.whc;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/*
 * FileKind作为基类。
 */

public abstract class FileKind {
	protected String sourceFilePath; //需要拆分的用例的源文件地址
	protected String outputFilePath; //正确的输出文件（.out文件）
	protected String targetFilePath; //拆分后的样例的存放地址
	protected String rightProPath; //正确运行的程序地址
	protected StringBuffer res;
	
	
	public final int Unknow_Kind = 0; 
	public final int T_Kind = 1;
	public final int EOF_Kind = 2;
	public final int Sentinel_Kind = 3;
	
	//constructor
	public FileKind(String sourcePath, String outputFilePath, String targetPath, String rightProPath) {
		// TODO Auto-generated constructor stub
		this.sourceFilePath = sourcePath;
		this.outputFilePath = outputFilePath;
		this.targetFilePath = targetPath;
		this.rightProPath = rightProPath;
		this.res = new StringBuffer();
		this.res.append("");
	}
	

	
	/**
	 * 
	 * @param inputFile.in 
	 * @param outputFile.out
	 * @param rightProExe
	 * @return kind of problem
	 * @throws IOException
	 */
	abstract int judgeKind() throws IOException;
	/**
	 * 
	 * @param fileName
	 */
	protected void creatFile(File file) {
		try {
			if(!file.exists())
				file.createNewFile();
			else{
				file.delete();
				file.createNewFile();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected boolean cmpFiles(String sourceFileName, String targetFileName) throws IOException
	{
		boolean flag = true;
		RandomAccessFile sourceFileReader = new RandomAccessFile(new File(sourceFileName), "rw");
		RandomAccessFile targetFileReader = new RandomAccessFile(new File(targetFileName), "rw");
		
		while(sourceFileReader.getFilePointer() < sourceFileReader.length()
				&& targetFileReader.getFilePointer() < targetFileReader.length()){
			if(!sourceFileReader.readLine().trim().equals(targetFileReader.readLine().trim())){
				flag = false;
				break;
			}
		}
		if(sourceFileReader.getFilePointer() < sourceFileReader.length()
				|| targetFileReader.getFilePointer() < targetFileReader.length())
			flag = false;
		sourceFileReader.close();
		targetFileReader.close();
		return flag;
	}
	abstract boolean splitFile() throws IOException, InterruptedException;
	
	String compileFile(){
		
		return "D:\\application\\eclipse\\WorkSpace\\TOJ_Plus_Plus\\src\\main\\java\\exe\\toj1007.exe";
	}
}
