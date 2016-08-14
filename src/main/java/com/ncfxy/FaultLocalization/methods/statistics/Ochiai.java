package com.ncfxy.FaultLocalization.methods.statistics;

import java.util.ArrayList;
import java.util.List;

import com.ncfxy.FaultLocalization.Experiment;

public class Ochiai extends StatisticsBase {

	public List<Double> getSuspicious(Experiment experiment) {
		init(experiment);

		for (int i = 0; i < failed.size(); i++) {
			Double haha = (failed.get(i) * 1.0)
					/ Math.sqrt(((passed.get(i) * 1.0 + failed.get(i) * 1.0) * totalFailed));
			if (haha.isNaN() || haha.isInfinite()) {
				haha = 0.0;
			}
			result.add(haha);
		}

		return result;
	}

}
