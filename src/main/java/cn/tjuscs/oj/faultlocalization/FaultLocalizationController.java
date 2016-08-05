package cn.tjuscs.oj.faultlocalization;

import java.io.FileNotFoundException;
import java.util.List;

import com.ncfxy.FaultLocalization.FaultLocalization;

public class FaultLocalizationController {

	private String pid;
	private String commitId;

	public FaultLocalizationController(String pid, String commitId) {
		super();
		this.pid = pid;
		this.commitId = commitId;
	}

	public List<Integer> getCodeSuspiciousList() {
		String coverageFilePath = System.getProperty("user.dir") + "/data/toj_problem_" + pid + "/programs/commit_id_"
				+ commitId + "/coverage_matrix.txt";
		FaultLocalization faultLocalization = new FaultLocalization(coverageFilePath);
		List<Integer> result = null;
		try {
			result = faultLocalization.getSuspiciousList();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		FaultLocalizationController haha = new FaultLocalizationController("1007", "1131123");
		List<Integer> result = haha.getCodeSuspiciousList();
		for(Integer x:result){
			System.out.println("line: " + x);
		}
	}

}
