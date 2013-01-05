package termparser;

import java.util.ArrayList;
import java.util.List;

public final class NodeTree{
	private List<Node> nodeList = new ArrayList<>();
	private String nameString;
	
	public NodeTree(List<Node> list,String aString){
		nodeList = list;
		nameString = aString;
	}	
	public NodeTree(NodeTree anotherTree){
		this(anotherTree.getClonedList(),anotherTree.getstring());
	}	
	public NodeTree(){}
	public List<Node> getNodeList(){
		return nodeList;
	}	
	public List<Node> getClonedList(){
		List<Node> helpList = new ArrayList<>();
		for(Node node : nodeList){
			Node help = new Node(node);
			helpList.add(help);
		}
		return helpList;
	}
	public String getstring(){
		return nameString;
	}
	public void setstring(String string){
		nameString = string;
	}
	public void addToNodeList(Node node){
		nodeList.add(node);
	}
	public void addRoot(Node node){
		nodeList.add(0, node);
	}
}
