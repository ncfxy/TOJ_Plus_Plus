package com.ncfxy.FaultLocalization;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Experiment {

	private String basePath;
	private String programName;
	private String version; // v1、v2、v3
	private String coverageFileName = "output102.txt";
	private String absoluteCoverageFilePath;

	private String versionName;
	private Integer numberOfTestCases;// NOTS;
	private List<Integer> locationsOfExecutableEntities;// LOES
	private Integer numberOfExecutableEntities;// NOES;
	private Integer numberOfFaults; // NOF_
	private List<Integer> locationsOfFaults; // LOFS
	private List<Integer> indexOfFaults;
	private List<List<Byte>> coverageMatrix;
	private List<List<Byte>> passCoverageMatrix;
	private List<List<Byte>> failCoverageMatrix;
	private List<Boolean> resultVector;
	private List<Integer> deeplyCompressRelation;

	public Experiment(String basePath, String programName, String version, String coverageFileName, String absoluteCoverageFilePath) {
		super();
		this.basePath = basePath;
		this.programName = programName;
		this.version = version;
		this.coverageFileName = coverageFileName;
		this.absoluteCoverageFilePath = absoluteCoverageFilePath;
	}
	
	

	public boolean isFaultLine(Integer i) {
		for (Integer x : indexOfFaults) {
			if (x.equals(i)) {
				return true;
			}
		}
		return false;
	}

	public boolean isMultipleFaults() {
		if (numberOfFaults > 1) {
			return true;
		}
		return false;
	}
	
	/**
	 * 从压缩后的文件中读取覆盖信息
	 */
	public void readFromCompressedFile() throws FileNotFoundException{
		File coverageFile = new File(basePath + "/" + programName + "/" + version + "/compressed_coverage_matrix.txt");
		FileInputStream fileInputStream = new FileInputStream(coverageFile);
		InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
		Scanner cin = new Scanner(fileInputStream);
		List<String> lines = new ArrayList<String>();
		while (cin.hasNext()) {
			String tmp = cin.nextLine();
			lines.add(tmp);
		}
		versionName = lines.get(0).substring(7);
		numberOfTestCases = Integer.parseInt(lines.get(1).substring(7));
		
		
		String tmp = lines.get(5).substring(7);
		String tmps[] = tmp.split(" ");
		locationsOfExecutableEntities = new ArrayList<Integer>();
		for (String s : tmps) {
			locationsOfExecutableEntities.add(Integer.parseInt(s));
		}
		numberOfExecutableEntities = Integer.parseInt(lines.get(2).substring(7));
		numberOfFaults = Integer.parseInt(lines.get(3).substring(7));
		tmp = lines.get(4).substring(7);
		String tmps2[] = tmp.split(" ");
		locationsOfFaults = new ArrayList<Integer>();
		indexOfFaults = new ArrayList<Integer>();
		for (String s : tmps2) {
			int tempInt = Integer.parseInt(s);
			//locationsOfFaults.add(tempInt);
			indexOfFaults.add(tempInt);
		}
		coverageMatrix = new ArrayList<List<Byte>>();
		passCoverageMatrix = new ArrayList<List<Byte>>();
		failCoverageMatrix = new ArrayList<List<Byte>>();
		resultVector = new ArrayList<Boolean>();
		for (int i = 0; i < numberOfTestCases; i++) {
			tmp = lines.get(6 + i);
			if (tmp.substring(13, 14).equals("0")) {
				resultVector.add(true);
				String a[] = tmp.substring(16).split(" ");
				ArrayList<Byte> listTmp = new ArrayList<Byte>();
				for (String s : a) {
					listTmp.add(new Byte(s));
				}
				coverageMatrix.add(listTmp);
				passCoverageMatrix.add(listTmp);
			} else {
				resultVector.add(false);
				String a[] = tmp.substring(16).split(" ");
				ArrayList<Byte> listTmp = new ArrayList<Byte>();
				for (String s : a) {
					listTmp.add(new Byte(s));
				}
				coverageMatrix.add(listTmp);
				failCoverageMatrix.add(listTmp);
			}
		}
	}

	/**
	 * 从源覆盖信息文件中读取覆盖信息
	 */
	public void readInfoFromFile() throws FileNotFoundException {
		File coverageFile = new File(absoluteCoverageFilePath);
		FileInputStream fileInputStream = new FileInputStream(coverageFile);
		InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
		Scanner cin = new Scanner(fileInputStream);
		List<String> lines = new ArrayList<String>();
		while (cin.hasNext()) {
			String tmp = cin.nextLine();
			lines.add(tmp);
		}
		versionName = lines.get(0).substring(7);
		numberOfTestCases = Integer.parseInt(lines.get(1).substring(7));
		String tmp = lines.get(2).substring(7);
		String tmps[] = tmp.split(" ");
		locationsOfExecutableEntities = new ArrayList<Integer>();
		for (String s : tmps) {
			locationsOfExecutableEntities.add(Integer.parseInt(s));
		}
		numberOfExecutableEntities = Integer.parseInt(lines.get(3).substring(7));
		numberOfFaults = Integer.parseInt(lines.get(4).substring(7));
		tmp = lines.get(5).substring(7);
		String tmps2[] = tmp.split(" ");
		locationsOfFaults = new ArrayList<Integer>();
		indexOfFaults = new ArrayList<Integer>();
		for (String s : tmps2) {
			int tempInt = Integer.parseInt(s);
			locationsOfFaults.add(tempInt);
			indexOfFaults.add(findFirstLessEqualThan(tempInt));
		}
		coverageMatrix = new ArrayList<List<Byte>>();
		passCoverageMatrix = new ArrayList<List<Byte>>();
		failCoverageMatrix = new ArrayList<List<Byte>>();
		resultVector = new ArrayList<Boolean>();
		for (int i = 0; i < numberOfTestCases; i++) {
			tmp = lines.get(6 + i);
			if (tmp.substring(13, 14).equals("0")) {
				resultVector.add(true);
				String a[] = tmp.substring(16).split(" ");
				ArrayList<Byte> listTmp = new ArrayList<Byte>();
				for (String s : a) {
					listTmp.add(new Byte(s));
				}
				coverageMatrix.add(listTmp);
				passCoverageMatrix.add(listTmp);
			} else {
				resultVector.add(false);
				String a[] = tmp.substring(16).split(" ");
				ArrayList<Byte> listTmp = new ArrayList<Byte>();
				for (String s : a) {
					listTmp.add(new Byte(s));
				}
				coverageMatrix.add(listTmp);
				failCoverageMatrix.add(listTmp);
			}
		}
		// System.out.println(coverageMatrix.toString());
	}
	
	/**
	 * 压缩矩阵，删除未被错误用例覆盖过的行
	 */
	public void deeplyCompressMatrix() {
		// outputMatrix(coverageMatrix);
		deeplyCompressRelation = new ArrayList<Integer>();
		int matrixLines = coverageMatrix.size();
		int matrixColumns = coverageMatrix.get(0).size();
		int coIndex = 0;
		int columnIndex = 0;
		while (coIndex < matrixColumns) {
			boolean same = true;
			for(int lineIndex = 0; lineIndex < failCoverageMatrix.size();lineIndex++){
				if(failCoverageMatrix.get(lineIndex).get(coIndex).equals(new Byte("1"))){
					same = false;
					break;
				}
			}
			if(same){
				for (int lineIndex = 0; lineIndex < matrixLines; lineIndex++) {
					coverageMatrix.get(lineIndex).remove(coIndex);
				}
				locationsOfExecutableEntities.remove(coIndex);
				matrixColumns--;
			}else{
				deeplyCompressRelation.add(columnIndex);
				coIndex++;
			}
			columnIndex++;
		}
		System.out.println("original Executable entities is : " + numberOfExecutableEntities);
		numberOfExecutableEntities = coverageMatrix.get(0).size();
		System.out.println("number of Executable entities is : " + numberOfExecutableEntities);
		// outputMatrix(coverageMatrix);
	}

	/**
	 * 压缩矩阵，合并所有覆盖信息都相同的行
	 */
	public void compressMatrix() {
		// outputMatrix(coverageMatrix);
		int matrixLines = coverageMatrix.size();
		int matrixColumns = coverageMatrix.get(0).size();
		int coIndex = 0;
		while (coIndex < matrixColumns - 1) {
			boolean same = true;
			for (int lineIndex = 0; lineIndex < matrixLines; lineIndex++) {
				if (!coverageMatrix.get(lineIndex).get(coIndex)
						.equals(coverageMatrix.get(lineIndex).get(coIndex + 1))) {
					same = false;
					break;
				}
			}
			if (same) {
				for (int lineIndex = 0; lineIndex < matrixLines; lineIndex++) {
					coverageMatrix.get(lineIndex).remove(coIndex + 1);
				}
				locationsOfExecutableEntities.remove(coIndex + 1);
				matrixColumns--;
			} else {
				coIndex++;
			}
		}
		numberOfExecutableEntities = coverageMatrix.get(0).size();
		indexOfFaults = new ArrayList<>();
		for (int i = 0; i < locationsOfFaults.size(); i++) {
			indexOfFaults.add(findFirstLessEqualThan(locationsOfFaults.get(i)));
		}
		// outputMatrix(coverageMatrix);
	}

	/**
	 * find the first lcation in locationsOfExecutableEntities which is less or
	 * equal than x
	 */
	private Integer findFirstLessEqualThan(Integer x) {
		for (Integer i = 0; i < locationsOfExecutableEntities.size(); i++) {
			if (locationsOfExecutableEntities.get(i) > x) {
				return i - 1;
			}
		}
		return locationsOfExecutableEntities.size() - 1;
	}

	private void outputMatrix(List<List<Byte>> m) {
		for (int i = 0; i < m.size(); i++) {
			for (int j = 0; j < m.get(0).size(); j++) {
				System.out.print(m.get(i).get(j));
			}
			System.out.println();
		}
		System.out.println();
	}

	public void outputFaultsCoverage(String fileName) throws FileNotFoundException, IOException {
		File file = new File(fileName);
		
		String str = "passed:\n";
		for(int i = 0;i < passCoverageMatrix.size();i++){
			for(int j = 0; j < passCoverageMatrix.get(i).size();j++){
				if(isFaultLine(j)){
					str += passCoverageMatrix.get(i).get(j) + " ";
				}
			}
			str += "\n";
		}
		str += "failed:\n";
		for(int i = 0;i < failCoverageMatrix.size();i++){
			for(int j = 0; j < failCoverageMatrix.get(i).size();j++){
				if(isFaultLine(j)){
					str += failCoverageMatrix.get(i).get(j) + " ";
				}
			}
			str += "\n";
		}
		
		
		try (FileOutputStream output = new FileOutputStream(file);) {
			output.write(str.getBytes());
		}
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("#Ver_# " + programName + "_" + version + "\n");
		buffer.append("#NOTS# " + numberOfTestCases + "\n");
		buffer.append("#NOES# " + numberOfExecutableEntities + "\n");
		buffer.append("#NOF_# " + numberOfFaults + "\n");
		buffer.append("#LOFS#");
		for (Integer i = 0; i < locationsOfFaults.size(); i++) {
			buffer.append(" " + findFirstLessEqualThan(locationsOfFaults.get(i)));

		}
		buffer.append("\n");
		buffer.append("#LOES#");
		for (Integer i = 0; i < locationsOfExecutableEntities.size(); i++) {
			buffer.append(" " + toFiveLengthString(locationsOfExecutableEntities.get(i)));
		}
		buffer.append("\n");

		for (Integer i = 1; i <= coverageMatrix.size(); i++) {
			buffer.append("#CASE#" + toFiveLengthString(i) + "#R" + (resultVector.get(i - 1) == true ? 0 : 1) + "#");
			for (int j = 0; j < coverageMatrix.get(i - 1).size(); j++) {
				buffer.append(" " + coverageMatrix.get(i - 1).get(j));
			}
			buffer.append("\n");
		}
		// System.out.println(buffer.toString());

		return buffer.toString();
	}

	private String toFiveLengthString(Integer a) {
		String str = a.toString();
		while (str.length() < 5) {
			str = "0" + str;
		}
		return str;
	}

	public String getVersion() {
		return version;
	}

	public String getVersionName() {
		return versionName;
	}

	public Integer getNumberOfTestCases() {
		return numberOfTestCases;
	}

	public List<Integer> getLocationsOfExecutableEntities() {
		return locationsOfExecutableEntities;
	}

	public Integer getNumberOfExecutableEntities() {
		return numberOfExecutableEntities;
	}

	public Integer getNumberOfFaults() {
		return numberOfFaults;
	}

	public List<Integer> getLocationsOfFaults() {
		return locationsOfFaults;
	}

	public List<List<Byte>> getCoverageMatrix() {
		return coverageMatrix;
	}

	public List<List<Byte>> getPassCoverageMatrix() {
		return passCoverageMatrix;
	}

	public List<List<Byte>> getFailCoverageMatrix() {
		return failCoverageMatrix;
	}

	public List<Boolean> getResultVector() {
		return resultVector;
	}

	public List<Integer> getDeeplyCompressRelation() {
		return deeplyCompressRelation;
	}

	public List<Integer> getIndexOfFaults() {
		return indexOfFaults;
	}
	
	

}
