package clueGame;

public class BoardCell {
	int row,column;
	char initial;
	DoorDirection DD;
	
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
	
	
}
