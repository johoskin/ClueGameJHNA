package clueGame;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.ControlGUI.TurnListener;

public class GuessGUI extends JDialog {
	private JTextField room, person, weapon;
	private String roomG, personG, weaponG;
	private JComboBox roomCombo, personCombo, weaponCombo;
	private JTextField yourRoom;
	private JButton submit, cancel;
	private Board board;
	private boolean inRoom = false;
	private boolean submitted = false;
	private ControlGUI control;
	
	public boolean isSubmitted() {
		return submitted;
	}


	public GuessGUI(boolean inRoom, Player p) {
		setTitle("Make a Guess");
		//setSize(700, 180);
		board = Board.getInstance();
		control = ControlGUI.getInstance();
		
		
		setSize(300,300);
		setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
		setLayout(new GridLayout(4,1));
		if(inRoom) {
			this.inRoom = true;
			JPanel urRoom = createYourRoom(p);
			add(urRoom);
		}
		else {
			JPanel room = createRoom();
			add(room);
		}
		JPanel  person = createPerson();
		add(person);
		JPanel weapon = createWeapons();
		add(weapon);
		
		JPanel buttons = createButtonPanel();
		add(buttons);
		
		
		
	}
	
	private JPanel createButtonPanel() {
		JPanel buttPanel = new JPanel();
		buttPanel.setLayout(new GridLayout(1,2));
		submit = new JButton("Submit");
		submit.addActionListener(new SubmitListener());
		cancel = new JButton("Cancel");
		cancel.addActionListener(new SubmitListener());
		buttPanel.add(submit);
		buttPanel.add(cancel);
		
		return buttPanel;
	}
	
	private JPanel createYourRoom(Player p) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		room = new JTextField(15);
		room.setText("Your Room");
		room.setEditable(false);
		panel.add(room);
		yourRoom = new JTextField(15);
		
		yourRoom.setText(board.getLegend().get(board.getCurrentLocation(p.getRow(),p.getColumn()).getInitial()));
		yourRoom.setEditable(false);
		panel.add(yourRoom);
		return panel;
	}

	public JPanel createPerson() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		person = new JTextField(15);
		person.setText("Person");
		person.setEditable(false);
		panel.add(person);
		personCombo = createPersonCombo(board.getPlayers());
		panel.add(personCombo);
		return panel;
	}
	
	public JComboBox createPersonCombo(Player[] players) {
		//JComboBox combo = new JComboBox();
		JComboBox<String> box = new JComboBox<String>();
		for(int i=0; i < players.length; i++) {
			box.addItem(players[i].getPlayerName());
		}
		return box;
	}
	
	public JPanel createRoom() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		room = new JTextField(15);
		room.setText("Room");
		room.setEditable(false);
		panel.add(room);
		roomCombo = createRoomCombo(board.getLegend());
		panel.add(roomCombo);
		return panel;
	}
	
	public JComboBox createRoomCombo(Map<Character, String> legend) {
		//JPanel panel = new JPanel();
		
		JComboBox<String> box = new JComboBox<String>();
		for(Iterator entries = legend.entrySet().iterator(); entries.hasNext(); ) {
			Entry thisEntry = (Entry) entries.next();
			Character roomChar = (Character) thisEntry.getKey();
			if(roomChar == 'H' || roomChar == 'X') {
				continue;
			}
			String roomName = (String) thisEntry.getValue();
			box.addItem(roomName);
		}
		
		//panel.add(box);
		
		return box;
	}
	
	public JPanel createWeapons() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		weapon = new JTextField(15);
		weapon.setText("Weapon");
		weapon.setEditable(false);
		panel.add(weapon);
		weaponCombo = createWeaponCombo(board.getWeapons());
		panel.add(weaponCombo);
		return panel;
	}
	
	public JComboBox createWeaponCombo(ArrayList<String> weapons) {
		//JPanel panel = new JPanel();
		JComboBox<String> box = new JComboBox<String>();
		for(String s: weapons) {
			box.addItem(s);
		}
		
		//panel.add(box);

		return box;
	}
	
	public class SubmitListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String personName;
			String weaponName;
			if(e.getSource() == submit) {
				submitted = true;
			} else if (e.getSource() == cancel){
				submitted = false;
			}
			//System.out.println(submitted);
			if(submitted) {
				if(inRoom) {
					board.getPlayers()[0].setSuggRoom(yourRoom.getText());
					board.getPlayers()[0].setSuggPerson((String)personCombo.getSelectedItem());
					board.getPlayers()[0].setSuggWeapon((String)weaponCombo.getSelectedItem());
					board.setSuggCard(board.handleSuggestion(board.getPlayers()[0].getMySolution(), board.getPlayers()[0], board.getPlayers()));
					if(board.getSuggCard() != null) {
						control.setResponse(board.getSuggCard().getCardName());
					}
					if(!(board.getPlayers()[board.getPlayerIndex()].getSuggPerson() == null && board.getPlayers()[board.getPlayerIndex()].getSuggRoom() == null && board.getPlayers()[board.getPlayerIndex()].getSuggWeapon() == null)) {
						control.setGuess(board.getPlayers()[board.getPlayerIndex()].getSuggPerson() + " in the " + board.getPlayers()[board.getPlayerIndex()].getSuggRoom() + " with the " + board.getPlayers()[board.getPlayerIndex()].getSuggWeapon());
					}
					board.getTargets().clear();
					board.repaint();
					board.nextPlayer();
					dispose();
				} else {
					board.getPlayers()[0].setSuggRoom((String)roomCombo.getSelectedItem());
					board.getPlayers()[0].setSuggPerson((String)personCombo.getSelectedItem());
					board.getPlayers()[0].setSuggWeapon((String)weaponCombo.getSelectedItem());
					//System.out.println((String)roomCombo.getSelectedItem());
					board.getPlayers()[0].makeAccusation();
					if (board.checkAccusation(board.getPlayers()[0].getMySolution())) {
						JOptionPane.showMessageDialog(null, "Congrats, you have won the game!");
						System.exit(0);
						return;
					}
					else {
						JOptionPane.showMessageDialog(null, "No, sorry!");
						board.getTargets().clear();
						board.repaint();
						board.nextPlayer();
						dispose();
						
						
					}
				}
				

			} else {
				dispose();
			}
			
		}
		
	}
	
	public static void main(String[] args){
		//JDialog dialog = new JDialog();
		Board controlBoard = Board.getInstance();
		controlBoard.setConfigFiles("ASTS_ClueLayout.csv", "ASTS_ClueLegend.txt");
		controlBoard.setPlayerConfig("JHASv2_CluePlayer.txt");
		controlBoard.setCardConfig("JHASv2_ClueCards.txt");
		controlBoard.initialize();
		//dialog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Player p = new Player();
		p.setRow(20);
		p.setColumn(15);
		// Create the JPanel and add it to the JFrame
		GuessGUI gui = new GuessGUI(true, p);
		//dialog.add(gui, BorderLayout.CENTER);
		// Now let's view it
		gui.setVisible(true);
		
		//JPanel person = gui.createPerson();
		//person.setVisible(true);
	}
}
