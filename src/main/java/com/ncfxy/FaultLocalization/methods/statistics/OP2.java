package com.ncfxy.FaultLocalization.methods.statistics;

import java.util.ArrayList;
import java.util.List;

import com.ncfxy.FaultLocalization.Experiment;

public class OP2 extends StatisticsBase {
	
	public List<Double> getSuspicious(Experiment experiment) {
		init(experiment);
		for (int i = 0; i < failed.size(); i++) {
			//System.out.println("" + failed.get(i) + "  " + totalFailed + "  " + passed.get(i) + "  " + totalPassed);
			Double haha = failed.get(i) * 1.0 - 1.0 * passed.get(i) / (totalPassed + 1);
			if(haha.isNaN() || haha.isInfinite()){
				haha = 0.0;
			}
			result.add(haha);
		}

		return result;
	}

}
