package clueGame;

public class Card {
	private String cardName;
	private CardType cardType;
	
	
	
	public Card() {
		
	}

	public Card(String cardName, CardType cardType) {
		this.cardName = cardName;
		this.cardType = cardType;
	}
	
	public boolean equals() {
		
		return false;
	}
	public String getCardName() {
		return cardName;
	}
	public CardType getCardType() {
		return cardType;
	}
}
