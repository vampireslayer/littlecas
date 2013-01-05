package termparser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.math.BigDecimal;

public class Parser {
	private NodeTree nodeTree = new NodeTree();
	private  Node rootNode;	
	private List<Node> nodeListe = new ArrayList<>();
	private int max_depth;
	public Parser(String formula){
		rootNode = this.parse(formula);	
		nodeTree.setstring("test");
//		print_rek(nodeTree.getNodeList().get(0),"    ");
	}
	public Parser(){}
	public BigDecimal parse_x(String formula,String value){
		formula = formula.replaceAll("x",value);
		this.parse(formula);
		return this.calculate(this.nodeTree.getNodeList().get(0));
	}
	
	public BigDecimal calculate(Node startNode){			
		while(startNode.hasChild()){
			if(startNode.hasOperationChild()){
				startNode = startNode.getOperationChild();
			}else{
				NumberNode node = new NumberNode("1");
				if(startNode instanceof OperationNode){
					node = ((OperationNode)startNode).calculate();
				}else if(startNode instanceof FunctionNode){
					node = ((FunctionNode)startNode).calculate();
				}
//				System.out.println(node.get_number());				
				if(startNode.hasParent()){
					Node help  = startNode;					
					startNode = startNode.parent;					
					if(startNode.isFirstChild(help)){
						startNode.replaceChild(0, node);						
					}else{
						startNode.replaceChild(1, node);
					}										
				}else{
					startNode = node;
				}
			}
		}
		return new BigDecimal(((NumberNode)startNode).get_number());
	}
	public BigDecimal calculate(){
		return this.calculate(this.nodeTree.getNodeList().get(0));
	}
	public BigDecimal calculate(String var,String replace){
		print_rek(nodeTree.getNodeList().get(0), nodeTree.getstring() + "     ");
		NodeTree copyNodeTree = new NodeTree(nodeTree);
		Node start = copyNodeTree.getNodeList().get(0);
		copyNodeTree.setstring("aaa");
		replace_var_rek(start, var, replace);		
		print_rek(start, copyNodeTree.getstring()+"   ");
		return calculate(start);
	}
	private void replace_var_rek(Node node,String var,String replace){				
		if(node instanceof NumberNode && ((NumberNode)node).get_number().equals("x")){
			((NumberNode)node).set_number(replace);			
		}
		if(node.hasChild()){
			for(Node child : node.getChildren()){
				replace_var_rek(child,var,replace);
			}
		}
	}
//	private BigDecimal calc(Node node){
//		if(!node.hasChild()){
//			if(node.parent instanceof OperationNode){
//				NumberNode ololNode = new NumberNode(((OperationNode) node.parent).truncate_to_BigDecimal().toString());
//				node.parent.parent.removeChild(node);
//				node.parent.parent.addChild(ololNode);
//			}
//		}else{			
//			for(Node child : node.getChildren()){
//				calc(child);
//			}
//		}
//		return BigDecimal.ONE;
//	}
	private Node parse(String formula){
		Nodenizer nodenizer = new Nodenizer();
		this.nodeListe = nodenizer.nodenize(normalizeFormula(formula));
		List<Node> nodeList = new ArrayList<>(this.nodeListe);
		/*
		 * shit is about to get real
		 */
//		printTree();
		//		
		while (nodeList.size() > 1){		
//			System.out.println("vor Klammer auflösen"+nodeList);
			int offset=0,start=0,end = nodeList.size()-1;
			for(Node node : nodeList){			
				if(node instanceof Bracket){
					if(((Bracket) node).isOpen()){
						start = offset;
					}
					if(((Bracket) node).isClose()){					
						end = offset;
						break;
					}
				}
				offset++;
			}
//			System.out.println("start: "+start+" end: "+end);
			Node node = parse(start+1, end-1,nodeList);// den inhalt der Klammer parsen	
			node.handleAsNumber();
			for(int i = start;i<=end;i++ ){	//die Klammer löschen
				nodeList.remove(start);
			}
			nodeList.add(start, node);	//ersetzen durch den node			
			//printTree();			
		}		
		nodeTree.addRoot((nodeList.get(0)));
		return nodeList.get(0);
	}	
	private Node parse(int start,int end,List<Node> nodeList){
		//neue Liste aus den end - offset		
		List<Node> helpList = new ArrayList<>();
		for(int i = start;i < end+1;i++){
			helpList.add(nodeList.get(i));
		}

//		System.out.println(helpList+"size: "+helpList.size());

		//wenn Länge gleich 1 dann direkt returned minimal performance
		if(helpList.size() == 1){
			return helpList.get(0);
		}
		//wenn function dabei wird das nächste als child geaddet und aus der liste entfernt
		ArrayList<Integer> positionsArrayList = new ArrayList<>();
		for(int i = 0;i<helpList.size();i++){
			if(helpList.get(i) instanceof FunctionNode && !helpList.get(i).isNumber()){				
				positionsArrayList.add(i);
			}
		}
		for(Integer posInteger : positionsArrayList){
			Node help = helpList.get(posInteger+1);
			help.setParent(helpList.get(posInteger));
			helpList.get(posInteger).addChild(help);
			helpList.remove(posInteger+1);
			//test einfügen in nodeTree
			nodeTree.addToNodeList(help);
			//---------------------------
			for(int i = 0; i<positionsArrayList.size();i++){
				positionsArrayList.set(i, positionsArrayList.get(i)-1);
			}
		}
//		Iterator<Node> iter = helpList.iterator();
//		int count=0;
//		while(iter.hasNext()){			
//			Node node = iter.next();
//			if(node instanceof FunctionNode){
//				node.addChild(helpList.get(count+1));
//				helpList.remove(count+1);
//			}
//			count++;
//		} 
		//wenn Länge gleich 1 dann direkt returned minimal performance
		if(helpList.size() == 1){
			return helpList.get(0);
		}
		//wenn ^ dabei wird das vorherige und das nächste als child geaddet und entfernt		
			for(int p = 3;p>0;p--){			
			ArrayList<Integer> positions = new ArrayList<>();		
			for (int i = 0;i<helpList.size();i++ ) {
				//System.out.println(helpList.get(i).isNumber());
				if(helpList.get(i) instanceof  OperationNode && ((OperationNode) helpList.get(i)).get_priority() == p && !helpList.get(i).isNumber()){
					positions.add(i);
				}
			}
			// TODO
			// HANDLE AS NUMBER FLAG falls 
			for(Integer position : positions){
				Node child1 = new Node();
				Node child2 = new Node();
				child1 = helpList.get(position-1);
				child2 = helpList.get(position+1);
				child1.setParent(helpList.get(position));
				child2.setParent(helpList.get(position));
				helpList.get(position).addChild(child1);	
				helpList.get(position).addChild(child2);
				//-------------
				nodeTree.addToNodeList(child1);
				nodeTree.addToNodeList(child2);
				//------------------
				helpList.remove(position+1);
				helpList.remove(position-1);
				for(int i =0;i<positions.size();i++){
					positions.set(i, positions.get(i)-2);
				}
				if(helpList.size() == 1){
					return helpList.get(0);
				}
			}
			}
//			System.out.println(helpList);
			//helpList.get(0).handleAsNumber();
		return helpList.get(0);
	}
	private void printTree(){
			
	}	
	private void print_rek(Node node,String space){
		if(!node.hasChild()){
			if(node instanceof NumberNode){
				System.out.println(space+((NumberNode)node).get_number() + node.hasParent());
			}else if (node instanceof FunctionNode){
				System.out.println(space+((FunctionNode)node).get_function() + node.hasParent());
			}else if(node instanceof OperationNode){
				System.out.println(space+((OperationNode)node).get_operation() + node.hasParent());
			}						
		}else{
			if(node instanceof NumberNode){
				System.out.println(space+((NumberNode)node).get_number()+ " has children: "+node.getChildren().size() + node.hasParent());
			}else if (node instanceof FunctionNode){
				System.out.println(space+((FunctionNode)node).get_function()+ " has children: "+node.getChildren().size() + node.hasParent());
			}else if(node instanceof OperationNode){
				System.out.println(space+((OperationNode)node).get_operation()+ " has children: "+node.getChildren().size() + node.hasParent());
			}			
			for(Node child : node.getChildren()){
				print_rek(child,space+space);
			}
		}
	}
	/* 
	 * Die Funktion soll die Eingabe normalisieren
	 * z.B. 2x zu 2*x oder 2sin(x) zu 2*sin(x) oder 2(x+3) zu 2*(x+3) etc
	 * und ++ entfernen oder +- oder -- etc
	 * kann evt auch gleich auf Fehler checken wie klammern zählen etc
	 */
	private String normalizeFormula(String formula){
		return formula;
	}
}
