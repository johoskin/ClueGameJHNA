package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	private Set<BoardCell> visitedSpots = new HashSet<BoardCell>();
	private boolean noReturn = false;
	private String lastVisited = "Attic";
	private Character lastChar = 'A';
	private Card card = new Card();
	private BoardCell cell;


	//last visited location
	private BoardCell lastLocation = new BoardCell();


	private Card[] seen = new Card[22];
	
	//SUGGESTION FOR CODE
	
	//SUGGESTION FOR CODE
	
	public BoardCell getLastVisitedOn() {
		return lastLocation;
	}

	public void setLastVisitedOn(BoardCell lastVisitedOn) {
		this.lastLocation = lastVisitedOn;
	}

	public ComputerPlayer() {
		super();
	}
	
	public ComputerPlayer(String playerName, int row, int column, Color color, Boolean isHuman) {
		super(playerName, row, column, color, isHuman);
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		ArrayList<BoardCell> tempArr = new ArrayList<BoardCell>();
		for(BoardCell s: targets){
			tempArr.add(s);
		}
		Random randomGen = new Random();
		BoardCell loc = new BoardCell();
		
		
		for(BoardCell s:targets) {
			if(s.isRoom() && lastLocation.isRoom()) {
				int randInt = randomGen.nextInt(tempArr.size());
				loc = tempArr.get(randInt);
			}
			else if (s.isRoom() && !lastLocation.isRoom()) {
				loc = s;
				setLastVisitedOn(loc);
				return loc;
			}
			else {
				int randInt = randomGen.nextInt(tempArr.size());
				loc = tempArr.get(randInt);
			}
		}
		setLastVisitedOn(loc);
		return loc;
	}


	public void createSuggestion(ArrayList<Card> seenCards, ArrayList<Card> unSeenCards, Card roomCard) {
		Random randomGen = new Random();

		int randomInt = randomGen.nextInt(unSeenCards.size());
		if(unSeenCards.size() > 1) {
			do {
				randomInt = randomGen.nextInt(unSeenCards.size());
				if(unSeenCards.get(randomInt).getCardType() == CardType.PERSON ) {
					setSuggPerson(unSeenCards.get(randomInt).getCardName());
				}
			}while (unSeenCards.get(randomInt).getCardType() != CardType.PERSON);

			do {
				randomInt = randomGen.nextInt(unSeenCards.size());
				if(unSeenCards.get(randomInt).getCardType() == CardType.WEAPON) {
					setSuggWeapon(unSeenCards.get(randomInt).getCardName());
				}
			}while (unSeenCards.get(randomInt).getCardType() != CardType.WEAPON);
		}else {
			if(unSeenCards.get(0).getCardType() == CardType.PERSON) {
				setSuggPerson(unSeenCards.get(0).getCardName());
			}
			if(unSeenCards.get(0).getCardType() == CardType.WEAPON) {
				setSuggWeapon(unSeenCards.get(0).getCardName());
			}
		}

		setSuggRoom(roomCard.getCardName());
		setMySolution(getSuggRoom(), getSuggPerson(), getSuggWeapon());
	}
	
	@Override
	public void makeMove(Set<BoardCell> targets) {
		
		if(noReturn == true) {
			makeAccusation();
			compAcc = true;
			return;
		}
		
		cell = pickLocation(targets);
		
		if(cell.isRoom() && cell.getInitial() == lastChar) {
			do {
				cell = pickLocation(targets);
			} while (cell.getInitial() == lastChar);
		}
		setRow(cell.getRow());
		setColumn(cell.getColumn());
		
		
		
		if(cell.isRoom()) {
			lastChar = cell.getInitial();
			switch(cell.getInitial()) {
			case('B'):
				cell.setRoomName("Bathroom");
				lastVisited = "BATHROOM";
				break;
			case('T'):
				cell.setRoomName("Theater");
				lastVisited = "THEATHER";
				break;
			case('G'):
				cell.setRoomName("Game Room");
				lastVisited = "GAME ROOM";
				break;
			case('K'):
				cell.setRoomName("Kitchen");
				lastVisited = "KITCHEN";
				break;
			case('A'):
				cell.setRoomName("Annex");
				lastVisited = "ANNEX";
				break;
			case('C'):
				cell.setRoomName("Attic");
				lastVisited = "ATTIC";
				break;
			case('Y'):
				cell.setRoomName("Balcony");
				lastVisited = "BALCONY";
				break;
			case('L'):
				cell.setRoomName("Living Room");
				lastVisited = "LIVING ROOM";
				break;
			case('O'):
				cell.setRoomName("Office");
				lastVisited = "OFFICE";
				break;
			default: 
				break;
			}
			//System.out.println(cell.getRoomName() + "heyy now");
			
			card.setCardName(cell.getRoomName());
			card.setCardType(CardType.ROOM);
			createSuggestion(seenCards, unSeenCards, card);
			//System.out.println(unSeenCards.size() + " unseen " + seenCards.size() + " seen " + card.getCardName() + " card name ");
			inRoom = true;
			System.out.println(suggPerson + " sugPerson " + suggWeapon + " sugWeapon " + suggRoom);
		}
		else {
			control.setGuess(" ");
			control.setResponse(" ");
		}
	}

	public boolean isNoReturn() {
		return noReturn;
	}

	public void setNoReturn(boolean noReturn) {
		this.noReturn = noReturn;
	}
	
	@Override
	public void compReturn() {
		noReturn = true;
	}

}
