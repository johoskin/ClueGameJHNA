package clueGame;
import java.awt.Color;
import java.util.ArrayList;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	protected ArrayList<Card> myCards = new ArrayList<Card>();
	protected ArrayList<Card> seenCards = new ArrayList<Card>();
	
	
	public ArrayList<Card> getSeenCards() {
		return seenCards;
	}

	public void setSeenCards(ArrayList<Card> seenCards) {
		this.seenCards = seenCards;
	}

	public Player(){
		
	}
	
	public Player(String playerName, int row, int column, Color color){
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
	}
	
	public ArrayList<Card> getMyCards() {
		return myCards;
	}

	public Card disproveSuggestion(Solution suggestion) {
		Card card = new Card();
		
		return card;
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
}
