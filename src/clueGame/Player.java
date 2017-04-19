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
	protected boolean inRoom =false;
	protected boolean compAcc = false;
	protected ControlGUI control;

	protected ArrayList<Card> matching = new ArrayList<Card>();
	
	public boolean isCompAcc() {
		return compAcc;
	}

	public void setCompAcc(boolean compAcc) {
		this.compAcc = compAcc;
	}
	
	public boolean isInRoom() {
		return inRoom;
	}

	public void setInRoom(boolean inRoom) {
		this.inRoom = inRoom;
	}

	protected ArrayList<Card> myCards = new ArrayList<Card>();
	
	protected String suggPerson;
	protected String suggRoom;
	protected String suggWeapon;
	
	
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

	public void setMySolution(String room, String person, String weapon) {
		this.mySolution.room = room;
		this.mySolution.person = person;
		this.mySolution.weapon = weapon;
	}
	
	public void clearSolution() {
		this.mySolution = null;
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
		control = ControlGUI.getInstance();
	}
	
	public boolean isHuman() {
		return isHuman;
	}

	public ArrayList<Card> getCards() {
		return myCards;
	}

	public Card disproveSuggestion(Solution suggestion) {
		matching.clear();
		
		//Card person = new Card(suggestion.person, CardType.PERSON);
		//Card room = new Card(suggestion.room, CardType.ROOM);
		//Card weapon = new Card(suggestion.weapon, CardType.WEAPON);
		//System.out.println(myCards.size());
		System.out.println("disprove suggestion " + suggestion.person + " " + suggestion.weapon + " " + suggestion.room);
		for(Card c: myCards) {
			
			if(c.getCardName().equals(suggestion.person)) {
				System.out.println(c.getCardName());
				matching.add(c);
			}
			if(c.getCardName().equals(suggestion.weapon)) {
				System.out.println(c.getCardName());
				matching.add(c);
			}
			if(c.getCardName().equals(suggestion.room)) {
				System.out.println(c.getCardName());
				matching.add(c);
			}
			
		}
		
		//if(myCards.contains(person) matching.add(person);
		//if(myCards.contains(weapon.getCardName())) matching.add(weapon);
		//if(myCards.contains(room.getCardName())) matching.add(room);
		
		/*for(int i = 0; i < myCards.size(); i++){
			
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
			
		}*/
		System.out.println(matching.size() + " matching size");
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
