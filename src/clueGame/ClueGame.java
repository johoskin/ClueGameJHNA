package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ClueGame extends JFrame implements MouseListener{
	
	private DetectiveNotes dialog;
	private Board board;

	public ClueGame() {
		// Create a JFrame with all the normal functionality
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue Game");
		setSize(712, 800);
		// Create the JPanel and add it to the JFrame
		Board board = Board.getInstance();
		board.setConfigFiles("ASTS_ClueLayout.csv", "ASTS_ClueLegend.txt");
		board.setPlayerConfig("JHASv2_CluePlayer.txt");
		board.setCardConfig("JHASv2_ClueCards.txt");
		board.initialize();
		// Initialize will load BOTH config files 
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());

		add(board, BorderLayout.CENTER);
		JPanel cards = myCards(board.getPlayers()[0].getCards());
		add(cards, BorderLayout.EAST);
		JPanel control = new ControlGUI();
		add(control, BorderLayout.SOUTH);
		
		// Now let's view it
		setVisible(true);
	}
	

	
	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createDetectiveItem());
		menu.add(createFileExitItem());
		return menu;
	}

	private JMenuItem createFileExitItem() {
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
				
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}

	private JMenuItem createDetectiveItem() {
		JMenuItem item = new JMenuItem("Detective Notes");
		class MenuItemListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dialog = new DetectiveNotes();
				dialog.setVisible(true);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}
	
	
	private JPanel myCards(ArrayList<Card> cards) {
		JPanel masterPanel = new JPanel();
		masterPanel.setLayout(new GridLayout(5,1));
		JPanel people = new JPanel();
		people.setLayout(new GridLayout(2,2));
		people.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		JPanel rooms = new JPanel();
		rooms.setLayout(new GridLayout(2,2));
		rooms.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		JPanel weapons = new JPanel();
		weapons.setLayout(new GridLayout(2,2));
		weapons.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		
		for(Card c : cards) {
			if(c.getCardType().equals(CardType.PERSON)) {
				people.add( new JLabel(c.getCardName()));
			}
			if(c.getCardType().equals(CardType.WEAPON)) {
				weapons.add( new JLabel(c.getCardName()));
			}
			if(c.getCardType().equals(CardType.ROOM)) {
				rooms.add( new JLabel(c.getCardName()));
			}
		}
		
		masterPanel.add(people);
		masterPanel.add(rooms);
		masterPanel.add(weapons);
		
		masterPanel.setBorder(new TitledBorder (new EtchedBorder(), "My Cards"));
		return masterPanel;
	}
	
	public void playGame() {
		//TODO: START GAME
		//System.out.println(rollDie());
		//board.handleTurn(board.getPlayers()[0], rollDie());
	}
	
	
	public static void main(String[] args){
		ClueGame game = new ClueGame();
		JOptionPane.showMessageDialog(game,"You are Colonel Mustard, press Next Player to begin play...", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		game.setVisible(true);
		//game.playGame();
	}



	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
