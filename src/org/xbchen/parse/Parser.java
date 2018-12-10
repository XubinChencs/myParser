package org.xbchen.parse;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.xbchen.DAO.DataOutput;
import org.xbchen.constant.ParseConstant;
import org.xbchen.datastructure.LogGroup;
import org.xbchen.datastructure.Tree;
import org.xbchen.datastructure.Tree.Node;
import org.xbchen.util.SystemHelper;

public class Parser {
	private final static int MAX_DEPTH = 4;
	Tree<String> tree = new Tree<String>(null, MAX_DEPTH, 10, 0.3);
	String fileName = "F:\\log data\\act-test64-2018.11.20.log";
	
	public Parser(){

	}
	
	public void process(String log, double ID) {
		Node<String> leafNode = traverse(log);
		if (leafNode == null) return;
		LogGroup logGroup = leafNode.findGroup(log);
		if (logGroup == null) {
			logGroup = leafNode.addGroup(log, ID);
		} else {
			logGroup.update(log);
		}
		//System.out.println(logGroup.toString());
	}
	
	private Node<String> traverse(String log){
		Node<String> node;
		String[] seq = log.split(ParseConstant.PRIMARY_SEPARATOR);
		//对开头字符串就是数字的日志进行预处理
		for (int i = 0; i < ((MAX_DEPTH - 2) <  seq.length ? (MAX_DEPTH - 2) : seq.length); i++) {
			seq[i] = transformDigit(seq[i]);
		}
		String len = String.valueOf(seq.length);
		node = tree.root().findChild(len);
		Pattern pattern = Pattern.compile("[0-9]*");
		if (node == null) {
			node = tree.addNode(len, tree.root());
			if (node == null) {
				System.err.println("分支数达最大，无匹配日志长度");
				return null;
			}
		}
		Node<String> child_node;
		try {
			for (int i = 0; i < ((MAX_DEPTH - 2) <  seq.length ? (MAX_DEPTH - 2) : seq.length); i++) {
				child_node = node.findChild(pattern.matcher(seq[i]).matches()?"*":seq[i]);
				if (child_node == null) {
					if (pattern.matcher(seq[i]).matches()) {
						 child_node = tree.addNode("*", node);
					}else child_node = tree.addNode(seq[i], node);
					if (child_node == null) {
						System.err.println("分支数达最大，无匹配token");
						return null;
					}
				}
				node = child_node;
			}
		}catch (Exception e){
			System.out.println(log);
		}
		return node;
	}
	
	private String transformDigit(String token) {
		char[] chars = token.toCharArray();
		int count_num = 0;
		for (int i = 0; i < chars.length; i++) {
			if(chars[i] >= 48 && chars[i] <= 57){
				count_num++;
			}
		}
		if (count_num >= chars.length * 0.618) return "*";
		return token;
	}
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		SystemHelper.getInstance().getMemoryForWindows();
		Parser parser = new Parser();
		double cnt = 0;
		long startTime=System.nanoTime();   //获取开始时间 
		System.out.println("Running...");
		try {   
	        FileReader reader = new FileReader(parser.fileName);
	        BufferedReader br = new BufferedReader(reader);
	        String str = null;       
	        while ((str = br.readLine()) != null) {  
	        	String line = Preprocessor.getInstance().jsonProcessor(str);
	            parser.process(line, cnt);
	            cnt++;
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
		System.out.println(cnt);
		long endTime=System.nanoTime(); //获取结束时间  
		System.out.println("程序运行时间： "+(endTime-startTime)/1000000000+"s");
		List<Node<String>> nodes = parser.tree.nodes;
		DataOutput out = new DataOutput("F:\\drain");
		int groupNum = 0;
		//allGroups所有日志集合的列表
		List<LogGroup> allGroups = new ArrayList<LogGroup>();
		//按不同的日志集合输出分组的结果
		SystemHelper.getInstance().getMemoryForWindows();
		for (int i = 0; i < nodes.size(); i++) {
			if (nodes.get(i).getDepth() == MAX_DEPTH) {
				List<LogGroup> groupList = nodes.get(i).groupList;
				for (int j = 0; j < groupList.size(); j++) {
					allGroups.add(groupList.get(j));
					groupNum++;
					out.writeStrToFile("events.log", groupList.get(j).toString());
					out.outputByGroups(groupNum, groupList.get(j).getLogSet(), groupList.get(j).toString());
				}
			}
		}
	}
}
