package com.ncfxy.FaultLocalization;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.ncfxy.FaultLocalization.methods.FaultLocalizationMethod;
import com.ncfxy.FaultLocalization.methods.statistics.Tarantula;

public class FaultLocalization {

	private String absoluteCoverageFilePath;

	public FaultLocalization(String absoluteCoverageFilePath) {
		super();
		this.absoluteCoverageFilePath = absoluteCoverageFilePath;
	}

	public List<Integer> getSuspiciousList() throws FileNotFoundException {
		Experiment experiment = new Experiment("", "", "", "", absoluteCoverageFilePath);
		experiment.readInfoFromFile();
		FaultLocalizationMethod method = new Tarantula();
		List<Integer> list = method.getResult(experiment);
		for(int i = 0;i < list.size();i++){
			list.set(i, experiment.getLocationsOfExecutableEntities().get(list.get(i)));
		}
		return list;
	}

}
