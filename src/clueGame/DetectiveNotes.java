package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotes extends JDialog {
	
	public DetectiveNotes() {
		Board board = Board.getInstance();

		setSize(600,600);
		setLayout(new GridLayout(3,2));
		JPanel people = new JPanel();
		people = peoplePanel(board.getPlayers());
		add(people);
		JPanel personGuess = new JPanel();
		personGuess = createPersonCombo(board.getPlayers());
		add(personGuess);
		JPanel room = new JPanel();
		room = roomPanel(board.getLegend());
		add(room);
		JPanel roomGuess = new JPanel();
		roomGuess = createRoomCombo(board.getLegend());
		add(roomGuess);
		JPanel weapons = new JPanel();
		weapons = weaponPanel(board.getWeapons());
		add(weapons);
		JPanel weaponGuess = new JPanel();
		weaponGuess = createWeaponCombo(board.getWeapons());
		add(weaponGuess);

		
		
	}
	
	public JPanel peoplePanel(Player[] players) {
		JPanel panel = new JPanel();
		ArrayList<JCheckBoxMenuItem> buttonList = new ArrayList<JCheckBoxMenuItem>();
		for(int i=0; i < players.length; i++) {
			buttonList.add( new JCheckBoxMenuItem(players[i].getPlayerName()));
		}
		
		for(JCheckBoxMenuItem b: buttonList) {
			panel.add(b);
		}
		panel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		
		
		return panel;
	}
	
	public JPanel roomPanel(Map<Character, String> legend) {
		JPanel panel = new JPanel();
		ArrayList<JCheckBoxMenuItem> buttonList = new ArrayList<JCheckBoxMenuItem>();
		for(Iterator entries = legend.entrySet().iterator(); entries.hasNext(); ) {
			Entry thisEntry = (Entry) entries.next();
			Character roomChar = (Character) thisEntry.getKey();
			if(roomChar == 'H' || roomChar == 'X') {
				continue;
			}
			String roomName = (String) thisEntry.getValue();
			buttonList.add(new JCheckBoxMenuItem(roomName));
		}
		
		for(JCheckBoxMenuItem b: buttonList) {
			panel.add(b);
		}
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		
		
		return panel;
	}
	
	public JPanel weaponPanel(ArrayList<String> weapons) {
		JPanel panel = new JPanel();
		ArrayList<JCheckBoxMenuItem> buttonList = new ArrayList<JCheckBoxMenuItem>();
		for(String s: weapons) {
			buttonList.add( new JCheckBoxMenuItem(s));
		}
		
		for(JCheckBoxMenuItem b: buttonList) {
			panel.add(b);
		}
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		
		
		return panel;
	}
	
	public JPanel createPersonCombo(Player[] players) {
		JPanel panel = new JPanel();
		JComboBox<String> box = new JComboBox<String>();
		for(int i=0; i < players.length; i++) {
			box.addItem(players[i].getPlayerName());
		}
		
		panel.add(box);
	
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Person Guess"));
		
		
		return panel;
	}
	
	public JPanel createRoomCombo(Map<Character, String> legend) {
		JPanel panel = new JPanel();
		
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
		
		panel.add(box);
		
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
		
		return panel;
	}
	
	public JPanel createWeaponCombo(ArrayList<String> weapons) {
		JPanel panel = new JPanel();
		JComboBox<String> box = new JComboBox<String>();
		for(String s: weapons) {
			box.addItem(s);
		}
		
		panel.add(box);
		
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
		
		
		return panel;
	}
	
	public static void main(String[] args){
		DetectiveNotes notes = new DetectiveNotes();
		notes.setVisible(true);
	}
}
