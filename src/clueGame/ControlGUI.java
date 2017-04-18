package clueGame;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ControlGUI extends JPanel{
	private JTextField turn, roll, guess, response;
	private Board board;
	private int numRoll;
	
	// variable used for singleton pattern
	private static ControlGUI theInstance = new ControlGUI();
	// ctor is private to ensure only one can be created
	private ControlGUI() {}
	// this method returns the only Board
	public static ControlGUI getInstance() {
		return theInstance;
	}
	
	public void setTurn(String s) {
		turn.setText(s);
	}

	public void setRoll(String i) {
		roll.setText(i);
	}

	public void setGuess(String s) {
		guess.setText(s);
	}

	public void setResponse(String s) {
		response.setText(s);
	}

	public void initialize() {
		board = board.getInstance();
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
		nextPlayer.addActionListener(new TurnListener());
		JButton accusationButton = new JButton("Make an accusation");
		accusationButton.addActionListener(new AccListener());
		
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
		roll = new JTextField(3);
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
		guess = new JTextField(30);
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
	
	
	class TurnListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			//System.out.println(board.getPlayerIndex());
			//System.out.println(board.getTurnOver());
			
			
			
			if(board.getPlayers()[board.getPlayerIndex()].isHuman()) {
				if(board.isTurnOver() == false) {
					JOptionPane.showMessageDialog(null, "You need to finish your turn");
					return;
				}
				else {
					setTurn(board.getPlayers()[board.getPlayerIndex()].getPlayerName());
					numRoll = board.rollDie();
					setRoll(String.valueOf(numRoll));
					board.handleTurn(board.getPlayers()[board.getPlayerIndex()], numRoll);
					if(board.getSuggCard() != null) {
						setResponse(board.getSuggCard().getCardName());
					}
					if(!(board.getPlayers()[board.getPlayerIndex()].getSuggPerson() == null && board.getPlayers()[board.getPlayerIndex()].getSuggRoom() == null && board.getPlayers()[board.getPlayerIndex()].getSuggWeapon() == null)) {
						setGuess(board.getPlayers()[board.getPlayerIndex()].getSuggPerson() + " in the " + board.getPlayers()[board.getPlayerIndex()].getSuggRoom() + " with the " + board.getPlayers()[board.getPlayerIndex()].getSuggWeapon());
					}
					//System.out.println(board.isTurnOver());
				}
			}
			else {
				setTurn(board.getPlayers()[board.getPlayerIndex()].getPlayerName());
				numRoll = board.rollDie();
				setRoll(String.valueOf(numRoll));
				board.handleTurn(board.getPlayers()[board.getPlayerIndex()], numRoll);
				board.nextPlayer();
			}
		}
	}
	
	class AccListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(board.getPlayers()[board.getPlayerIndex()].isHuman()) {
				if(board.isTurnOver() == true) {
					JOptionPane.showMessageDialog(null, "You can only make an accusation at the start of your turn...");
					return;
				}
				else {
					GuessGUI guess = new GuessGUI(false, board.getPlayers()[0]);
					guess.setVisible(true);
					setGuess(board.getPlayers()[0].getSuggPerson() + " in the " + board.getPlayers()[0].getSuggRoom() + " with the " + board.getPlayers()[0].getSuggWeapon());
					if(board.getSuggCard() !=null) {
						setResponse(board.getSuggCard().getCardName());
					}
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "You can only make an accusation at the start of your turn...");
				return;
			}
			
		}
		
		

		
	}
	
	public static void main(String[] args){
		// Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		Board controlBoard = Board.getInstance();
		controlBoard.setConfigFiles("ASTS_ClueLayout.csv", "ASTS_ClueLegend.txt");
		controlBoard.setPlayerConfig("JHASv2_CluePlayer.txt");
		controlBoard.setCardConfig("JHASv2_ClueCards.txt");
		controlBoard.initialize();
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