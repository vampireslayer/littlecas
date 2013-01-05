package termparser;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class OperationNode extends Node{
	private char operation;
	public OperationNode(char operation){
		this.operation = operation;
	}
	public NumberNode calculate(){
		BigDecimal erg = BigDecimal.ONE;
		if(this.operation == '+'){
			List<Node> children= this.getChildren();
			erg = new BigDecimal(((NumberNode)children.get(0)).get_number());
			erg = erg.add(new BigDecimal(((NumberNode)children.get(1)).get_number()));
		}
		if(this.operation == '-'){
			List<Node> children= this.getChildren();
			erg = new BigDecimal(((NumberNode)children.get(0)).get_number());
			erg = erg.subtract(new BigDecimal(((NumberNode)children.get(1)).get_number()));		
		}
		if(this.operation == '*'){
			List<Node> children= this.getChildren();
			erg = new BigDecimal(((NumberNode)children.get(0)).get_number());
			erg = erg.multiply(new BigDecimal(((NumberNode)children.get(1)).get_number()));
		}
		if(this.operation == '/'){
			List<Node> children= this.getChildren();
			erg = new BigDecimal(((NumberNode)children.get(0)).get_number());
			erg = erg.divide(new BigDecimal(((NumberNode)children.get(1)).get_number()),RoundingMode.HALF_UP);
		}
		if(this.operation == '^'){
			List<Node> children= this.getChildren();
			erg = new BigDecimal(((NumberNode)children.get(0)).get_number());
			erg = erg.multiply(new BigDecimal(((NumberNode)children.get(1)).get_number()));			
		}
		return new NumberNode(erg.toString());
	}
	public char get_operation(){
		return this.operation;
	}	
	public Integer get_priority(){
		switch (this.operation) {
		case '+':
			return 1;			
		case '-':
			return 1;	
		case '*':
			return 2;	
		case '/':
			return 2;	
		case '^':
			return 3;
		}
		return 1;
	}
}
