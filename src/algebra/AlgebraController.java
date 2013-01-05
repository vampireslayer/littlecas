package algebra;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;


public class AlgebraController {
	private AlgebraPanel panel = new AlgebraPanel();
	
	
	public AlgebraController(){
		panel.setBounds(0, 50, 100, 100);
		panel.setBackground(Color.gray);
		
		panel.add(new JLabel("hier gibts irgendwann Algebra"));		
	}
	
	public AlgebraPanel getPanel(){
		return panel;
	}
}
