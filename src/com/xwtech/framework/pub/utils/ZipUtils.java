package com.xwtech.framework.pub.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;


public class ZipUtils {
	/**
	 * 压缩一个文件
	 * @param fileToZip 要压缩的目标文件
	 * @param zos zip输出流
	 * @param zipFolderName 要压缩的起始目录
	 */
	public static void ZipFile(File fileToZip, ZipOutputStream zos,String zipFolderName,String charsetName) {
		try {
			// String getPath() 将此抽象路径名转换为一个路径名字符串。
			String path = fileToZip.getPath();
			
//			System.out.println("zos encoding :"+zos.getEncoding());
			
			// char charAt(int index) 返回指定索引处的 char 值
			if (path.charAt(1) == ':')
				path = path.substring(path.indexOf(zipFolderName+"\\")+zipFolderName.length()+1);

			/*
			 * public ZipEntry(String name)使用指定名称创建新的 ZIP 条目 public void
			 * putNextEntry(ZipEntry e) e - 要写入的 ZIP 条目 开始写入新的 ZIP
			 * 文件条目并将流定位到条目数据的开始处 注意getNextEntry和pubNextEntry的区别
			 * zos.putNextEntry();
			 */

			zos.putNextEntry(new ZipEntry(path));

			/*
			* FileInputStream(File file) 通过打开一个到实际文件的连接来创建一个 FileInputStream，
			*/
			FileInputStream fis = new FileInputStream(fileToZip);
				
			InputStreamReader inputStreamReader = new InputStreamReader(fis,charsetName);
				
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				
			String c;
			/*
			 * public int read():从此输入流中读取一个数据字节 返回：下一个数据字节；如果已到达文件末尾，则返回 -1。
			 */
			while ((c = bufferedReader.readLine()) != null){
				zos.write(c.getBytes(charsetName));	
			}
			fis.close();
			zos.closeEntry();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * 查找目录下的文件
	 * @param start
	 * @param zos
	 * @throws IOException 
	 */
	public static void findSubFiles(String start, ZipOutputStream zos,String zipfolderName,String charsetName) throws IOException {
		System.out.println(start);
		// File(String pathname) 通过将给定路径名字符串转换成抽象路径名来创建一个新 File 实例。
		File fileStart = new File(start);
		// 要压缩的(start)，如果是一个文件，调用ZipFile压缩之，然后返回
		if (fileStart.isFile()) {
			ZipFile(fileStart, zos,zipfolderName,charsetName);
			return;
		}

		// 如果不是文件，就是子目录，执行下面的操作
		// list() 返回由此抽象路径名所表示的目录中的文件和目录的名称所组成字符串数组。
		//取出该子目录下的文件名和子目录名
		String[] subFn = fileStart.list();
		
		// 下面的 for 处理这些文件或子目录
		for (int i = 0; i < subFn.length; i++) {
			File subFile = new File(start + "\\" + subFn[i]);

			if (subFile.isFile()) {// 如果是一个文件，调用ZipFile压缩之
				ZipFile(subFile, zos,zipfolderName,charsetName);
				System.out.println(subFile.getName());
			} else
				// 不是文件，就是子目录，递归之，再找。
				findSubFiles(start + "\\" + subFile.getName(), zos,zipfolderName,charsetName);
		}
	}

	public static void main(String[] args) {
		try {

			String[] folders = { "E:/Tomcat 5.0/webapps/mpms/mpms/temp/" };
			FileOutputStream fos = new FileOutputStream("E:/Tomcat 5.0/webapps/mpms/mpms/zip/file.zip");
			// class ZipOutputStream此类为以 ZIP 文件格式写入文件实现输出流过滤器。包括对已压缩和未压缩条目的支持。

			// 创建新的 ZIP 输出流。
			ZipOutputStream zos = new ZipOutputStream(fos);
			String zipfolderName = "temp";
			
			String charsetName = "UTF-8";

			zos.setEncoding(charsetName);
			// folders.length的值是1
			for (int i = 0; i < folders.length; i++) {

				System.out.println("Writing   file   " + folders[i]);
				findSubFiles(folders[i], zos,zipfolderName,charsetName);
			}
			zos.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}