package experiment;

import java.util.*;

public class IntBoard {
	private Map<BoardCell, Set<BoardCell>> adjMtx; //adjacency matrix
	private Set<BoardCell> visited; //visited cells
	private Set<BoardCell> targets; //targets for the player to move to
	private BoardCell[][] grid; //playing board
	
	public IntBoard(int xSize,int ySize) {
		adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		grid = new BoardCell[xSize][ySize];
		
		//create the BoardCells in every slot
		for(int i = 0; i < xSize; i++) {
			for(int j = 0; j < ySize; j++) {
				grid[i][j] = new BoardCell(i,j);
			}
		}
		calcAdjacencies();
	}
	
	private void calcAdjacencies() {
		for(int i = 0; i < grid.length; i++) { //y length
			for(int j = 0; j < grid[0].length; j++) { //x length
				HashSet <BoardCell> temp = new HashSet<BoardCell>();
				int leftPosition = j - 1;
				int rightPosition = j + 1;
				int upPosition = i - 1;
				int downPosition = i + 1;
				
				//check all positions
				if(leftPosition >= 0) {
					temp.add(grid[i][leftPosition]);
				}
				if(rightPosition < grid[0].length) {
					temp.add(grid[i][rightPosition]);
				}
				if(upPosition >= 0) {
					temp.add(grid[upPosition][j]);
				}
				if(downPosition < grid.length) {
					temp.add(grid[downPosition][j]);
				}
				adjMtx.put(grid[i][j], temp);
			}
		}
	}
	
	public void calcTargets(BoardCell startCell,int pathLength) {
		visited.add(startCell);
		HashSet<BoardCell> adj = new HashSet<BoardCell>(getAdjacencies(startCell));
		for(BoardCell cell:adj) {
			if(!visited.contains(cell)) {
				visited.add(cell);
				if(pathLength == 1) {
					targets.add(cell);
				} else {
					calcTargets(cell, pathLength - 1);
				}
				visited.remove(cell);
			}
		}
	}
	
	public BoardCell getCell(int x, int y) {
		return grid[x][y];
	}

	public Set<BoardCell> getAdjacencies(BoardCell cell) { //return adjacencies to the cell
		return adjMtx.get(cell);
	}

	public Set<BoardCell> getTargets() { //return the targets for player to move to
		return targets;
	}
}
