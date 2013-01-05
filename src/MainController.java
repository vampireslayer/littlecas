import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import algebra.AlgebraController;
import algebra.AlgebraPanel;

import plotting.PlottingController;


public class MainController {
	private JFrame mainFrame = new JFrame("littleCAS");    
	private String featureString[] = {"Plotting","Algebra"};
	private JComboBox features = new JComboBox(featureString);
	private JLabel label = new JLabel("Wähle den gewünschten Modus aus:");
	
	private ActionListener comboListener = new ActionListener() {		
		@Override
		public void actionPerformed(ActionEvent e) {			
			clean();
			if(features.getSelectedItem().toString().equals(featureString[0])){	
				PlottingController controller = new PlottingController();
				controller.getPanel().setBounds(0, 30, 1200, 770);
				mainFrame.getContentPane().add(controller.getPanel());
			}else if(features.getSelectedItem().toString().equals(featureString[1])){			
				AlgebraController controller = new AlgebraController();	
				controller.getPanel().setBounds(0, 30, 1200, 770);
				mainFrame.getContentPane().add(controller.getPanel());
			}
		}
	};
	public MainController() {
		features.setSelectedIndex(0);
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(1200,800);
		mainFrame.setLayout(null);
		mainFrame.getContentPane().setLayout(null);
		//settings
		features.setBounds(250, 0, 200, 30);
		label.setBounds(10, 2, 230, 30);		
		//add elements
		mainFrame.add(features);
		mainFrame.add(label);
		//add Listener	
		features.addActionListener(comboListener);
		//start with Plotting
		comboListener.actionPerformed(new ActionEvent(features, 0, featureString[0]));			
    }
    public void show() {
    	mainFrame.setVisible(true);
    }
    private void clean(){
		for(Component component : mainFrame.getContentPane().getComponents()){
			//System.out.println(component.getClass());
			if (component instanceof JPanel) {
				mainFrame.getContentPane().remove(component);
			}						
		}	
		mainFrame.getContentPane().repaint();
    }
}
