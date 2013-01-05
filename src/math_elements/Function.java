package math_elements;

import math_elemtents_supp.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;

import termparser.Parser;

public class Function {
	private String formula;
	private BigDecimal xmin;
	private BigDecimal xmax;
	private ArrayList<ValuePair> values = new ArrayList<>();	
	
	public Function(BigDecimal xmin,BigDecimal xmax,double step){
		this.xmin = xmin;
		this.xmax = xmax;
	}
	
	public Function(String formula,ViewPort port,double step){
		this.formula = formula;
		this.xmin = BigDecimal.valueOf(port.get_xmin());
		this.xmax = BigDecimal.valueOf(port.get_xmax());
		
		Parser parser = new Parser();
		BigDecimal run = xmin;				
		while(run.compareTo(xmax) <= 0){			
			try {								
				this.add_valuepair(run, parser.parse_x(formula, String.valueOf(run)));
				run = run.add(BigDecimal.valueOf(step));				
			} catch (ArithmeticException e) {
				run = run.add(BigDecimal.valueOf(step));
				System.out.println("Arithmetik Exception wahrscheinlich Division durch 0 übersprungen." + e.getMessage()+e.getLocalizedMessage());
				continue;
			}			
		}
		
	}
	public void add_valuepair(BigDecimal x,BigDecimal y){
		ValuePair v = new ValuePair(x, y);
		values.add(v);		
	}
	public ValuePair get_valuepair(int index){
		return this.values.get(index);
	}
	public ArrayList<ValuePair> get_values(){
		return this.values;
	}
	public String get_formula(){
		return this.formula;
	}
	public BigDecimal get_ymin(){
		BigDecimal ymin = values.get(0).gety();
		for (ValuePair pair : values){
			if(pair.gety().compareTo(ymin) < 0){
				ymin = pair.gety();
			}
		}
		return ymin;
	}
	public BigDecimal get_ymax(){
		BigDecimal ymax = values.get(0).gety();
		for (ValuePair pair : values){
			if(pair.gety().compareTo(ymax) > 0){
				ymax = pair.gety();
			}
		}
		return ymax;
	}
	public BigDecimal get_xmin(){
		return xmin;
	}
	public BigDecimal get_xmax(){
		return xmax;
	}
	public void y_truncate(BigDecimal ymin,BigDecimal ymax){
		Iterator<ValuePair> iter = this.values.iterator();
		while(iter.hasNext()){
			ValuePair next = iter.next();			
			if (next.gety().compareTo(ymin) == -1 || next.gety().compareTo(ymax) == 1) {
				System.out.println("asd: "+ next.gety() + ymax);
				iter.remove();
			}
		}
	}
	public void x_truncate(BigDecimal xmin, BigDecimal xmax){
		Iterator<ValuePair> iter = this.values.iterator();
		while(iter.hasNext()){
			ValuePair next = iter.next();
			if (next.getx().compareTo(xmin) == -1 || next.getx().compareTo(xmax) == 1) {
				iter.remove();
			}
		}
	}
}
