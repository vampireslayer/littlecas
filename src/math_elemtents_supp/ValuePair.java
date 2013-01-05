package math_elemtents_supp;

import java.math.BigDecimal;

public class ValuePair {
	private BigDecimal x;
	private BigDecimal y;
	
	public ValuePair(BigDecimal x,BigDecimal y){
		this.x=x;
		this.y=y;
	}
	public BigDecimal getx(){
		return this.x;
	}
	public BigDecimal gety(){
		return this.y;
	}	
}
