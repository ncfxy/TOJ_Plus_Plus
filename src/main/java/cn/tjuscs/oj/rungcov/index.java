package cn.tjuscs.oj.rungcov;

import java.io.IOException;

public class index {
	public static void main(String [] args){
		try {
			System.out.println("aaaaaaaa");
			new GetMatrixFromGcov(new String[] {"992065", "3", "3"});
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
