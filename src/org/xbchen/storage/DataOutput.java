package org.xbchen.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Set;

public class DataOutput {
	private String outDirectory;
	public DataOutput(String outDirectory) {
		this.outDirectory = outDirectory;
	}
	public void outputByGroups(int groupNum, Set<String> logSet, String event) {
		File groupDirectory = new File(outDirectory + "\\group");
		if (!groupDirectory.exists() || groupDirectory.isFile())
			groupDirectory.mkdirs();
		RandomAccessFile raf;
		try {
			raf = new RandomAccessFile(outDirectory + "\\group\\" + String.valueOf(groupNum) + ".txt", "rw");
			raf.seek(raf.length());
			StringBuilder str = new StringBuilder(event);
			str.append("\r\n");
			raf.write(str.toString().getBytes());
			for (String log : logSet) {
				str = new StringBuilder(log);
				str.append("\r\n");
				raf.write(str.toString().getBytes());
			}
			raf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeStrToFile(String fileName, String content) {
		RandomAccessFile raf;
		try {
			raf = new RandomAccessFile(outDirectory + "\\" + fileName, "rw");
			raf.seek(raf.length());
			StringBuilder str = new StringBuilder(content);
			str.append("\r\n");
			raf.write(str.toString().getBytes());
			raf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
