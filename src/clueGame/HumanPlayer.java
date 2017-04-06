package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class HumanPlayer extends Player{
	
	private String suggPerson;
	private String suggRoom;
	private String suggWeapon;

	public HumanPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
	}
	
	public HumanPlayer() {
		super();
	}
	

}
