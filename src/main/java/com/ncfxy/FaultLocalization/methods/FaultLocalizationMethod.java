package com.ncfxy.FaultLocalization.methods;

import java.util.List;

import com.ncfxy.FaultLocalization.Experiment;

public interface FaultLocalizationMethod {
	
	/**
	 * 执行缺陷定位方法获取程序实体的可疑度值排名
	 * @param experiment
	 * @return
	 */
	public List<Double> getSuspicious(Experiment experiment);
	
	public List<Integer> getResult(Experiment experiment);

}
