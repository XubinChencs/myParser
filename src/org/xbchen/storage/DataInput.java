package org.xbchen.storage;
import org.json.JSONObject;
import org.xbchen.parse.Preprocessor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataInput {
	public List<String> readByLine(String filePath) {
		List<String> result = new ArrayList<String>();
		// System.out.println(filePath);
		try {   
	        FileReader reader = new FileReader(filePath);
	        BufferedReader br = new BufferedReader(reader);
	        String str = null;  
	        int cnt = 0;
	        while ((str = br.readLine()) != null) {  
	        	String line = Preprocessor.getInstance().jsonProcessor(str);
                System.out.println(line);
	            //System.out.println(jo.getString("@message"));
                cnt++;
	            if (cnt > 6000) break;
	        }           
	        br.close();
	        reader.close();
	      }
	      catch(FileNotFoundException e) {
	            e.printStackTrace();
	      }
	      catch(IOException e) {
	            e.printStackTrace();
	      }

		return result;
      }
	
	public static void main( String[] args ) {
		DataInput in = new DataInput();
		System.out.println(in.readByLine("windows-multi-2018.10.16.txt").isEmpty());
	}

}