package clueGame;

import java.awt.Color;

public class HumanPlayer extends Player{
	
	private String suggPerson;
	private String suggRoom;
	private String suggWeapon;

	public HumanPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
		// TODO Auto-generated constructor stub
	}
	
	public HumanPlayer() {
		super();
	}

}
