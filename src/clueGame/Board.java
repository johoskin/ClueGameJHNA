package clueGame;

import java.awt.Color;
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
import java.util.Random;

public class Board {
	private int numRows, numColumns;
	public final int MAX_BOARD_SIZE = 50;
	private Map<Character, String> legend;
	private String boardConfigFile, roomConfigFile, playerConfigFile, cardConfigFile;
	private Map<BoardCell, Set<BoardCell>> adjMtx; //adjacency matrix
	private Set<BoardCell> visited; //visited cells
	private Set<BoardCell> targets; //targets for the player to move to
	private BoardCell[][] grid = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE]; //playing board
	private Player[] players = new Player[6];
	private static ArrayList<Card> cards = new ArrayList<Card>();
	public Solution solution;

	// variable used for singleton pattern
		private static Board theInstance = new Board();
		// ctor is private to ensure only one can be created
		private Board() {}
		// this method returns the only Board
		public static Board getInstance() {
			return theInstance;
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
				int leftPosition = j - 1;
				int rightPosition = j + 1;
				int upPosition = i - 1;
				int downPosition = i + 1;
				
				if(cell.getInitial() == 'W') {//test if it is a walkway 
					//check all positions
					if(leftPosition >= 0 && (grid[i][leftPosition].getInitial() == 'W' || grid[i][leftPosition].getDoorDirection() == DoorDirection.RIGHT)) {
						temp.add(grid[i][leftPosition]);
					}
					if(rightPosition < numColumns && (grid[i][rightPosition].getInitial() == 'W' || grid[i][rightPosition].getDoorDirection() == DoorDirection.LEFT)) {
						temp.add(grid[i][rightPosition]);
					}
					if(upPosition >= 0 && (grid[upPosition][j].getInitial() == 'W' || grid[upPosition][j].getDoorDirection() == DoorDirection.DOWN)) {
						temp.add(grid[upPosition][j]);
					}
					if(downPosition < numRows && (grid[downPosition][j].getInitial() == 'W' || grid[downPosition][j].getDoorDirection() == DoorDirection.UP)) {
						temp.add(grid[downPosition][j]);
					}
				} else if(cell.isDoorway()) {//test if is doorway
					switch(cell.getDoorDirection()) {
					case DOWN:
						temp.add(grid[downPosition][j]);
						break;
					case LEFT:
						temp.add(grid[i][leftPosition]);
						break;
					case RIGHT:
						temp.add(grid[i][rightPosition]);
						break;
					case UP:
						temp.add(grid[upPosition][j]);
						break;
					default:
						break;
					}
				}
				adjMtx.put(cell, temp);
			}
		}
	}
	
	public void calcTargets(int r, int c, int pathLength) {
		visited.add(grid[r][c]);
		HashSet<BoardCell> adj = new HashSet<BoardCell>(getAdjList(r, c));
		for(BoardCell cell:adj) {
			if(!visited.contains(cell)) {
				visited.add(cell);
				if(cell.isDoorway()) {
					switch(cell.getDoorDirection()) {
					case DOWN:
						if(visited.contains(grid[cell.getRow()+1][cell.getColumn()]))
							targets.add(cell);
						break;
					case LEFT:
						if(visited.contains(grid[cell.getRow()][cell.getColumn()-1]))
							targets.add(cell);
						break;
					case RIGHT:
						if(visited.contains(grid[cell.getRow()][cell.getColumn()+1]))
							targets.add(cell);
						break;
					case UP:
						if(visited.contains(grid[cell.getRow()-1][cell.getColumn()]))
							targets.add(cell);
						break;
					default:
						break;
					}
				}
				if(pathLength == 1) {
					targets.add(cell);
				} else {
					calcTargets(cell.getRow(), cell.getColumn(), pathLength - 1);
				}
				visited.remove(cell);
			}
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
		// TODO Auto-generated method stub
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
					// TODO Auto-generated catch block
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
				players[j] = new Player(str[0], row, col, color);
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
				cards.add(tempCard);
				k++;
			}
		}
		if(cardConfigFile != null) {
			dealCards();
		}
		
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
		// TODO Auto-generated method stub
		return legend;
	}

	public int getNumRows() {
		// TODO Auto-generated method stub
		return numRows;
	}

	public int getNumColumns() {
		// TODO Auto-generated method stub
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
		
		
		
		
		do {
			for(int i = 0; i < players.length; i++) {
				randomInt = randomGen.nextInt(cards.size());
				while(cards.get(randomInt).isDealt()){
					randomInt = randomGen.nextInt(cards.size());
				}
					//System.out.println(cards.get(randomInt).toString());
					players[i].getMyCards().add(cards.get(randomInt));
					cards.get(randomInt).setDealt(true);
					
				if(isDeckOpen() == false){
					break;
				}
				
			}
			
		} while (isDeckOpen());
		
		
	}
	
	public void selectAnswer() {
		
	}
	
	public Card handleSuggestion() {
		Card card = new Card();
		return card;
	}
	
	public boolean checkAccusation(Solution accusation) {
		
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
}
