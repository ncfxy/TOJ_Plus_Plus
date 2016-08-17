/**
 * File Name: JavaOperateFiles.java
 * Package Name: cn.tjuscs.oj.cmdHelper
 * Description： TODO 
 *
 *   ver					date			author
 * ──────────────────────────────────
 *   1.0-SNAPSHOT			2016年8月16日			ncfxy
 *
 * Copyright (c) 2016, ncfxy All Rights Reserved.
 */

package cn.tjuscs.oj.cmdHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * 该类是一个工具类，主要实现了Java对文件的操作
 * 包括：对比文件，删除文件，重命名等操作
 *
 * @author ncfxy
 * @version 1.0-SNAPSHOT
 * @Date 2016 2016年8月16日 下午5:18:29
 */
public class JavaOperateFiles {
	/**
	 * compareTwoFile: 判断两个文件是否相同
	 *
	 * @param file1Path
	 * @param file2Path
	 * @return
	 * @since TOJ_Plus_Plus　Ver 1.0-SNAPSHOT
	 */
	public static boolean compareTwoFile(String file1Path, String file2Path)
			throws FileNotFoundException, IOException {
		try (FileInputStream file1 = new FileInputStream(new File(file1Path));
				FileInputStream file2 = new FileInputStream(new File(file2Path));) {
			Scanner cin1 = new Scanner(file1);
			Scanner cin2 = new Scanner(file2);
			while (cin1.hasNext()) {
				if (!cin2.hasNext())
					return false;
				String s1 = cin1.nextLine();
				String s2 = cin2.nextLine();
				if (!s1.equals(s2)) {
					return false;
				}
			}
			if (cin2.hasNext())
				return false;
		}
		return true;
	}

	/**
	 * Java重命名文件
	 *
	 * @param path
	 * @param oldname
	 * @param newname
	 * @since TOJ_Plus_Plus　Ver 1.0-SNAPSHOT
	 */
	public static void renameFile(String path, String oldname, String newname) {
		if (!oldname.equals(newname)) {// 新的文件名和以前文件名不同时,才有必要进行重命名
			File oldfile = new File(path + "/" + oldname);
			File newfile = new File(path + "/" + newname);
			if (!oldfile.exists()) {
				return;// 重命名文件不存在
			}
			if (newfile.exists())// 若在该目录下已经有一个文件和新文件名相同，则不允许重命名
				System.out.println(newname + "已经存在！");
			else {
				oldfile.renameTo(newfile);
			}
		} else {
			System.out.println("新文件名和旧文件名相同...");
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 根据路径删除指定的目录或文件，无论存在与否
	 *
	 * @param sPath
	 *            要删除的目录或文件
	 * @return 删除成功返回 true，否则返回 false。
	 */
	public static boolean DeleteFolder(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 判断目录或文件是否存在
		if (!file.exists()) { // 不存在返回 false
			return flag;
		} else {
			// 判断是否为文件
			if (file.isFile()) { // 为文件时调用删除文件方法
				return deleteFile(sPath);
			} else { // 为目录时调用删除目录方法
				return deleteDirectory(sPath);
			}
		}
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param sPath
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String sPath) {
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} // 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

}
