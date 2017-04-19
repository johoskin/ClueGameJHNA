package clueGame;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.util.Random;

public class Board extends JPanel {
	//git log | awk '{printf "%s\r\n", $0}' > log.txt
	private int numRows, numColumns;
	public final int MAX_BOARD_SIZE = 50;
	public final int MAX_PLAYERS = 6;
	private Map<Character, String> legend;
	private String boardConfigFile, roomConfigFile, playerConfigFile, cardConfigFile;
	private Map<BoardCell, Set<BoardCell>> adjMtx; //adjacency matrix
	private Set<BoardCell> visited; //visited cells
	private Set<BoardCell> targets; //targets for the player to move to
	private BoardCell[][] grid = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE]; //playing board
	private Player[] players = new Player[MAX_PLAYERS];
	private static ArrayList<String> weapons = new ArrayList<String>();
	private static ArrayList<Card> cards = new ArrayList<Card>();
	public Solution solution = new Solution();
	private BoardCell targetCell;
	private int playerIndex = 0;
	private boolean isTurnOver = true;
	private Card suggCard = new Card();
	private ControlGUI control;


	public void setSuggCard(Card suggCard) {
		this.suggCard = suggCard;
	}

	public Card getSuggCard() {
		return suggCard;
	}

	public boolean isTurnOver() {
		return isTurnOver;
	}

	public void setTurnOver(boolean isTurnOver) {
		this.isTurnOver = isTurnOver;
	}
	
	//////////////////////
	public boolean getTurnOver() {
		return isTurnOver;
	}

	public BoardCell getCurrentLocation(int r, int c){
		return grid[r][c];
	}


	public int getPlayerIndex() {
		return playerIndex;
	}
	
	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// ctor is private to ensure only one can be created
	private Board() {}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	
	public void handleTurn(Player p, int roll) {
		
		calcTargets(p.getRow(), p.getColumn(), roll);
		if(p.isHuman()) {
			isTurnOver = false;
			for(BoardCell c: targets) {
				c.setTarget(true);
			}
			repaint();
		}
		else if (!p.isHuman() && isTurnOver == true){
			p.makeMove(targets);
			if (p.isCompAcc()) {
				if(checkAccusation(p.getMySolution())) {
					JOptionPane.showMessageDialog(null, p.getPlayerName() + "has won the game! The correct guess was " + p.getPlayerName() + "has accused " + p.getSuggPerson() + " in the " + p.getSuggRoom() + " with the " + p.getSuggWeapon() + ".");
					return;
				} else {
					JOptionPane.showMessageDialog(null, p.getPlayerName() + "has accused " + p.getSuggPerson() + " in the " + p.getSuggRoom() + " with the " + p.getSuggWeapon() + ", but this is incorrect!");
					return;
				}
				
			}
			control.setGuess(p.getSuggPerson() + " in the " + p.getSuggRoom() + " with the " + p.getSuggWeapon());
			if(p.isInRoom()) {
			suggCard = handleSuggestion(p.getMySolution(), p, players);
			System.out.println(suggCard.getCardName());
			}
			//System.out.println(suggCard.getCardName());
			if(suggCard != null) {
			control.setResponse(suggCard.getCardName());
			}
			else {
				control.setResponse(" ");
			}
			p.seenCards.add(suggCard);
			p.unSeenCards.remove(suggCard);
			if(suggCard == null) {
				p.compReturn();
			}
			repaint();
			
		}
	}
	
	public int rollDie() {
		Random randomGen = new Random();
		int roll = (randomGen.nextInt(6)+1);
		return roll;
	}
	
	
	public void nextPlayer() {
		playerIndex = playerIndex + 1;
		playerIndex = playerIndex%=(MAX_PLAYERS);
	}
	
	public Player[] getPlayers() {
			return players;
		}
	
	public ArrayList<Card> getCards() {
		return cards;
	}
	
	private void calcAdjacencies() {
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numColumns; j++) {
				BoardCell cell = grid[i][j];
				HashSet <BoardCell> temp = new HashSet<BoardCell>();
				int left = j - 1;
				int right = j + 1;
				int up = i - 1;
				int down = i + 1;
				
				if(cell.getInitial() == 'W') {//test if it is a walkway 
					//check all positions
					if(left >= 0 && (grid[i][left].getInitial() == 'W' || grid[i][left].getDoorDirection() == DoorDirection.RIGHT)) {
						temp.add(grid[i][left]);
					}
					if(right < numColumns && (grid[i][right].getInitial() == 'W' || grid[i][right].getDoorDirection() == DoorDirection.LEFT)) {
						temp.add(grid[i][right]);
					}
					if(up >= 0 && (grid[up][j].getInitial() == 'W' || grid[up][j].getDoorDirection() == DoorDirection.DOWN)) {
						temp.add(grid[up][j]);
					}
					if(down < numRows && (grid[down][j].getInitial() == 'W' || grid[down][j].getDoorDirection() == DoorDirection.UP)) {
						temp.add(grid[down][j]);
					}
				} else if(cell.isDoorway()) {//test if is doorway
					switch(cell.getDoorDirection()) {
					case DOWN:
						temp.add(grid[down][j]);
						break;
					case LEFT:
						temp.add(grid[i][left]);
						break;
					case RIGHT:
						temp.add(grid[i][right]);
						break;
					case UP:
						temp.add(grid[up][j]);
						break;
					default:
						break;
					}
				}
				adjMtx.put(cell, temp);
			}
		}
	}
	
	
	public void calcTargets(int row, int col, int pathLength){
		visited.clear();
		targets.clear();
		
		visited.add(grid[row][col]);
		findAllTargets(grid[row][col], pathLength);
	}
	
	public void findAllTargets(BoardCell startCell, int pathLength) {
		Set<BoardCell> tempSet = new HashSet<BoardCell>();
		tempSet = adjMtx.get(startCell);
		for(BoardCell s: tempSet) {
			if(s.isDoorway() && !visited.contains(s)){
				targets.add(s);
			}
			if (visited.contains(s)) {
				continue;
			}
			else {
				visited.add(s);
				if(pathLength == 1) targets.add(s);
				else {
					findAllTargets(s,pathLength-1);
				}
				visited.remove(s);
			}
			s.toString();
		}
		
	}
	
	public BoardCell getCellAt(int r, int c) {
		return grid[r][c];
	}

	public Set<BoardCell> getAdjList(int r, int c) { //return adjacencies to the cell
		return adjMtx.get(grid[r][c]);
	}

	public Set<BoardCell> getTargets() { //return the targets for player to move to
		Set<BoardCell> temp = new HashSet<BoardCell>();
		for(BoardCell cell:targets) {
			temp.add(cell);
		}
		targets.clear();
		return temp;
	}

	public void setConfigFiles(String boardCon, String roomCon) {
		boardConfigFile = boardCon;
		roomConfigFile = roomCon;
	}
	
	public void setPlayerConfig(String playerCon) {
		playerConfigFile = playerCon;
	}
	
	public void setCardConfig(String cardCon) {
		cardConfigFile = cardCon;
	}

	public void initialize() {
		control = ControlGUI.getInstance();
		addMouseListener(new ChooseListener());
		FileReader roomin, boardin, playerin, cardin;
		Scanner Roomin1 = null, Boardin1 = null, Playerin1 = null, Cardin1 = null;
		adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		legend = new HashMap<Character, String>();
		
		try{
			roomin = new FileReader(roomConfigFile);
			boardin = new FileReader(boardConfigFile);
			if(playerConfigFile != null){
				playerin = new FileReader(playerConfigFile);
				Playerin1 = new Scanner(playerin);
			}
			if(cardConfigFile != null){
				cardin = new FileReader(cardConfigFile);
				Cardin1 = new Scanner(cardin);
			}
			Roomin1 = new Scanner(roomin);
			Boardin1 = new Scanner(boardin);
		} catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		int rowCount = 0, prevcount = 0, count = 0;
		
		while(Boardin1.hasNextLine()) {
			String[] str = Boardin1.nextLine().split(",");
			count = str.length;
			//test if there is a mismatch in columns
			if(prevcount != count && prevcount != 0) {
				try {
					throw new BadConfigFormatException("The number of Columns is not consistent");
				} catch (BadConfigFormatException e) {
					System.out.println(e);
				}
			}
			//set cell
			for(int i = 0; i < count; i++) {
				if(str[i].length() == 1) {
					grid[rowCount][i] = new BoardCell(rowCount, i, str[i].toCharArray()[0]);
				} else {
					grid[rowCount][i] = new BoardCell(rowCount, i, str[i].toCharArray()[0], str[i].toCharArray()[1]);
				}
			}
			prevcount = count;
			rowCount++;
		}
		numRows = rowCount;
		numColumns = count;
		
		while(Roomin1.hasNextLine()) {
			String[] str = Roomin1.nextLine().split(",");
			for(int i = 0; i < str.length; i++) {
				str[i] = str[i].trim();
			}
			legend.put(str[0].toCharArray()[0], str[1]);
		}
		
		calcAdjacencies();
		int j = 0;
		if(playerConfigFile != null){
			while(Playerin1.hasNextLine()) {
				String[] str = Playerin1.nextLine().split(",");
				for(int i = 0; i < str.length; i++) {
					str[i] = str[i].trim();
				}
				Integer row = new Integer(Integer.parseInt(str[2]));
				Integer col = new Integer(Integer.parseInt(str[3]));
				Color color = stringToColor(str[1]);
				if(j==0) players[j] = new HumanPlayer(str[0],row,col,color, true);
				else players[j] = new ComputerPlayer(str[0], row, col, color, false);
				j++;
			}
			
		}
		
		int k = 0;
		if(cardConfigFile != null){
			while(Cardin1.hasNextLine()) {
				String[] str = Cardin1.nextLine().split(",");
				for(int i = 0; i < str.length; i++) {
					str[i] = str[i].trim();
				}
				CardType ret_val;
				ret_val = CardType.valueOf(str[0]);
				Card tempCard = new Card(str[1], ret_val);
				if(tempCard.getCardType().equals(CardType.WEAPON)) {
					weapons.add(tempCard.getCardName());
				}
				cards.add(tempCard);
				k++;
			}
		}
		if(cardConfigFile != null) {
			dealCards();
		}
		
		
		
	}
	
	public ArrayList<String> getWeapons() {
		return weapons;
	}
	public static Color stringToColor(final String value){
		if (value == null){
			return Color.black;
		}
		try{
			
			return Color.decode(value);
			
		}catch(NumberFormatException nfe){
			try{
				final Field f = Color.class.getField(value);
				
				return (Color) f.get(null);
			}catch (Exception ce){
				return Color.black;
			}
		}
		
	}
	
	public Map<Character, String> getLegend() {
		return legend;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}
	
	
	public void dealCards() {
		Random randomGen = new Random();
		int randomInt = randomGen.nextInt(cards.size());
		do {
			randomInt = randomGen.nextInt(cards.size());
			if(cards.get(randomInt).getCardType() == CardType.PERSON) {
				solution.person = cards.get(randomInt).getCardName();
				cards.get(randomInt).setDealt(true);
			}
		}while (cards.get(randomInt).getCardType() != CardType.PERSON);

		do {
			randomInt = randomGen.nextInt(cards.size());
			if(cards.get(randomInt).getCardType() == CardType.ROOM) {
				solution.room = cards.get(randomInt).getCardName();
				cards.get(randomInt).setDealt(true);
			}
		}while (cards.get(randomInt).getCardType() != CardType.ROOM);

		do {
			randomInt = randomGen.nextInt(cards.size());
			if(cards.get(randomInt).getCardType() == CardType.WEAPON) {
				solution.weapon = cards.get(randomInt).getCardName();
				cards.get(randomInt).setDealt(true);
			}
		}while (cards.get(randomInt).getCardType() != CardType.WEAPON);
		
		System.out.println("SOLUTION: " + solution.person + " " + solution.weapon + " " + solution.room);
		
		
		do {
			for(int i = 0; i < players.length; i++) {
				randomInt = randomGen.nextInt(cards.size());
				while(cards.get(randomInt).isDealt()){
					randomInt = randomGen.nextInt(cards.size());
				}

					players[i].getCards().add(cards.get(randomInt));
					players[i].getSeenCards().add(cards.get(randomInt));
					cards.get(randomInt).setDealt(true);
					
				if(isDeckOpen() == false){
					break;
				}
				
			}
			
		} while (isDeckOpen());
		
		for(Player p: players) {
			for(Card c: cards) {
				if(!p.getSeenCards().contains(c)) {
					p.getUnSeenCards().add(c);
				}
			}
		}
		
	}
	
	
	//Handle Suggestion iterates through the each player's cards, in order, and returns a card 
	//within the suggestion
	/*public Card handleSuggestion(Solution sol, Player accuser, Player[] plrs) {
		
		Card suggCard = new Card();
		
		for(Player s : plrs) {
			
			
			for(int j = 0; j < s.getCards().size(); j++){

				if(s.getCards().get(j).getCardType() == CardType.PERSON){
					if(s.getCards().get(j).getCardName().equals(sol.person)){
						if(s.equals(accuser)){
							suggCard = null;
						}
						suggCard = s.getCards().get(j);
						return suggCard;
					}
				}

				if(s.getCards().get(j).getCardType() == CardType.WEAPON){
					if(s.getCards().get(j).getCardName().equals(sol.weapon)){
						if(s.equals(accuser)){
							suggCard = null;
						}
						suggCard = s.getCards().get(j);
						return suggCard;
					}
				}

				if(s.getCards().get(j).getCardType() == CardType.ROOM){
					if(s.getCards().get(j).getCardName().equals(sol.room)){
						if(s.equals(accuser)){
							suggCard = null;
						}
						suggCard = s.getCards().get(j);
						return suggCard;
					}
				}
			}
		}
		return suggCard;
	}*/
	
	public Card handleSuggestion(Solution sol, Player accuser, Player[] plrs) {
		Card tempCard = new Card();
		
		for(Player s : plrs) {
			//System.out.println(s.getCards().get(0) + " " + s.getCards().get(1));
			tempCard = s.disproveSuggestion(sol);
			//System.out.println(tempCard.getCardName());
			if(tempCard != null) {
				if(s.equals(accuser)) {
					tempCard = null;
					//return tempCard;
				}
				else {
					return tempCard;
				}
			}
		}

		return tempCard;
	}
	
	public boolean checkAccusation(Solution accusation) {
		if(solution.person.equals(accusation.person) && solution.room.equals(accusation.room) && solution.weapon.equals(accusation.weapon)) {
			return true;
		}
		return false;
	}
	
	public boolean isDeckOpen() {
		for(int i = 0; i < cards.size(); i++) {
			if(cards.get(i).isDealt() == false) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for(int i=0; i < numRows; i++) {
			for(int j = 0; j < numColumns; j++) {
				grid[i][j].DrawBoardCell(g);
			}
		}
		repaint();
		//
		//Draw Players
		for(Player p: players) {
			p.drawPlayer(g);
		}
		
		repaint();
		
	}
	
	public class ChooseListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			//if(e.getSource() == )
			BoardCell cell = new BoardCell(e.getY()/27,e.getX()/27);
			for(int i= 0; i < numRows; i++) {
				for(int j = 0; j < numColumns; j++) {
					
					//if (playerIndex != 0){
					//	JOptionPane.showMessageDialog(null, "Not your turn!");
					//	return;
					//}
					if(grid[i][j].getRow() == (e.getY()/27) && grid[i][j].getColumn() == (e.getX()/27) && isTurnOver == false){
						cell = grid[i][j];
						if(!cell.isTarget()) {
							JOptionPane.showMessageDialog(null, "Not a valid target");
							return;
						}
						else {
							for(BoardCell c: targets) {
								c.setTarget(false);
							}
							targets.clear();
							targets.add(cell);
							
							getPlayers()[0].makeMove(targets);
							
							
							targets.clear();
							repaint();
							
							if(getCurrentLocation(players[0].getRow(), players[0].getColumn()).isRoom()) {
								players[0].setInRoom(true);
								GuessGUI guess = new GuessGUI(players[0]);
								guess.setVisible(true);
								if(suggCard == null) {
									System.out.println("null");
								}
								else {
								System.out.println(suggCard.getCardName());
								}
/*								if (checkAccusation(players[0].getMySolution())) {
									JOptionPane.showMessageDialog(null, "Congrats, you have won the game!");
									System.exit(0);
									return;
								}
								setSuggCard(handleSuggestion(players[0].getMySolution(), players[0], players));*/
							}
							else {
								players[0].setInRoom(false);
							}
							
							isTurnOver = true;
							nextPlayer();
							break;
						}
						
					}
				}
			}
		}
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}
		@Override
		public void mousePressed(MouseEvent e) {
			
		}
		@Override
		public void mouseReleased(MouseEvent e) {
		}
	
	}

}
