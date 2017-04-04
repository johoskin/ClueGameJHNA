package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	private String suggPerson;
	private String suggRoom;
	private String suggWeapon;
	public Solution mySolution = new Solution();
	
	public Solution getMySolution() {
		return mySolution;
	}

	public void setMySolution(Solution mySolution) {
		this.mySolution = mySolution;
	}

	private Card[] seen = new Card[22];
	
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
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		ArrayList<BoardCell> tempArr = new ArrayList<BoardCell>();
		for(BoardCell s: targets){
			tempArr.add(s);
		}
		Random randomGen = new Random();
		BoardCell loc = new BoardCell();
		
		
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
	
	public void makeAccusation() {
		mySolution.person = suggPerson;
		mySolution.room = suggRoom;
		mySolution.weapon = suggWeapon;
	}
	
	public void setSuggPerson(String suggPerson) {
		this.suggPerson = suggPerson;
	}

	public void setSuggRoom(String suggRoom) {
		this.suggRoom = suggRoom;
	}

	public void setSuggWeapon(String suggWeapon) {
		this.suggWeapon = suggWeapon;
	}

	public void createSuggestion(ArrayList<Card> seenCards, ArrayList<Card> unSeenCards, Card roomCard) {
		Random randomGen = new Random();

		int randomInt = randomGen.nextInt(unSeenCards.size());
		if(unSeenCards.size() > 1) {
			do {
				randomInt = randomGen.nextInt(unSeenCards.size());
				if(unSeenCards.get(randomInt).getCardType() == CardType.PERSON ) {
					suggPerson = unSeenCards.get(randomInt).getCardName();
				}
			}while (unSeenCards.get(randomInt).getCardType() != CardType.PERSON);

			do {
				randomInt = randomGen.nextInt(unSeenCards.size());
				if(unSeenCards.get(randomInt).getCardType() == CardType.WEAPON) {
					suggWeapon = unSeenCards.get(randomInt).getCardName();
				}
			}while (unSeenCards.get(randomInt).getCardType() != CardType.WEAPON);
		}else {
			if(unSeenCards.get(0).getCardType() == CardType.PERSON) {
				suggPerson = unSeenCards.get(0).getCardName();
			}
			if(unSeenCards.get(0).getCardType() == CardType.WEAPON) {
				suggWeapon = unSeenCards.get(0).getCardName();
			}
		}

		suggRoom = roomCard.getCardName();

	}

}
