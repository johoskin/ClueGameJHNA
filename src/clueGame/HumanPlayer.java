package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Set;

public class HumanPlayer extends Player{
	
	private String suggPerson;
	private String suggRoom;
	private String suggWeapon;

	public HumanPlayer(String playerName, int row, int column, Color color, boolean isHuman) {
		super(playerName, row, column, color, isHuman);
	}
	
	public HumanPlayer() {
		super();
	}
	
	@Override
	public void makeMove(Set<BoardCell> targets) {
		ArrayList<BoardCell> arrTargets = new ArrayList<BoardCell>();
		for(BoardCell c: targets) {
			arrTargets.add(c);
		}
		
		setRow(arrTargets.get(0).getRow());
		setColumn(arrTargets.get(0).getColumn());
	}

}
