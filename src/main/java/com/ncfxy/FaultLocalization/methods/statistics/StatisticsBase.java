package com.ncfxy.FaultLocalization.methods.statistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.ncfxy.FaultLocalization.Experiment;
import com.ncfxy.FaultLocalization.methods.FaultLocalizationMethod;

public abstract class StatisticsBase implements FaultLocalizationMethod {
	protected int totalFailed;
	protected int totalPassed;
	protected List<Integer> failed = new ArrayList<Integer>();
	protected List<Integer> passed = new ArrayList<Integer>();
	protected List<Double> result = new ArrayList<Double>();
	
	protected void init(Experiment experiment) {
		totalFailed = experiment.getFailCoverageMatrix().size();
		totalPassed = experiment.getPassCoverageMatrix().size();
		for (int i = 0; i < experiment.getNumberOfExecutableEntities(); i++) {
			failed.add(new Integer(0));
			passed.add(new Integer(0));
		}
		for (int i = 0; i < experiment.getFailCoverageMatrix().size(); i++) {
			List<Byte> list = experiment.getFailCoverageMatrix().get(i);
			for (int j = 0; j < list.size(); j++) {
				failed.set(j, failed.get(j) + list.get(j));
			}
		}
		for (int i = 0; i < experiment.getPassCoverageMatrix().size(); i++) {
			List<Byte> list = experiment.getPassCoverageMatrix().get(i);
			for (int j = 0; j < list.size(); j++) {
				passed.set(j, passed.get(j) + list.get(j));
			}
		}
	}

	public abstract List<Double> getSuspicious(Experiment experiment);
	
	public List<Integer> getResult(Experiment experiment){
		List<Double> suspiciousList = getSuspicious(experiment);
		List<Pair> reSortList = new ArrayList<>();
		List<Integer> resultList = new ArrayList<>();
		int first = -1, last = -1;
		for (int i = 0; i < suspiciousList.size(); i++) {
			reSortList.add(new Pair(suspiciousList.get(i), i));
		}
		Collections.sort(reSortList);
		for (int i = 0; i < reSortList.size(); i++) {
			resultList.add(reSortList.get(i).getValue());
		}
		return resultList;
	}
	
	
	private class Pair implements Map.Entry<Double, Integer>, Comparable<Pair> {
		Double key;
		Integer value;

		public Pair(Double key, Integer value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public Double getKey() {
			return key;
		}

		@Override
		public Integer getValue() {
			return value;
		}

		@Override
		public Integer setValue(Integer value) {
			return null;
		}

		@Override
		public int compareTo(Pair o) {
			if (this.key < o.getKey()) {
				return 1;
			} else if (this.key > o.getKey()) {
				return -1;
			}
			return 0;
		}

	}

}
