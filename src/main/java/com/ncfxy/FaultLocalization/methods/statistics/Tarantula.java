package com.ncfxy.FaultLocalization.methods.statistics;

import java.util.ArrayList;
import java.util.List;

import com.ncfxy.FaultLocalization.Experiment;

public class Tarantula extends StatisticsBase {
	
	public List<Double> getSuspicious(Experiment experiment) {
		init(experiment);
		for (int i = 0; i < failed.size(); i++) {
			//System.out.println("" + failed.get(i) + "  " + totalFailed + "  " + passed.get(i) + "  " + totalPassed);
			Double haha = (failed.get(i) * 1.0 / totalFailed * 1.0)
					/ ((passed.get(i) * 1.0 / totalPassed * 1.0) + (failed.get(i)*1.0 / totalFailed * 1.0));
			if(haha.isNaN() || haha.isInfinite()){
				haha = 0.0;
			}
			result.add(haha);
		}

		return result;
	}

}
