package org.xbchen.parse;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.regex.Pattern;

import org.xbchen.datastructure.LogGroup;
import org.xbchen.datastructure.Tree;
import org.xbchen.datastructure.Tree.Node;
import org.xbchen.storage.DataOutput;

public class Parser {
	private static final String DELIMITER = "\\s+";
	Tree<String> tree = new Tree<String>(null, 4, 10, 0.3);
	String fileName = "windows-multi-2018.10.16.txt";
	
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
		String[] seq = log.split(DELIMITER);
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
		for (int i = 0; i < tree.getMaxDepth() - 2; i++) {
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
		
		return node;
	}
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
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
		for (int i = 0; i < nodes.size(); i++) {
			if (nodes.get(i).getDepth() == 4) {
				List<LogGroup> groupList = nodes.get(i).groupList;
				for (int j = 0; j < groupList.size(); j++) {
					groupNum++;
					out.writeStrToFile("events.txt", groupList.get(j).toString());
					out.outputByGroups(groupNum, groupList.get(j).getLogSet(), groupList.get(j).toString());
				}
			}
		}
	}
}
