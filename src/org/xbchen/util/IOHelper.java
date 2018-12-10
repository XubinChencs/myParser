package org.xbchen.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
/**
 * @author WangAo IO帮助类
 */
public class IOHelper {

	// instance : 单例模式实例
	private static IOHelper instance = new IOHelper();

	private static final Charset charset = Charset.forName("UTF-8");

	/**
	 * @Title:IOHelper
	 * @Description: 单例构造函数
	 */
	private IOHelper() {
	}

	/**
	 * @return 单例实例
	 * @Title: getInstance
	 * @Description: 获取单例实例
	 * @return: IOHelper
	 */
	public static IOHelper getInstance() {
		return instance;
	}
	

	/**
	 * @param context  指定文本
	 * @param filePath 指定路径
	 * @param append   追加还是覆盖，true追加，false覆盖
	 * @Title: writeToFile
	 * @Description: 将指定文本写入指定路径，并指定是追加还是覆盖
	 * @return: void
	 */
	public void writeToFile(String context, String filePath, boolean append) {
		File file = new File(filePath);
		File folder = file.getParentFile();
		if (folder != null && !folder.exists()) {
			folder.mkdirs();
		}
		try (FileOutputStream fos = new FileOutputStream(file, append);
				OutputStreamWriter osw = new OutputStreamWriter(fos, charset);
				BufferedWriter bw = new BufferedWriter(osw);) {
			bw.write(context);
			bw.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @param context  指定文本
	 * @param path     指定目录
	 * @param fileName 指定文件
	 * @param append   追加还是覆盖，true追加，false覆盖
	 * @Title: writeToFile
	 * @Description: 将指定文本写入指定目录下的指定文件内，并指定是追加还是覆盖
	 * @return: void
	 */
	public void writeToFile(String context, String path, String fileName, boolean append) {
		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		try (FileOutputStream fos = new FileOutputStream(new File(path + File.separator + fileName), append);
				OutputStreamWriter osw = new OutputStreamWriter(fos, charset);
				BufferedWriter bw = new BufferedWriter(osw);) {
			bw.write(context);
			bw.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @param context  指定文本
	 * @param filePath 指定路径
	 * @param append   追加还是覆盖，true追加，false覆盖
	 * @Title: writeToFile
	 * @Description: 将指定文本写入指定路径，并指定是追加还是覆盖
	 * @return: void
	 */
	public void writeToFile(byte[] context, String filePath, boolean append) {
		File file = new File(filePath);
		File folder = file.getParentFile();
		if (folder != null && !folder.exists()) {
			folder.mkdirs();
		}
		try (FileOutputStream fos = new FileOutputStream(file, append);) {
			fos.write(context);
			fos.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param context  指定文本
	 * @param path     指定目录
	 * @param fileName 指定文件
	 * @param append   追加还是覆盖，true追加，false覆盖
	 * @Title: writeToFile
	 * @Description: 将指定文本写入指定目录下的指定文件内，并指定是追加还是覆盖
	 * @return: void
	 */
	public void writeToFile(byte[] context, String path, String fileName, boolean append) {
		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		try (FileOutputStream fos = new FileOutputStream(new File(path + File.separator + fileName), append);) {
			fos.write(context);
			fos.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * @param filePath 指定路径
	 * @return 字节数组
	 * @Title: readFromFileToBytes
	 * @Description: 从指定路径读取字节数组
	 * @return: byte[]
	 */
	public byte[] readFromFileToBytes(String filePath) {
		File file = new File(filePath);
		byte[] result = new byte[(int) file.length()];
		try (FileInputStream fis = new FileInputStream(file);) {
			fis.read(result);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * @param filePath 指定路径
	 * @return 字符串
	 * @Title: readFromFileToBytes
	 * @Description: 从指定路径读取字符串
	 * @return: List<String>
	 */
	public List<String> readFromFile(String filePath) {             
		List<String> result = new ArrayList<String>();
		// System.out.println(filePath);
		try (FileInputStream fis = new FileInputStream(new File(filePath));
				InputStreamReader isr = new InputStreamReader(fis, charset);
				BufferedReader br = new BufferedReader(isr);) {
			String curLine = null;
			while ((curLine = br.readLine()) != null) {
				// System.out.println(curLine);
				result.add(curLine);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public String readFromFileByLine(String filePath) {             
		String result = null;
		// System.out.println(filePath);
		try (FileInputStream fis = new FileInputStream(new File(filePath));
				InputStreamReader isr = new InputStreamReader(fis, charset);
				BufferedReader br = new BufferedReader(isr);) {
			String curLine = null;
			if ((curLine = br.readLine()) != null) {
				// System.out.println(curLine);
				result = new String (curLine);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	
	public int getLineNum(String filePath) {
		int result = 0;

		try (FileInputStream fis = new FileInputStream(new File(filePath));
				InputStreamReader isr = new InputStreamReader(fis);
				BufferedReader br = new BufferedReader(isr);) {
			while ((br.readLine()) != null) {
				result++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public void copyFile(String oldPath, String newPath) {
		try {
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldPath);
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();
		}
	}
	
	

}
