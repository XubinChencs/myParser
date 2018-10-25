package org.xbchen.parse;

import org.json.JSONObject;


public class Preprocessor {
	private static Preprocessor instance = new Preprocessor();
	
	private Preprocessor() {
	}

	public static Preprocessor getInstance() {
		return instance;
	}
	
	public String jsonLinuxOperationProcessor(String str) {
		JSONObject jo = new JSONObject(str);
        String line = jo.getJSONObject("_source").getString("@message");
        line = line.substring(20, line.length());
        line = line.substring(0, line.indexOf("2018")).replace(":", " ");
        return line;
	}
	
	public String jsonProcessor(String str) {
		JSONObject jo = new JSONObject(str);
        String line = jo.getString("Message");
        return line;
	}
	
	public String syslogProcessor(String str) {
		return str;
		
	}
	
	public String webVpnProcessor(String str) {
		String[] split = str.split(" ");
		StringBuilder sb = new StringBuilder();
		sb.append(split[3]);
		for (int i = 4; i < split.length; i++) {
			sb.append(" ");
			sb.append(split[i]);
		}
		return sb.toString();
	}
}
