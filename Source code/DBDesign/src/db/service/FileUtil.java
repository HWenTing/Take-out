package db.service;

import java.io 

.File;
import java.io 

.FileInputStream;
import java.io 

.FileOutputStream;

/**
 * 进行文件读写的类
 * 
 * @author 87663
 *
 */
public class FileUtil {

	/**
	 * 读文件
	 * @param file 文件类
	 * @return 文件内容
	 * @throws Exception IO异常
	 */
	public static String getDataFromFile(File file) throws Exception {
		byte[] tmp = null;
		try (FileInputStream fips = new FileInputStream(file)) {
			tmp = new byte[fips.available()];
			fips.read(tmp);
			fips.close();
		}
		return new String(tmp, "utf-8");
	}

	/**
	 * 写文件
	 * @param file 文件类
	 * @param data 写入的数据（覆盖）
	 * @throws Exception IO异常
	 */
	public static void putDataToFile(File file, String data) throws Exception {
		try (FileOutputStream fops = new FileOutputStream(file)) {
			fops.write(data.getBytes("utf-8"));
		}
	}
}