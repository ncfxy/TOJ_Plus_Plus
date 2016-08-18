package cn.tjuscs.oj.yh;

import java.io.*;

public class ChangeFile{
	
	static String[] headfile = {
			"<iostream.h>",
			"<assert.h>",
			"<ctype.h>",
			"<errno.h>",
			"<float.h>",
			"<fstream.h>",
			"<iomanip.h>",
			"<limits.h>",
			"<locale.h>",
			"<math.h>",
			"<stdio.h>",
			"<stdlib.h>",
			"<string.h>",
			"<time.h>",
			"<wchar.h>",
			"<wctype.h>"
	};
	static String[] newheadfile = {
			"#include <iostream>",
			"#include <cassert>",
			"#include <cctype>",
			"#include <cerrno>",
			"#include <cfloat>",
			"#include <fstream>",
			"#include <iomanip>",
			"#include <limits>",
			"#include <clocale>",
			"#include <cmath>",
			"#include <cstdio>",
			"#include <cstdlib>",
			"#include <cstring>",
			"#include <ctime>",
			"#include <cwchar>",
			"#include <cwctype>"
	};
	
	public static void changefile(String file_path, String file_tmp){
		try{
			BufferedReader in = new BufferedReader(new FileReader(file_path));
			BufferedWriter out = new BufferedWriter(new FileWriter(file_tmp + "_src.cpp"));
			String s = "using namespace std;";
			out.write(s);
			out.newLine();
			while((s = in.readLine()) != null){
				if(s.startsWith("#include")){
					System.out.println("change " + s);
					checkheadfile:
					for (int i = 0; i < 16; i++){
						if(s.contains(headfile[i])){
							s = newheadfile[i];
							System.out.println(s);
							break checkheadfile;
						}
					}
				}
				out.write(s);
				out.newLine();
			}
			out.flush();
			in.close();
			out.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}