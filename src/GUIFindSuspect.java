import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class GUIFindSuspect extends JFrame {	
	
	private JPanel panelFindSuspect;
	
	private JTextField suspectsName;
	
	private JButton find;
	private JButton visualizeButton;
	
	private Registry record;
	
	
	public GUIFindSuspect(Registry record) {
		
		//Load the registry
		this.record = record;
		
		// Create Panel and components
		panelFindSuspect = new JPanel();
		
		suspectsName = new JTextField("Please enter suspect name", 18);		
				
		find = new JButton("Find");
		visualizeButton = new JButton("Visualize Network");	
		
		panelFindSuspect.add(suspectsName);
		panelFindSuspect.add(find);
		panelFindSuspect.add(visualizeButton);
		
		this.setContentPane(panelFindSuspect);
		
		ButtonListener listener = new ButtonListener();		
		find.addActionListener(listener);
		visualizeButton.addActionListener(listener);
		
		FieldFocus fListener = new FieldFocus();
		
		suspectsName.addFocusListener(fListener);
		find.addFocusListener(fListener);
												
		this.setVisible(true);
		this.setSize(300, 150);
		this.setTitle("Find Suspect");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}
	
	class ButtonListener implements ActionListener {
		
		
		public void actionPerformed(ActionEvent e) {
					
			if(e.getSource() == find) {
				
				String suspectName = suspectsName.getText();
							
				if(record.existsTheSuspect(suspectName)) {
					new GUISuspectPage(record, suspectName);
					dispose();
				}					
				else
					JOptionPane.showMessageDialog(panelFindSuspect, "Suspect " + suspectName + " not found");
			}
			else if(e.getSource() == visualizeButton) {
				new GraphNetwork(record);
				dispose();
			}
		}
	}
	
	class FieldFocus implements FocusListener { 
		
		public void focusLost(FocusEvent e) {
			
			if( suspectsName.getText().isEmpty() )
				suspectsName.setText("Please enter suspect name");
			}
		
		public void focusGained(FocusEvent e) {
			
			if(suspectsName.getText().equals("Please enter suspect name"))
				suspectsName.setText("");
		}
		public void windowGainedFocus(WindowEvent e) {
	        find.setFocusCycleRoot(true);
	    }
	}
}
