package clueGame;
import java.awt.Color;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private Card[] myCards = new Card[21];
	private Card[] seenCards;
	
	public Player(String playerName, int row, int column, Color color){
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
	}
	
	public Card[] getMyCards() {
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
