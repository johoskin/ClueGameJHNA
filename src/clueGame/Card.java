package clueGame;

public class Card {
	private String cardName;
	private CardType cardType;
	private boolean dealt = false;
	
	
	public Card() {
		
	}
	
	public boolean isDealt(){
		if(dealt == false){
			return false;
		}else{
			return true;
		}
	}
	
	public void setDealt(boolean dealt){
		this.dealt = dealt;
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
