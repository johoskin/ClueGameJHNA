package clueGame;

import java.awt.Color;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	//SUGGESTION FOR CODE
	
	private BoardCell lastVisitedOn = new BoardCell(4, 0);
	
	//SUGGESTION FOR CODE
	
	public BoardCell getLastVisitedOn() {
		return lastVisitedOn;
	}

	public void setLastVisitedOn(BoardCell lastVisitedOn) {
		this.lastVisitedOn = lastVisitedOn;
	}

	public ComputerPlayer() {
		super();
	}
	
	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
		// TODO Auto-generated constructor stub
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		BoardCell cell = new BoardCell(0, 5, 'W');
		return cell;
	}
	
	public void makeAccusation() {
		
	}
	
	public void createSuggestion() {
		
	}
}
