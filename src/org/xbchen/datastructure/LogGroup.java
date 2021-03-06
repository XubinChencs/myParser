    package org.xbchen.datastructure;

import java.util.HashSet;
import java.util.Set;

import org.xbchen.constant.ParseConstant;

public class LogGroup {
	private String[] eventSeq;
	private Set<String> logIDs = new HashSet<String>();
	
	public LogGroup() {
		
	}
	
	public LogGroup (String log, double ID) {
		eventSeq = log.split(ParseConstant.PRIMARY_SEPARATOR);
		logIDs.add(log);
	}
	
	private int min(int a, int b) {
		if (a >= b) return b;
		return a;
	}
	
	public double calSimilarity(String logData) {
		String[] seq1 = logData.split(ParseConstant.PRIMARY_SEPARATOR);
		double cnt = 0;
		for (int i = 0; i < min(seq1.length, eventSeq.length); i++) {
			if (seq1[i].equals(eventSeq[i]) || eventSeq[i].equals("*")) {
				cnt++;
			}
		}
		double simSeq = cnt / seq1.length;
		return simSeq;
	}
	
	public void update(String logData) {
		logIDs.add(logData);
		String[] seq1 = logData.split(ParseConstant.PRIMARY_SEPARATOR);
		for (int i = 0; i < min(seq1.length, eventSeq.length); i++) {
			if (!seq1[i].equals(eventSeq[i])) {
				eventSeq[i] = new String("*");
			}
		}
	}
	
	public Set<String> getLogSet() {
		return logIDs;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < eventSeq.length; i++) {
			sb.append(eventSeq[i]+ " ");
		}
		return sb.toString();
	}
}
