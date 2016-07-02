package cn.tjuscs.oj.wyh;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public class QueryAndCompare {
	public static void main(String [] args){
		DB db = new DB("sonar", "sonar", "acmdata");
		db.setTable("judge");
		db.getByUid("temp0");
		//db.setLimit(2);
		db.setOrder("submittime", "desc");
		db.query();
		List<String> list1 = db.getList1();
		List<String> list2 = db.getList2();

//		System.out.println(list1.get(0));
//		System.out.println(list2.get(0));
		
		String file1 = list1.get(0);
		String file2 = list2.get(0);
		
		CompareFile compareFile = new CompareFile(file1, file2);
		compareFile.judge();
	}
}
 