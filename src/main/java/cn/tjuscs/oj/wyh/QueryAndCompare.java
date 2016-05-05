package cn.tjuscs.oj.wyh;

import java.util.Iterator;
import java.util.List;

public class QueryAndCompare {
	public static void main(String [] args){
		DB db = new DB("root", "root", "toj");
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
		
		String url = "F:\\Material\\2016dachaung\\acmdata\\src\\";
		String str = CompareFile.compare(url + file1 ,  url + file2);
		System.out.println(str);
	}
}
 