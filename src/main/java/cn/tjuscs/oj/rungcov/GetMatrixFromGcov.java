package cn.tjuscs.oj.rungcov;

import javax.naming.spi.DirStateFactory.Result;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.naming.spi.ResolveResult;

public class GetMatrixFromGcov {

	public GetMatrixFromGcov(String [] argus) throws NumberFormatException, IOException{
		if(argus.length < 3){
			System.out.println("\\nParameters is not enough.Please input using this pattern: \\n\\t\"python getMatrixFromGcov programName versionNum testCaseNum\"\\n");
		}
		else{
			String programName = argus[0];
			String lenn = argus[1];
			String testNum = argus[2];
			for(int i = 1; i <= (Integer.valueOf(lenn) + 1); i++){
				parseGcov(i, programName, Integer.valueOf(testNum));
				System.out.println(i);
			}
		}
	}
	
	public void parseGcov(int version, String programName, int testNum) throws IOException{
		String sourceDir = System.getProperty("user.dir") + "\\src\\main\\java\\cn\\tjuscs\\oj\\rungcov";
		String outputFileName = sourceDir + "\\coverage_matrix.txt";
		String errorTestCasesFileName = sourceDir + "\\errorTestCases.txt";
		
		FileWriter outputFile = new FileWriter(outputFileName);
		BufferedWriter outputFout = new BufferedWriter(outputFile);
		
		FileWriter errorTestCasesFile = new FileWriter(errorTestCasesFileName);
		BufferedWriter errorTestCasesFout = new BufferedWriter(errorTestCasesFile);
		
		String outputStr = "";
		outputStr += "#Ver_# "+programName+'_'+String.valueOf(version)+"\n";
		outputStr += "#NOTS# "+String.valueOf(testNum)+"\n";
		outputStr += "#LOES# ";
		
		int executableLineNum = 0;
		String inputFileName = sourceDir + "\\" + programName + String.valueOf(1) + ".cpp.gcov";
		FileReader inputFile = new FileReader(inputFileName);
		BufferedReader inputFin = new BufferedReader(inputFile);
		
		String line = inputFin.readLine();
		while(line != null){
			if(line.charAt(13) == ' ' && line.charAt(14) == '0'){
				//do nothing
			}
			else{
				if(line.charAt(9) == ':' && line.charAt(15) == ':'){
					if(line.charAt(8) != '-'){
						executableLineNum++;
						outputStr += getLineIndex(line) + ' '; 
					}
				}
			}
			line = inputFin.readLine();
		}
		inputFile.close();
		outputStr += "\n"
				   + "#NOES# " + String.valueOf(executableLineNum) + "\n"
				   + "#NOF_# " + "\n"
				   + "#LOFS#" + "\n";
		for(int i = 1; i <= testNum + 1; i++){
			inputFileName = sourceDir + "\\" + programName + String.valueOf(i) + ".cpp.gcov";
			File file = new File(inputFileName);
			if(!file.exists()){
				errorTestCasesFile.append(String.valueOf(i));
				continue;
			}
			else{
				inputFile = new FileReader(inputFileName);
				inputFin = new BufferedReader(inputFile);
			}
			int lineIndex = 1;
			line = inputFin.readLine();
			while(line != null){
				if(line.charAt(13) == ' ' && line.charAt(14) == '0'){
					//do nothing
				}
				else{
					if(line.charAt(9) == ':' && line.charAt(15) == ':'){
						if(line.charAt(8) != '-'){
							if(line.charAt(8) != '#'){
								outputStr += "1 ";
								lineIndex++;
							}
							else{
								outputStr += "0 ";
								lineIndex++;
							}
						}
					}
				}
				line = inputFin.readLine();
			}
			outputStr += "\n";
			inputFile.close();
		}
		outputFile.write(outputStr);
		outputFile.flush();
		errorTestCasesFile.write('\n');
		errorTestCasesFile.flush();
		errorTestCasesFile.close();
		outputFile.close();
	}
	
	public String getLineIndex(String line){
		String result = "";
		for(int i = 10; i <= 15; i++){
			if(line.charAt(i) == ' '){
				 result += '0';
			}
			else{
				result += line.charAt(i);
			}
		}
		return result;
	}
	
	public String getString(int num){
		if(num < 10)
			return "0000" + String.valueOf(num);
		else if(num < 100)
			return "000" + String.valueOf(num);
		else if(num < 1000)
			return "00" + String.valueOf(num);
		else if(num < 10000)
			return "0" + String.valueOf(num);
		else
			return String.valueOf(num);
	}
	
	/*
	public int[][] getTestResult(String sourceDir, int x){
		int resultContent[][];
		String resultFileName = sourceDir + "/outputs/outputs.csv"; 
		
		return resultContent;
	}
	*/
}
