package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
	private int numRows, numColumns;
	public final int MAX_BOARD_SIZE = 50;
	private Map<Character, String> legend;
	private String boardConfigFile, roomConfigFile;
	private Map<BoardCell, Set<BoardCell>> adjMtx; //adjacency matrix
	private Set<BoardCell> visited; //visited cells
	private Set<BoardCell> targets; //targets for the player to move to
	private BoardCell[][] grid = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE]; //playing board
	private Player[] players = new Player[6];

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

	public void initialize() {
		FileReader roomin, boardin;
		Scanner Roomin1 = null, Boardin1 = null;
		adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		legend = new HashMap<Character, String>();
		
		try{
			roomin = new FileReader(roomConfigFile);
			boardin = new FileReader(boardConfigFile);
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
	
	public void selectAnswer() {
		
	}
	
	public Card handleSuggestion() {
		Card card = new Card();
		return card;
	}
	
	public boolean checkAccusation(Solution accusation) {
		
		return false;
	}
}
