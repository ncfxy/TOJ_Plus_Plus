package cn.tjuscs.oj.whc;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;



public abstract class FileKind {
	protected String sourceFIlePath;
	protected String targetFilePath;
	protected String rightProPath;
	protected StringBuffer res;
	
	
	public final int Unknow_Kind = 0;
	public final int T_Kind = 1;
	public final int EOF_Kind = 2;
	public final int Sentinel_Kind = 3;
	
	
	
	public FileKind(String sourcePath, String targetPath, String rightProPath) {
		// TODO Auto-generated constructor stub
		this.sourceFIlePath = sourcePath;
		this.sourceFIlePath = targetPath;
		this.rightProPath = rightProPath;
		this.res = new StringBuffer();
		this.res.append("");
	}
	
	public String getResult(){
		if (this.res.toString() != "")
			return res.toString();
		else
			return "It is not Sentinel";
	}
	
	/**
	 * 
	 * @param inputFile.in 
	 * @param outputFile.out
	 * @param rightProExe
	 * @return kind of problem
	 * @throws IOException
	 */
	protected int judgeKind(String inputFilePath, String outputFilePath, String rightProExe ) throws IOException {
		int kind = Unknow_Kind;
		
		String sampleInputFileName = "sample.in";
		String sampleOutputFileName = "sample.out";
		
		File sampleOutputFile = new File(sampleOutputFileName);
		File sampleInputFile = new File(sampleInputFileName);
		creatFile(sampleInputFile);
		creatFile(sampleOutputFile);
		
		RandomAccessFile inputFileReader = null;
		RandomAccessFile sampleInputFileWriter = null;
		
		inputFileReader = new RandomAccessFile(inputFilePath, "rw");
		sampleInputFileWriter = new RandomAccessFile(sampleInputFile, "rw");
		
		boolean flag = false;
		int cnt = 0;
		long[] inputFilePtr = null;
		inputFilePtr = new long[1100];
		
		while(inputFileReader.getFilePointer() != inputFileReader.length()){
			inputFilePtr[cnt++] = inputFileReader.getFilePointer();
			inputFileReader.readLine();
		}
		
		int cp;
		String tmp = null;
		
		for(cp = cnt-11; cp < cnt; cp ++){
			tmp = null;
			inputFileReader.seek(0);
			while(inputFileReader.getFilePointer() < inputFilePtr[cp]){
				tmp = inputFileReader.readLine();
				sampleInputFileWriter.writeBytes(tmp);
				sampleInputFileWriter.writeBytes("\n");
			}
			
			String command = rightProExe + " < " + sampleInputFileName + " > " + sampleOutputFileName;
			Runtime rn = Runtime.getRuntime();
			rn.exec(command);
			
			if(cmpFiles(sampleOutputFileName, outputFilePath)){
				break;
			}
		}
		
		while(inputFileReader.getFilePointer() < inputFileReader.length()){
			tmp = inputFileReader.readLine().trim();
			if(tmp != ""){
				res.append(tmp);
				flag = true;
			}
		}
		
		if(flag)
			kind = Sentinel_Kind;
		else
			kind = Unknow_Kind;
		
		
		/*while(!flag || inputFileReader.getFilePointer() == inputFileReader.length()){
			tmp = null;
			tmp = inputFileReader.readLine();
			sampleInputFileWriter.writeBytes(tmp);
			sampleInputFileWriter.writeBytes("\n");
			String command = rightProExe + " < " + sampleInputFileName + " > " + sampleOutputFileName;
			
			Runtime rn = Runtime.getRuntime();
			rn.exec(command);
			System.out.println(cnt++);
			//ExecuteWindowsCommand.execute(command);
			
			if(cmpFiles(sampleOutputFileName, outputFilePath)){
				flag = true;
			}
		}
		
		
		if(inputFileReader.getFilePointer() == inputFileReader.length())
			kind = Unknow_Kind;
		else {
			String string;
			this.res = new StringBuffer();
			while(inputFileReader.getFilePointer() < inputFileReader.length()){
				string = inputFileReader.readLine();
				res.append(string);
				res.append("\n");
			}
			kind = Sentinel_Kind;
		}
		*/
		inputFileReader.close();
		sampleInputFileWriter.close();
		return kind;
	}
	/**
	 * 
	 * @param fileName
	 */
	private void creatFile(File file) {
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
	private boolean cmpFiles(String sourceFileName, String targetFileName) throws IOException
	{
		
		boolean res = true;
		RandomAccessFile sourceFileReader = new RandomAccessFile(new File(sourceFileName), "rw");
		RandomAccessFile targetFileReader = new RandomAccessFile(new File(targetFileName), "rw");
		
		while(sourceFileReader.getFilePointer() < sourceFileReader.length()
				&& targetFileReader.getFilePointer() < targetFileReader.length()){
			if(!sourceFileReader.readLine().trim().equals(targetFileReader.readLine().trim())){
				res = false;
				break;
			}
		}
		if(sourceFileReader.getFilePointer() < sourceFileReader.length()
				|| targetFileReader.getFilePointer() < targetFileReader.length())
			res = false;
		
		sourceFileReader.close();
		targetFileReader.close();
		return res;
	}
	
	abstract boolean splitFile();
}
