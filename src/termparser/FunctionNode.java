package termparser;

import java.math.BigDecimal;

public class FunctionNode extends Node{
	private String[] functions = {"sin","cos","tan","exp","abs","atan","acos","asin","ln"};
	private String function;
	public FunctionNode(String func){
		this.function = func;
	}
	public NumberNode calculate(){		
		return new NumberNode(BigDecimal.valueOf(Math.sin(Double.valueOf(((NumberNode)this.getChildren().get(0)).get_number()))).toString());		
	}	
	public String[] get_function(){
		return this.functions;
	}
}
