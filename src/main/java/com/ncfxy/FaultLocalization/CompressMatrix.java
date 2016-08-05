package com.ncfxy.FaultLocalization;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.runner.Version;

public class CompressMatrix {

	public static void main(String[] args) throws IOException {
//		DealWithOneExperiment deal = new DealWithOneExperiment();
		String basePath = "C:/Users/Administrator/Desktop/邹雨果科研资料/matlab代码/MATLAB/Fault-Localization/data/";
		String programName = "tcas";
		List<Integer> versions = findProgramVersions(basePath, programName);
		for(int i = 0;i < versions.size();i++){
			String version = "v" + versions.get(i);
			Experiment experiment = new Experiment(basePath, programName, version, "output" + versions.get(i) + ".txt",basePath + "/" + programName + "/" + version + "/" + "output" + versions.get(i) + ".txt");//"coverage_matrix.txt");//
			experiment.readInfoFromFile();
			experiment.compressMatrix();
			experiment.deeplyCompressMatrix();
			outputFile(basePath, programName, version, experiment.toString());
			System.out.println(programName + version + ": finished");
		}
	}
	
	public static List<Integer> findProgramVersions(String basePath,String programName) {
		File file = new File(basePath + programName);
		List versions = new ArrayList<Integer>();
		if (file.exists() && file.isDirectory()) {
			File[] versionFiles = file.listFiles();
			for (File versionFile : versionFiles) {
				if (versionFile.getName().matches("v[0-9]+") && versionFile.isDirectory()) {
					String versionNumber = versionFile.getName().substring(1);
					try {
						Integer versionInteger = Integer.parseInt(versionNumber);
						versions.add(versionInteger);
					} catch (NumberFormatException e) {

					}
				}
			}
		}
		Collections.sort(versions);
		// System.out.println(versions);
		return versions;
	}

	public static void outputFile(String basePath, String programName, String version, String outputString)
			throws IOException {
		File outputFile = new File(
				basePath + "/" + programName + "/" + version + "/" + "deeply_compressed_coverage_matrix.txt");
		try (OutputStream output = new FileOutputStream(outputFile);) {
			output.write(outputString.getBytes());
		}
	}

}
