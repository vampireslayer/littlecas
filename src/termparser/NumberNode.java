package termparser;

import java.math.BigDecimal;

public class NumberNode extends Node{
	private String number;
	private String[] constants = {"pi","e","x"};
	public NumberNode(String number){		
		this.number = number;
	}
	public String get_number(){
		if(this.number.equals("pi")){
			return String.valueOf(Math.PI);
		}
		return this.number;		
	}
	public void set_number(String numberString){
		this.number = numberString;
	}
	public String[] get_constants(){
		return this.constants;
	}
}
