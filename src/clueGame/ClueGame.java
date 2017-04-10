package clueGame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class ClueGame extends JFrame{
	
	private DetectiveNotes dialog;

	public ClueGame() {
		// Create a JFrame with all the normal functionality
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue Game");
		setSize(570, 605);
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
	
	
	public static void main(String[] args){
		ClueGame game = new ClueGame();
		game.setVisible(true);
	}
}
