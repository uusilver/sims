package com.xwtech.framework.pub.utils;

import java.io.*;

import java.util.zip.*;

public class UnZipUtils {
	static final int BUFFER = 2048;

	public static void main(String argv[]) {
		try {
			// String fileName = "e:\\aa.zip"; //待解压缩的文件名

			String fileName = "E:\\Tomcat 5.0\\webapps\\mpms\\mpms\\zip\\readme.zip";
			/*
			 * 创建一个File实例file
			 * 
			 * public class File 为文件和目录路径名的抽象表示形式。 构造方法File(String pathname)
			 * 通过将给定路径名字符串转换成抽象路径名来创建一个新 File 实例。
			 */
			File file = new File(fileName);

			// 从文件名中提取出子目录(路径)名
			// lastIndexOf(String str)返回在此字符串中最右边出现的指定子字符串的索引
			int n = fileName.lastIndexOf(".");
			// String folderName = fileName.substring(0,n);
			String path = "c:\\02\\source\\";
			String folderName = path + fileName.substring(3, n);
			System.out.println(n);
			File folder = new File(folderName);

			// folder.mkdir(); //创建子目录(路径)

			System.out.println(folderName);
			BufferedOutputStream dest = null;
			FileInputStream fis = new FileInputStream(file);
			ZipInputStream zis = new ZipInputStream(
					new BufferedInputStream(fis));
			// ZipEntry类用于表示 ZIP 文件条目
			ZipEntry entry;

			// getNextEntry() 读取下一个 ZIP 文件条目并将流定位到该条目数据的开始处
			while ((entry = zis.getNextEntry()) != null) {
				if (entry.isDirectory())
					continue;
				System.out.println("Extracting:   " + entry);
				int count;
				byte data[] = new byte[BUFFER];
				/*
				 * write the files to the disk FileOutputStream(String name)
				 * 创建一个向具有指定名称的文件中写入数据的输出文件流。
				 */

				File f1 = new File(entry.getName());
				if (f1.getParent() != null) {
					String subDirName = folderName + "\\" + f1.getParent();
					File subDir = new File(subDirName);
					System.out.println(subDir.getAbsolutePath());
					subDir.mkdirs();
				}

				FileOutputStream fos = new FileOutputStream(folderName + "\\"
						+ entry.getName());
				/*
				 * BufferedOutputStream(OutputStream out, int size)
				 * 创建一个新的缓冲输出流，以将具有指定缓冲区大小的数据写入指定的基础输出流。
				 */
				dest = new BufferedOutputStream(fos, BUFFER);
				while ((count = zis.read(data, 0, BUFFER)) != -1) {
					dest.write(data, 0, count);
				}
				// 刷新此缓冲的输出流。
				dest.flush();
				dest.close();
			}
			zis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}