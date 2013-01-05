package termparser;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;;

/* TODO
 * eckige Klammern
 */
public class Nodenizer {
	public Nodenizer(){}
	public List<Node> nodenize(String formula){
		List<Node> nodeList = new ArrayList<Node>();
		//Klammern um die Formel
		formula = "("+formula+")";		
		formula = formula.replaceAll(" ", "");
		//System.out.println(formula);
		int offset = 0;
		int length = formula.length();
		while(offset < length){
			char current = formula.charAt(offset); 					
			
			if(!Character.isWhitespace(current)){		
				int checkbracket,checkoperation;
				if((checkbracket = "{[()]}".indexOf(current)) != -1){
					nodeList.add(new Bracket(current));
					offset++;
				}else if(Character.isDigit(current) || current == '.'){
					int look = offset + 1;
					boolean pointseen = current == '.'; // entscheident für unterscheidung .5 oder 12.5 
					
					while(look < length){
						char next = formula.charAt(look);
						if (Character.isDigit(next)) {
							look++;
						}else if(next == '.' && !pointseen){
							pointseen = true;
							look++;							
						}else{
							break;
						}
					}
					nodeList.add(new NumberNode(formula.substring(offset, look)));
					offset = look;
				}else if ((checkoperation = "+-/*^".indexOf(current)) != -1) {
					// Falls ein - auftritt vor einer Zahl bei zB (-10*4)					
					if(current == '-' && (!Character.isDigit(formula.charAt(offset-1)) && !(formula.charAt(offset-1) == ')')) && (Character.isDigit(formula.charAt(offset+1)) || formula.charAt(offset+1) == '.')){
						int look = offset +1;
						boolean pointseen = look == '.';
						while(look < length){
							char next = formula.charAt(look);
							if (Character.isDigit(next)) {
								look++;
							}else if(next == '.' && !pointseen){
								pointseen = true;
								look++;							
							}else{
								break;
							}
						}
						nodeList.add(new NumberNode(formula.substring(offset, look)));
						offset = look;
					}else{
						nodeList.add(new OperationNode(current));
						offset++;
					}						
				}else if(Character.isAlphabetic(current)){
					int look = offset + 1;
					while(look < length){
						char next = formula.charAt(look);
						if(Character.isAlphabetic(next)){
							look++;
						}else{
							break;
						}
					}
					String tocheck = formula.substring(offset,look);
					NumberNode node = new NumberNode(tocheck);
					String[] constants= node.get_constants();
					boolean wasconstant = false;
					for(String constant : constants){
						if(constant.equals(tocheck)){
							nodeList.add(node);
							wasconstant = true;
						}
					}
					boolean wasfunction = false;
					if(!wasconstant){
						FunctionNode fnode = new FunctionNode(tocheck);
						String[] functions = fnode.get_function();
						for(String function : functions){
							if(function.equals(tocheck)){
								nodeList.add(fnode);
								wasfunction = true;
							}
						}
					}					
					if(!wasfunction && !wasconstant){
						throw new IllegalArgumentException("Text represents neither Constant nor Function");
					}
					offset = look;
				}				
			}else{
				offset++;
			}
		}
//		System.out.println(nodeList);
		// Da beim Parsen Terme wie 2(1+2) oder 2sin(x) oder 2pi richtig zerlegt werden muss nur das * eingefügt werden
		for(int i = 0; i < nodeList.size();i++){
			if(nodeList.get(i) instanceof NumberNode){
				if(nodeList.get(i-1) instanceof Bracket && ((Bracket)nodeList.get(i-1)).isClose()){
					// der Fall (1+2)3
					nodeList.add(i, new OperationNode('*'));
				}else if((nodeList.get(i+1) instanceof Bracket && ((Bracket)nodeList.get(i+1)).isOpen()) || nodeList.get(i+1) instanceof NumberNode || nodeList.get(i+1) instanceof FunctionNode){
					// Fall 3( oder 3sin oder 3pi oder pi3
					nodeList.add(i+1,new OperationNode('*'));
				}
			}
		}
//		System.out.println(nodeList);
		return nodeList;
	}
}
