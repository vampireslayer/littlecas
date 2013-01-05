package plotting;


import math_elemtents_supp.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import math_elements.Function;

public class PlottingController {
	private PlottingPanel funcView = new PlottingPanel();	
	private JPanel panel = new JPanel();//new GridLayout(2, 2)
	private JTextField inputField = new JTextField("plot x;sin(x),from -3 to 3,yfrom -3 to 3");
	private JButton startButton = new JButton("GO!");
	
	private ActionListener buttonListener = new ActionListener() {		
		@Override
		public void actionPerformed(ActionEvent e) {
			funcView.getModel().clearModel();
			String input = inputField.getText();
			double xmax = 6.3;// Std einstellung
			double ymax = 3.1;
			double xmin = -6.3;
			double ymin = -3.1; 
			double step = 0.001;
			String formula[] = {""};
			
			String[] testStrings = input.split(",");
			for (String string : testStrings) {
				if(string.trim().startsWith("plot")){
					String helperString = string.substring(4, string.length()).trim();
					formula = helperString.split(";");								
				}
				if(string.trim().startsWith("from")){
					xmin = Double.valueOf(string.substring(string.indexOf("from")+4,string.indexOf("to")).trim());
					xmax = Double.valueOf(string.substring(string.indexOf("to")+2, string.length()).trim());
					if(xmin > xmax){
						double help = xmax;
						xmax = xmin;
						xmin = help;					
					}
				}
				if(string.trim().startsWith("yfrom")){
					ymin = Double.valueOf(string.substring(string.indexOf("yfrom")+5,string.indexOf("to")).trim());
					ymax = Double.valueOf(string.substring(string.indexOf("to")+2, string.length()).trim());
					if(ymin > ymax){
						double help = ymax;
						ymax = ymin;
						ymin = help;					
					}
				}
				if(string.trim().startsWith("step")){
					step = Double.valueOf(string.trim().substring(4, string.trim().length()).trim());
				}
			}			
						
			ViewPort viewPort = new ViewPort(xmin, xmax, ymin, ymax);
			funcView.getModel().setViewPort(viewPort);
			
			for (String formString : formula) {				
				Function function = new Function(formString, viewPort, step);
				funcView.getModel().addFunction(function);
			}								
		}
	};
	
	public PlottingController(){
		panel.setLayout(null);
		
		inputField.setBounds(0, 0,400,30);
		startButton.setBounds(400,0, 100, 30);
		funcView.setBounds(0,30,1200,740);
		
		panel.setBackground(Color.black);		
		funcView.setBackground(Color.white);
		
		startButton.addActionListener(buttonListener);
		
		panel.add(inputField);
		panel.add(startButton);				
		panel.add(funcView);
	}
	
	public JPanel getPanel(){
		return panel;
	}
}
