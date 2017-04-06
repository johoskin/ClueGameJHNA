package clueGame;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class BoardCell extends JPanel{
	int row,column;
	char initial;
	private boolean name = false;
	private String roomName;
	DoorDirection DD;
	static int CELL_WIDTH = 25;
	static int CELL_HEIGHT = 25;
	
	public BoardCell() {
		
	}

	public BoardCell(int r, int c) {
		super();
		this.row = r;
		this.column = c;
		this.initial = 'n';
		this.DD = DoorDirection.NONE;
	}
	
	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + ", initial=" + initial + ", DD=" + DD + "]";
	}

	public BoardCell(int r, int c, char ini) {
		super();
		this.row = r;
		this.column = c;
		this.initial = ini;
		this.DD = DoorDirection.NONE;
	}
	
	public BoardCell(int r, int c, char ini, char d) {
		super();
		this.row = r;
		this.column = c;
		this.initial = ini;
		switch(d) {
		case 'D':
			this.DD = DoorDirection.DOWN;
			break;
		case 'U':
			this.DD = DoorDirection.UP;
			break;
		case 'R':
			this.DD = DoorDirection.RIGHT;
			break;
		case 'L':
			this.DD = DoorDirection.LEFT;
			break;
		case 'N':
			name = true;
			this.DD = DoorDirection.NONE;
		}
	}

	public boolean isDoorway() {
		if(this.getDoorDirection() != DoorDirection.NONE)
			return true;
		return false;
	}
	
	public boolean isWalkway() {
		if(this.initial == 'W')
			return true;
		return false;
	}
	
	public boolean isRoom() {
		if(this.initial != 'W')
			return true;
		return false;
	}

	public DoorDirection getDoorDirection() {
		return this.DD;
	}

	public char getInitial() {
		return this.initial;
	}

	public int getRow() {
		return this.row;
	}

	public int getColumn() {
		return this.column;
	}
	
	public void DrawBoardCell(Graphics g) {
		if(isRoom()) {
			g.setColor(Color.lightGray);
			g.fillRect(column*CELL_HEIGHT, row*CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
			
		}
		else {
			g.setColor(Color.YELLOW);
			g.fillRect(column*CELL_HEIGHT, row*CELL_HEIGHT, CELL_WIDTH-1, CELL_HEIGHT-1);
			g.setColor(Color.BLACK);
			g.drawRect(column*CELL_HEIGHT, row*CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
			
			
		}
		//g.drawRect(x,y,CELL_WIDTH,CELL_HEIGHT);
		//g.fillRect(column*CELL_HEIGHT, row*CELL_HEIGHT, CELL_WIDTH-1, CELL_HEIGHT-1);
		//TODO doorway color
		
		if(isRoom() && name) {
			switch(initial) {
			case('B'):
				roomName = "BATHROOM";
				break;
			case('T'):
				roomName = "THEATHER";
				break;
			case('G'):
				roomName = "GAME ROOM";
				break;
			case('K'):
				roomName = "KITCHEN";
				break;
			case('A'):
				roomName = "ANNEX";
				break;
			case('C'):
				roomName = "ATTIC";
				break;
			case('Y'):
				roomName = "BALCONY";
				break;
			case('L'):
				roomName = "LIVING ROOM";
				break;
			case('O'):
				roomName = "OFFICE";
				break;
			default: 
				break;
			}
			g.setColor(Color.RED);
			g.drawString(roomName, column*CELL_HEIGHT, row*CELL_HEIGHT);
		}
		
		if(isDoorway()) {
			g.setColor(Color.CYAN);
			switch(DD) {
			case DOWN:
				g.fillRect(column*CELL_HEIGHT, row*CELL_HEIGHT+23, CELL_WIDTH, 2);
				break;
			case UP:
				g.fillRect(column*CELL_HEIGHT, row*CELL_HEIGHT, CELL_WIDTH, 2);
				break;
			case RIGHT:
				g.fillRect(column*CELL_HEIGHT+23, row*CELL_HEIGHT, 2, CELL_HEIGHT);
				break;
			case LEFT:
				g.fillRect(column*CELL_HEIGHT, row*CELL_HEIGHT, 2, CELL_HEIGHT);
				break;
			default:
				break;
		}
		
		}
	}
	
	
}
