package org.xbchen.datastructure;

import java.util.ArrayList;
import java.util.List;

public class Tree<E> {
	public static class Node<T>{
		T data;
		int parent;
		int childNum;
		public List<Node <T>> children = new ArrayList<Node <T>>();
		private int depth;
		public List<LogGroup> groupList = new ArrayList<LogGroup>();
		public Node() {
			childNum = 0;
		}
		
		public Node(T data) {
			this.data = data;
			childNum = 0;
		}
		
		public Node(T data, int parent, int depth) {
			this.data = data;
			this.parent = parent;
			this.depth = depth;
			childNum = 0;
		}
		
		public void addChild(Node<T> child) {
			children.add(child);
		}
		
		public Node<T> findChild(T data) {
			for (Node<T> node : children) {
				if (node.data.equals(data))
					return node;
			}
			return null;
		}
		
		public LogGroup addGroup(String log, double ID) {
			LogGroup logGroup = new LogGroup(log, ID);
			groupList.add(logGroup);
			return logGroup;
		}
		
		public LogGroup findGroup(String log) {
			double max_sim = 0;
			double sim = 0;
			int index = -1;
			for (int i = 0; i < groupList.size(); i++) {
				sim = groupList.get(i).calSimilarity(log);
				if (sim > max_sim) {
					max_sim = sim;
					index = i;
				}
			}
			if (max_sim > THRESHOLD) return groupList.get(index);
			return null;
		}
		
		public int getDepth() {
			return depth;
		}
		
		public String toString() {
			return "[data=" +data+ ", parent="+parent+"]";
		}
	}
	private static double THRESHOLD;
	private int MAX_TREE_DEPTH;
	private int MAX_CHILD;
	
	public List<Node <E>> nodes = new ArrayList<Node <E>>();
	
	public Tree(E data, int maxDepth, int maxChild, double threshold) {
		MAX_TREE_DEPTH = maxDepth;
		MAX_CHILD = maxChild;
		THRESHOLD = threshold;
		nodes.add(new Node<E>(data, -1, 1));
	}
	
	public Node<E> addNode(E data, Node<E> parent) {
		if (parent.childNum >= MAX_CHILD || parent.depth == MAX_TREE_DEPTH) return null;
		Node <E> node = new Node<E>(data, nodes.indexOf(parent), parent.getDepth() + 1);
		nodes.add(node);
		parent.addChild(node);
		return node;
	}
	
	public int getMaxDepth() {
		return MAX_TREE_DEPTH;
	}
	
	public boolean empty() {
		return nodes.isEmpty();
	}
	
	public Node<E> root(){
		return nodes.get(0);
	}
	
	public Node<E> getParent(Node<E> node){
		return nodes.get(node.parent);
	}
	
	public Node<E> searchChild(E data, Node<E> parent){
		return parent.findChild(data);
	}
	
	public LogGroup searchGroup(String log, Node<E> node) {
		return node.findGroup(log);
	}
	
	
}
