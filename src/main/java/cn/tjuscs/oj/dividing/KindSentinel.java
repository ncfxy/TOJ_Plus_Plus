package cn.tjuscs.oj.dividing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import cn.tjuscs.oj.cmdHelper.ExecuteWindowsCommand;



public class KindSentinel extends FileKind {

	//rightpropath为exe文件的目录
	//所有的参数均为相对路径
	public KindSentinel(String sourcePath, String outputFilePath, String targetPath, String rightProPath) {
		super(sourcePath, outputFilePath, targetPath, rightProPath);
		// TODO Auto-generated constructor stub
	}

	public KindSentinel(String pid, String sid) throws IOException {
		// TODO Auto-generated constructor stub
		super(pid, sid);
	}

	@Override
	public int judgeKind() throws IOException{
		// TODO Auto-generated method stub
		int kind = Unknow_Kind;
		
		String sampleInputFileName = this.targetFilePath + "\\sample.in";
		String sampleOutputFileName = this.targetFilePath + "\\sample.out";
		
		File sampleOutputFile = new File(sampleOutputFileName);
		
		creatFile(sampleOutputFile);
		
		RandomAccessFile inputFileReader = null;
		RandomAccessFile sampleInputFileWriter = null;
		
		inputFileReader = new RandomAccessFile(sourceFilePath, "rw");
		
		
		boolean flag = false;
		
		ArrayList<Long> inputFilePtr = new ArrayList<Long>();
		
		while(inputFileReader.getFilePointer() != inputFileReader.length()){
			inputFilePtr.add(inputFileReader.getFilePointer());
			inputFileReader.readLine();
		}
		
		int cp;
		int cnt = inputFilePtr.size();
		String tmp = null;
		
		for(cp = cnt-11; cp < cnt; cp ++){
			
			File sampleInputFile = new File(sampleInputFileName);
			creatFile(sampleInputFile);
			sampleInputFileWriter = new RandomAccessFile(sampleInputFile, "rw");

			inputFileReader.seek(0);
			while(inputFileReader.getFilePointer() < inputFilePtr.get(cp)){
				tmp = inputFileReader.readLine();
				sampleInputFileWriter.writeBytes(tmp);
				sampleInputFileWriter.writeBytes("\n");
			}
			String command = this.rightExePath + " < " + sampleInputFileName + " > " + sampleOutputFileName;
			
			//Runtime rn = Runtime.getRuntime();
			//rn.exec(command);
			ExecuteWindowsCommand.execute(command);
			if(cmpFiles(sampleOutputFileName, outputFilePath)){
				break;
			}
		}
		
		while(inputFileReader.getFilePointer() < inputFileReader.length()){
			tmp = inputFileReader.readLine().trim();
			if(!tmp.isEmpty()){
				res.append(tmp);
				res.append(" ");
				flag = true;
			}
		}
		
		if(flag)
			kind = Sentinel_Kind;
		else
			kind = Unknow_Kind;
		inputFileReader.close();
		sampleInputFileWriter.close();
		
		return kind;
	}
	
