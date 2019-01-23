package Plant;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class GApp implements ActionListener{
	private static GApp instance=null;
	private GApp(){
		GUI();
	}
	public static GApp getinstance() {
		if(instance==null) instance=new GApp();
		return instance;
	}
	
	private BGGenerationSet plant=null;
	private JFrame frame=null;
	
	private JPanel menuPanel=null;					// menu bar panel
	private JButton startBtn=null;					// start Button
	private JButton stopBtn=null;					// stop Button
	private JComboBox<Integer> rulemenu=null;		// Rule ComboBox
	private JTextField generations=null;			// Generation Text Field
	private JLabel txt1;			// used to show Rule text beside Rule Java ComboBox.
	private JLabel txt2;			// used to show Generations text beside Rule Java TextField.
	
	
	private JPanel drawPanel=null;					// Canvas Panel
	
	
	private JPanel dialogPanel=null;				// dialog panel shows dialogs at the bottom of the app
	private JLabel dialog1;			// status of the plant after start Button or stop button is pressed.
	private JLabel dialog2;			// status of the rule applied.
	private JLabel dialog3;			// status of the generations applied.
	
	
	private void GUI() {
    	frame = new JFrame();
		frame.setTitle("BGApp");
		frame.setResizable(true);
		frame.setSize(new Dimension(1000,1000));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);
		
		
		frame.add(getMenuPanel(),BorderLayout.NORTH);
		
		frame.add(getCanvas(), BorderLayout.CENTER);
		

		dialogPanel=new JPanel();
		dialogPanel.setLayout(new FlowLayout());				// setting FlowLayout for the dialog Panel
		dialogPanel.setBackground(Color.GRAY);
		dialog1=new JLabel("Enter the , No of Generations and click on start");
		dialog2=new JLabel();
		dialog3=new JLabel();
		dialog1.setForeground(Color.BLUE);
		dialog2.setForeground(Color.BLUE);
		dialog3.setForeground(Color.BLUE);
		dialogPanel.add(dialog1);
		dialogPanel.add(dialog2);
		dialogPanel.add(dialog3);
		frame.add(dialogPanel, BorderLayout.SOUTH);				// adding the dialog panel to south of the Frame
	}
	
	private JPanel getMenuPanel() {
		menuPanel=new JPanel();
		menuPanel.setLayout(new FlowLayout());
		
		
		startBtn=new JButton("Start");
		stopBtn=new JButton("Stop");
		startBtn.addActionListener(this);		// adding actionlistener to the start button and stop button
		stopBtn.addActionListener(this);
		
		txt1=new JLabel("Rule:");
		
		rulemenu=new JComboBox<>();
		rulemenu.addItem(0);
		rulemenu.addItem(1);
		rulemenu.addItem(2);
		rulemenu.addItem(3);
		rulemenu.addItem(4);
		rulemenu.addItem(5);
		rulemenu.addItem(6);
		rulemenu.addActionListener(this);
		
		txt2=new JLabel("#Generations:");
		generations=new JTextField(5);
		generations.setText("");
		generations.addActionListener(this);
		
		
		menuPanel.add(startBtn);
		menuPanel.add(stopBtn);
		menuPanel.add(txt1);
		menuPanel.add(rulemenu);
		menuPanel.add(txt2);
		menuPanel.add(generations);
		
		menuPanel.setBackground(Color.GRAY);
		
		return menuPanel;
	}
	
	public JPanel getCanvas() {
		if(drawPanel==null) drawPanel=new BGPanel();		// initializing the draw panel
		return drawPanel;
	}
	
	
	
	private int rule=0;
	private int gen=0;
	private boolean paused=false;
	private boolean stopped=true;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==startBtn && rule!=0 && gen!=0) {
			if(!paused && stopped) {
				// if the start is called and we are not in the middle of a growth clear the screen
				dialog1.setText("Start button pressed. ");
				// start the growth of the plant.
				int height=((BGPanel) drawPanel).getheight();
				plant=new BGGenerationSet(gen,rule,height);
				((BGPanel) drawPanel).setPlant(plant);
				stopped=false;
			}else {
				
				System.out.println("Resuming");
				// if the start button is pressed and we are in the middle of a growth then resume growth.
				plant.thr.resume();
				plant.changerule(rule);		// allow for a change of rule	
				dialog1.setText("Resuming growth. ");
				paused=false;
			}
		}else if(e.getSource()==stopBtn) {
			if(plant!=null) {
				if(plant.thr.isAlive()) {
					System.out.println("Paused");
					// if in middle of growth and we click the stop button then suspend the growth
					dialog1.setText("Growth paused.");
					
					plant.thr.suspend();
					paused=true;
				}else {
					
					System.out.println("Stoped");
					// once the growth ends then use clear the screen and set the rule and #generations to null
					rulemenu.setSelectedIndex(0);
					generations.setText("");
					dialog1.setText("Stop button pressed.");
					dialog2.setText("");
					dialog3.setText("");
					
					((BGPanel) drawPanel).ClearScreen();
					stopped=true;
				}
			}
		}else if(e.getSource()==rulemenu) {

			System.out.println("rule is being applied");
			// feed rule to r once a rule is set
			rule=(int) rulemenu.getSelectedItem();
			dialog3.setText(" with rule "+rule+".");
		}else if(e.getSource()==generations) {
			
			System.out.println("generations is being applied");
			// feed #generations to gen once #generations are set
			String str=generations.getText();
			for(int i=0;i<str.length();i++) {
				gen=(gen*10)+(str.charAt(i)-'0');
			}
			// cap the max #generations to 10
			if(gen>11) gen=11;
			dialog2.setText("Growing "+gen+" generations,");
		}
	}
	
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	GApp.getinstance();// The UI is built, so display it;
            }
        });
    	
	}
}
