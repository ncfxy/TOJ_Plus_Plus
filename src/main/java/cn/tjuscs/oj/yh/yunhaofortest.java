package cn.tjuscs.oj.yh;
import cn.tjuscs.oj.yh.*;
public class yunhaofortest{
	public static void main(String[] args) {
		String file_path = "/home/yunhao/coding/JavaProject/TOJ_Plus_Plus/data/toj_problem_2800/programs/commit_id_testid/testid.cpp";
		String dis_path = "/home/yunhao/test";
		String ret = compile.compile(file_path, dis_path);
		System.out.print(ret);
	}
}