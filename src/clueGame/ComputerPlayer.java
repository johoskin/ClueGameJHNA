package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	private String suggPerson;
	private String suggRoom;
	private String suggWeapon;
	
	//SUGGESTION FOR CODE
	
	public String getSuggPerson() {
		return suggPerson;
	}

	public String getSuggRoom() {
		return suggRoom;
	}

	public String getSuggWeapon() {
		return suggWeapon;
	}

	private BoardCell lastVisitedOn = new BoardCell();
	
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
		ArrayList<BoardCell> tempArr = new ArrayList<BoardCell>();
		for(BoardCell s: targets){
			tempArr.add(s);
		}
		//System.out.println(tempArr.toString());
		//System.out.println(tempArr.size());
		Random randomGen = new Random();
		BoardCell loc = new BoardCell();
		//int randInt = randomGen.nextInt(tempArr.size());
		
		
		for(BoardCell s:targets) {
			if(s.isRoom() && lastVisitedOn.isRoom()) {
				int randInt = randomGen.nextInt(tempArr.size());
				loc = tempArr.get(randInt);
			}
			else if (s.isRoom() && !lastVisitedOn.isRoom()) {
				loc = s;
				return loc;
			}
			else {
				int randInt = randomGen.nextInt(tempArr.size());
				loc = tempArr.get(randInt);
			}
		}
		
		return loc;
	}
	
	public void makeAccusation(String person, String weapon, String room) {
		
	}
	
	public void createSuggestion() {
		
	}

}
