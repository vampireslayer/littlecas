package plotting;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

import math_elements.Function;
import math_elemtents_supp.*;

public class PlottingPanel extends JPanel {
	private PlottingModel model = new PlottingModel();
	
		
	public PlottingModel getModel(){
		return model;		
	}
	
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        List<Function> functions = model == null ? Collections.<Function>emptyList() : model.getFunctions();
        ViewPort viewPort = model.getViewPort();
		int count = 1;
		Color color = Color.BLACK;
        for(Function function : functions){        	
    			switch (count) {
    			case 2:
    				color = Color.BLUE;
    				break;
    			case 3:
    				color = Color.GREEN;
    				break;
    			case 4:
    				color = Color.RED;
    				break;
    			case 5:
    				color = Color.CYAN;
    				break;
    			case 6:
    				color = Color.GRAY;
    				break;			
    			}
    			BigDecimal ymax = BigDecimal.valueOf(viewPort.get_ymax());
    			BigDecimal ymin = BigDecimal.valueOf(viewPort.get_ymin());
    			BigDecimal xmin = BigDecimal.valueOf(viewPort.get_xmin());
    			BigDecimal xmax = BigDecimal.valueOf(viewPort.get_xmax());
    			BigDecimal koord_xratio = BigDecimal.ONE.divide(xmax.subtract(xmin),5,RoundingMode.HALF_UP).multiply(new BigDecimal(this.getWidth()));
    			BigDecimal koord_yratio = BigDecimal.ONE.divide(ymax.subtract(ymin),5,RoundingMode.HALF_UP).multiply(new BigDecimal(this.getHeight()));
    			BigDecimal koord_xoffset = koord_xratio.multiply(xmin);
    			BigDecimal koord_yoffset = koord_yratio.multiply(ymin);

    			g.setColor(color);
    			g.drawString(function.get_formula(),this.getWidth()-100,20*count);
    			
    			for (ValuePair pair : function.get_values()){				
    				try {
    					g.draw3DRect(pair.getx().multiply(koord_xratio).subtract(koord_xoffset).intValue(), this.getHeight()-pair.gety().multiply(koord_yratio).subtract(koord_yoffset).intValue(), 1,1,false);
//    					this.setRGB(pair.getx().multiply(koord_xratio).subtract(koord_xoffset).intValue(),this.getHeight()-pair.gety().multiply(koord_yratio).subtract(koord_yoffset).intValue(), color.getRGB());
    				} catch (Exception e) {
    					// TODO: handle exception
    				}
    			}
    			count++;
        }
        if(viewPort != null){
        	g.setColor(Color.black);
        	// Achsen zeichnen
    		// drei fälle für min und max ++ +- --
    		double yratio=0;
    		double xratio=0;
    		//Y-ACHSE Fall --
    		if(viewPort.get_xmax() <= 0 && viewPort.get_xmin() < 0){yratio = 1;}
    		//Y-Achse Fall +-
    		if(viewPort.get_xmax() > 0 && viewPort.get_xmin() < 0){yratio = Math.abs(viewPort.get_xmin())/(viewPort.get_xmax()-viewPort.get_xmin());}
    		//Y-Achse Fall ++
    		if(viewPort.get_xmax() > 0 && viewPort.get_xmin() >= 0){yratio = 0;}
    		g.drawLine((int)(this.getWidth()*yratio), 0, (int)(this.getWidth()*yratio), this.getHeight());
//    		g.draw(new Line2D.Double(this.getWidth()*yratio,0,this.getWidth()*yratio,this.getHeight()));
    		//X-Achse Fall --
    		if(viewPort.get_ymax() <= 0 && viewPort.get_ymin() < 0){xratio=0;}
    		//X-Achse Fall +-	
    		if(viewPort.get_ymax() > 0 && viewPort.get_ymin() < 0){xratio = Math.abs(viewPort.get_ymax())/(viewPort.get_ymax()-viewPort.get_ymin());}
    		//X-Achse Fall ++	
    		if(viewPort.get_ymax() > 0 && viewPort.get_ymin() >= 0){xratio = 1;}
    		g.drawLine(0, (int)(this.getHeight()*xratio), this.getWidth(), (int)(this.getHeight()*xratio));
//    		g.draw(new Line2D.Double(0, this.getHeight()*xratio, this.getWidth(), this.getHeight()*xratio));
         	
        }
        this.repaint();
	}
}
