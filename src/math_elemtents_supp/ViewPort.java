package math_elemtents_supp;

public class ViewPort {
	private double xmin,xmax,ymin,ymax;
	public ViewPort(double xmin,double xmax,double ymin,double ymax){
		this.xmin = xmin;
		this.xmax = xmax;
		this.ymin = ymin;
		this.ymax = ymax;
	}
	public double get_xmin(){ return this.xmin; }
	public double get_xmax(){ return this.xmax; }
	public double get_ymin(){ return this.ymin; }
	public double get_ymax(){ return this.ymax; }	
}
