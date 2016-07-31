package com.ncfxy.FaultLocalization.methods.statistics;

import java.util.ArrayList;
import java.util.List;

import com.ncfxy.FaultLocalization.Experiment;

public class AdvancedTarantula extends StatisticsBase {
	
	public List<Double> getSuspicious(Experiment experiment) {
		init(experiment);
		for (int i = 0; i < failed.size(); i++) {
			Double haha = (failed.get(i) * 1.0 / totalFailed)
					/ ((passed.get(i) * 1.0 / totalPassed) + (failed.get(i) / totalFailed))
					* Math.max((passed.get(i) * 1.0 / totalPassed), (failed.get(i) / totalFailed));
			if(haha.isNaN() || haha.isInfinite()){
				haha = 0.0;
			}
			result.add(haha);
		}

		return result;
	}

}
