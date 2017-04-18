package clueGame;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private boolean isHuman = false;
	private boolean isTurn = false;
	protected ArrayList<Card> myCards = new ArrayList<Card>();
	
	private String suggPerson;
	private String suggRoom;
	private String suggWeapon;
	
	
	public String getSuggPerson() {
		return suggPerson;
	}

	public void setSuggPerson(String suggPerson) {
		this.suggPerson = suggPerson;
	}

	public String getSuggRoom() {
		return suggRoom;
	}

	public void setSuggRoom(String suggRoom) {
		this.suggRoom = suggRoom;
	}

	public String getSuggWeapon() {
		return suggWeapon;
	}

	public void setSuggWeapon(String suggWeapon) {
		this.suggWeapon = suggWeapon;
	}

	public Solution mySolution = new Solution();
	
	public Solution getMySolution() {
		return mySolution;
	}

	public void setMySolution(Solution mySolution) {
		this.mySolution = mySolution;
	}
	
	
	public void makeAccusation() {
		mySolution.person = suggPerson;
		mySolution.room = suggRoom;
		mySolution.weapon = suggWeapon;
	}
	
	
	public boolean isTurn() {
		return isTurn;
	}

	public void setTurn(boolean isTurn) {
		this.isTurn = isTurn;
	}

	protected ArrayList<Card> seenCards = new ArrayList<Card>();
	protected ArrayList<Card> unSeenCards = new ArrayList<Card>();
	
	static int CELL_WIDTH = 27;
	static int CELL_HEIGHT = 27;
	

	
	
	public ArrayList<Card> getUnSeenCards() {
		return unSeenCards;
	}

	public void setUnSeenCards(ArrayList<Card> unSeenCards) {
		this.unSeenCards = unSeenCards;
	}

	public ArrayList<Card> getSeenCards() {
		return seenCards;
	}

	public void setSeenCards(ArrayList<Card> seenCards) {
		this.seenCards = seenCards;
	}

	public Player(){
		
	}
	
	public Player(String playerName, int row, int column, Color color, boolean isHuman){
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
		this.isHuman = isHuman;
	}
	
	public boolean isHuman() {
		return isHuman;
	}

	public ArrayList<Card> getCards() {
		return myCards;
	}

	public Card disproveSuggestion(Solution suggestion) {
		ArrayList<Card> matching = new ArrayList<Card>();
		for(int i = 0; i < myCards.size(); i++){
			
			if(getCards().get(i).getCardType() == CardType.PERSON){
				if(myCards.get(i).getCardName().equals(suggestion.person)){
					matching.add(getCards().get(i));
				}
			}
			
			if(myCards.get(i).getCardType() == CardType.WEAPON){
				if(myCards.get(i).getCardName().equals(suggestion.weapon)){
					matching.add(getCards().get(i));
				}
			}
			
			if(getCards().get(i).getCardType() == CardType.ROOM){
				if(getCards().get(i).getCardName().equals(suggestion.room)){
					matching.add(getCards().get(i));
				}
			}
			
		}
		
		if(matching.size() == 0){
			return null;
		}
		
		if(matching.size() == 1){
			return matching.get(0);
		}
		Random randomGen = new Random();
		int randomInt = randomGen.nextInt(matching.size());
		return matching.get(randomInt);
	}


	public void setMyCards(ArrayList<Card> myCards) {
		this.myCards = myCards;
	}

	public String getPlayerName() {
		return playerName;
	}


	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}


	public int getRow() {
		return row;
	}


	public void setRow(int row) {
		this.row = row;
	}


	public int getColumn() {
		return column;
	}


	public void setColumn(int column) {
		this.column = column;
	}


	public Color getColor() {
		return color;
	}


	public void setColor(Color color) {
		this.color = color;
	}
	
	public void drawPlayer(Graphics g) {
		if(getColor().equals(Color.YELLOW)) {
			setColor(new Color(153,153,0));
		}
		g.setColor(Color.BLACK);
		g.drawOval(getColumn()*CELL_HEIGHT, getRow()*CELL_WIDTH, CELL_HEIGHT, CELL_WIDTH);
		g.setColor(getColor());
		g.fillOval(getColumn()*CELL_HEIGHT, getRow()*CELL_WIDTH, CELL_HEIGHT, CELL_WIDTH);
	}

	public void makeMove(Set<BoardCell> targets) {
		
	}
	
	public void compReturn() {
		
	}
}
