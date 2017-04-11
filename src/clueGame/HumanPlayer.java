package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Set;

public class HumanPlayer extends Player{
	
	private String suggPerson;
	private String suggRoom;
	private String suggWeapon;
	private boolean isTurnOver = false;

	public void setTurnOver(boolean isTurnOver) {
		this.isTurnOver = isTurnOver;
	}

	public HumanPlayer(String playerName, int row, int column, Color color, boolean isHuman) {
		super(playerName, row, column, color, isHuman);
	}
	
	public HumanPlayer() {
		super();
	}
	
	@Override
	public void makeMove(Set<BoardCell> targets) {
		
	}

}
