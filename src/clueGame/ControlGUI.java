package clueGame;


import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ControlGUI extends JPanel{
	private JTextField turn, roll, guess, response;
	
	public ControlGUI() {
		setLayout(new GridLayout(2,1));
		JPanel panel = createTurnPanel();
		add(panel);
		JPanel masterPanel = new JPanel();
		JPanel panel1 = createRollPanel();
		JPanel panel2 = createGuessPanel();
		JPanel panel3 = createResultPanel();
		
		masterPanel.add(panel1);
		masterPanel.add(panel2);
		masterPanel.add(panel3);
		add(masterPanel);

	}
	
	private JPanel createTurnPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		
		JPanel whosePanel = new JPanel();
		whosePanel.setLayout(new GridLayout(2,1));
		JLabel nameLabel = new JLabel("Whose turn?", SwingConstants.CENTER);
		turn = new JTextField(15);
		turn.setEditable(false);
		whosePanel.add(nameLabel);
		whosePanel.add(turn);
		
		
		JPanel buttPanel = new JPanel();
		buttPanel.setLayout(new GridLayout(1,2));
		JButton nextPlayer = new JButton("Next player");
		JButton accusationButton = new JButton("Make an accusation");
		
		buttPanel.add(nextPlayer);
		buttPanel.add(accusationButton);
		
		panel.add(whosePanel);
		panel.add(buttPanel);
		
		return panel;
	}
	
	private JPanel createRollPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		JLabel nameLabel = new JLabel("Roll");
		roll = new JTextField(5);
		roll.setEditable(false);
		panel.add(nameLabel);
		panel.add(roll);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Die"));
		return panel;
	}
	
	private JPanel createGuessPanel() {	
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,2));
		JLabel nameLabel = new JLabel("Guess");
		guess = new JTextField(20);
		guess.setEditable(false);
		panel.add(nameLabel);
		panel.add(guess);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		return panel;
	}
	
	private JPanel createResultPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,3));
		JLabel nameLabel = new JLabel("Response");
		response = new JTextField(20);
		response.setEditable(false);
		panel.add(nameLabel);
		panel.add(response);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		return panel;
		
	}  
	
	private JPanel createButtonPanel() {
		JPanel panel = new JPanel();
		JButton nextPlayer = new JButton("Next player");
		JButton accusationButton = new JButton("Make an accusation");
		panel.add(nextPlayer);
		panel.add(accusationButton);
		return panel;
	}
	
	public static void main(String[] args){
		// Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue GUI Control");
		frame.setSize(700, 180);	
		// Create the JPanel and add it to the JFrame
		ControlGUI gui = new ControlGUI();
		frame.add(gui, BorderLayout.CENTER);
		// Now let's view it
		frame.setVisible(true);
	}
	
}