	public String getResult(){
		if (this.res.toString() != "")
			return res.toString();
		else
			return "It is not Sentinel";
	}
	
	
//	@Override
//	boolean splitFile() throws IOException {
//		// TODO Auto-generated method stub
//		
//		File tmp = new File(sourceFilePath.trim());
//		String fileName = tmp.getName();
//		int dot = fileName.lastIndexOf('.');   
//        if ((dot >-1) && (dot < (fileName.length()))) {   
//            fileName = fileName.substring(0, dot);   
//        }
//		if(this.res.toString() == "")
//			return false;
//		
//		String outFileName = this.targetFilePath + "\\sample1.out";
//		String inFileName = this.targetFilePath + "\\sample1.in";
//		File outFile = new File(outFileName);
//		//File rightOutputFile = new File(outputFilePath);
//		creatFile(outFile);
//		
//		
//		RandomAccessFile sourceFileReader = new RandomAccessFile(new File(sourceFilePath), "rw");
//		
//		long prePtr = 0;
//		long curPtr = 0;
//		int index = 1;
//		while(prePtr < sourceFileReader.length()){
//			
//			File inFile = new File(inFileName);
//			creatFile(inFile);
//			RandomAccessFile sampleInputFile = new RandomAccessFile(inFile, "rw");
//			sourceFileReader.seek(0);
//			while(sourceFileReader.getFilePointer() < prePtr){
//				sampleInputFile.writeBytes(sourceFileReader.readLine().trim());
//				sampleInputFile.writeBytes("\n");
//			}
//			do{
//				sampleInputFile.writeBytes(sourceFileReader.readLine().trim());
//				sampleInputFile.writeBytes("\n");
//				String command = this.rightExePath + " < " + inFileName + " > " + outFileName;
//				//System.out.println(command);
//				//Runtime rn = Runtime.getRuntime();
//				//rn.exec(command);
//				ExecuteWindowsCommand.execute(command);
//			}while(outFile.length()== prePtr 
//					&& sourceFileReader.getFilePointer()<sourceFileReader.length());
//			curPtr = sourceFileReader.getFilePointer();
//			
//			
//			//String pathin = outputFilePath + fileName + "_" + index + ".in";
//			
//			
//			String pathin = this.targetFilePath + "\\" + fileName + "_" + index + ".in";
//			String pathout = this.targetFilePath + "\\" + fileName + "_" + index + ".out";
//			File splitedFiles = new File(pathin);
//			File outSplitedFiles = new File(pathout);
//			
//			if(!splitedFiles.getParentFile().exists()){
//				splitedFiles.getParentFile().mkdir();
//			}
//			
//			creatFile(splitedFiles);
//			creatFile(outSplitedFiles);
//			
//			RandomAccessFile fileWriter = new RandomAccessFile(splitedFiles, "rw");
//			sourceFileReader.seek(prePtr);
//			while(sourceFileReader.getFilePointer() < curPtr){
//				fileWriter.writeBytes(sourceFileReader.readLine().trim());
//				fileWriter.writeBytes("\n");
//			}
//			fileWriter.writeBytes(res.toString());
//			fileWriter.close();
//			
//			
//			String command = this.rightExePath + " < " + pathin + " > " + pathout;
//			ExecuteWindowsCommand.execute(command);
//			
//			index ++;
//			prePtr = curPtr;
//			sampleInputFile.close();
//			
//		}
//		
//		sourceFileReader.close();
//		return true;
//	}
	
	@Override
	boolean splitFile() throws IOException {
		// TODO Auto-generated method stub
		
		String outFileName = this.targetFilePath + "sample1.out";
		String inFileName = this.targetFilePath + "sample1.in";
		File outFile = new File(outFileName);
		//File rightOutputFile = new File(outputFilePath);
		creatFile(outFile);
		
		
		RandomAccessFile sourceFileReader = new RandomAccessFile(new File(sourceFilePath), "rw");
		
		long prePtr = 0;
		long curPtr = 0;
		int index = 0;
		while(prePtr < sourceFileReader.length()){
			
			File inFile = new File(inFileName);
			creatFile(inFile);
			RandomAccessFile sampleInputFile = new RandomAccessFile(inFile, "rw");
			sourceFileReader.seek(0);
			while(sourceFileReader.getFilePointer() < prePtr){
				sampleInputFile.writeBytes(sourceFileReader.readLine().trim());
				sampleInputFile.writeBytes("\n");
			}
			do{
				sampleInputFile.writeBytes(sourceFileReader.readLine().trim());
				sampleInputFile.writeBytes("\n");
				String command = this.rightExePath + " < " + inFileName + " > " + outFileName;
				//System.out.println(command);
				//Runtime rn = Runtime.getRuntime();
				//rn.exec(command);
				ExecuteWindowsCommand.execute(command);
			}while(outFile.length()== prePtr 
					&& sourceFileReader.getFilePointer()<sourceFileReader.length());
			curPtr = sourceFileReader.getFilePointer();
			
			
			//String pathin = outputFilePath + fileName + "_" + index + ".in";
			
			
			String pathin = this.targetFilePath + "\\" + this.pid + "_" + index + ".in";
			String pathout = this.targetFilePath + "\\" + this.pid + "_" + index + ".out";
			File splitedFiles = new File(pathin);
			File outSplitedFiles = new File(pathout);
			
			if(!splitedFiles.getParentFile().exists()){
				splitedFiles.getParentFile().mkdir();
			}
			
			creatFile(splitedFiles);
			creatFile(outSplitedFiles);
			
			RandomAccessFile fileWriter = new RandomAccessFile(splitedFiles, "rw");
			sourceFileReader.seek(prePtr);
			while(sourceFileReader.getFilePointer() < curPtr){
				fileWriter.writeBytes(sourceFileReader.readLine().trim());
				fileWriter.writeBytes("\n");
			}
			fileWriter.writeBytes(res.toString());
			fileWriter.close();
			
			String command = this.rightExePath + " < " + pathin + " > " + pathout;
			System.out.println(command);
			ExecuteWindowsCommand.execute(command);
			
			index ++;
			prePtr = curPtr;
			sampleInputFile.close();
			
		}
		
		BufferedWriter num = new BufferedWriter(new FileWriter(this.targetFilePath + "\\" + this.pid + "_total.txt"));
		num.write(String.valueOf(index));
		num.flush();
		num.close();
		
		
		sourceFileReader.close();
		return true;
	}
}
