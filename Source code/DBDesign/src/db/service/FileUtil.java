package db.service;

import java.io 

.File;
import java.io 

.FileInputStream;
import java.io 

.FileOutputStream;

/**
 * �����ļ���д����
 * 
 * @author 87663
 *
 */
public class FileUtil {

	/**
	 * ���ļ�
	 * @param file �ļ���
	 * @return �ļ�����
	 * @throws Exception IO�쳣
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
	 * д�ļ�
	 * @param file �ļ���
	 * @param data д������ݣ����ǣ�
	 * @throws Exception IO�쳣
	 */
	public static void putDataToFile(File file, String data) throws Exception {
		try (FileOutputStream fops = new FileOutputStream(file)) {
			fops.write(data.getBytes("utf-8"));
		}
	}
}