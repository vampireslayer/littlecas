package termparser;

import java.util.ArrayList;
import java.util.List;

public class Node {
	private List<Node> children = new ArrayList<>();
	private int depth;
	protected Node parent;
	private boolean handleAsNumber;
	
	public Node(){
		this.handleAsNumber = false;
	}
	public Node(Node anode){
		this(anode.getChildrenClone(), new Node(anode.getParent()), anode.isNumber());
	}
	public Node(List<Node> children, Node parent,boolean handleasnumber){
		handleAsNumber = handleasnumber;
		this.children = children;
		this.parent = parent;
	}
	public List<Node> getChildrenClone() {
		List<Node> helpList = new ArrayList<>();
		for(Node node : children){
			Node help = new Node(node);
			helpList.add(help);
		}
		return helpList;
	}
	public void addChild(Node node){
		this.children.add(node);		
	}
	public void removeChild(Node node){
		children.remove(node);
	}
	public void replaceChild(int index,Node node){				
		this.children.set(index, node);
	}
	public boolean isFirstChild(Node node){
		return this.children.get(0).equals(node);
	}
	public void handleAsNumber() {
		this.handleAsNumber = true;
	}
	public boolean isNumber(){
		return this.handleAsNumber;
	}
	public List<Node> getChildren(){
		return this.children;
	}
	public Node getParent(){
		return this.parent;
	}
	public void setParent(Node node){
		this.parent = node;
	}	
	public void setdepth(int depth){
		this.depth = depth;
	}
	public int getdeph(){
		return this.depth;
	}
	public boolean hasChild(){
		return !children.isEmpty();
	}
	public Node getOperationChild(){
		for(Node childNode : children){
			if(childNode instanceof OperationNode || childNode instanceof FunctionNode){
				return childNode;
			}
		}
		return new Node();
	}
	public boolean hasParent(){		
		 if(parent == null){
			 return false;
		 }else{
			 return true;
		 }
	}
	public boolean hasOperationChild(){
		for(Node childNode : children){
			if(childNode instanceof OperationNode || childNode instanceof FunctionNode){
				return true;
			}
		}
		return false;
	}
}
