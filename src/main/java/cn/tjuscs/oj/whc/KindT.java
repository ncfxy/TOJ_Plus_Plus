package cn.tjuscs.oj.whc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class KindT extends FileKind {

	
	public KindT(String sourcePath, String targetPath, String rightProPath) {
		// TODO Auto-generated constructor stub
		super(sourcePath, targetPath, rightProPath);
	}
	@Override
	boolean splitFile() {
		// TODO Auto-generated method stub
		File file = new File(this.sourceFIlePath);
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			Scanner scanner = new Scanner(fileInputStream);
			int T = scanner.nextInt();
			String command = "";
			while(scanner.hasNext()){
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
}
