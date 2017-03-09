package tests;

import java.util.Set;

//Doing a static import allows me to write assertEquals rather than
//assertEquals
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class ASTS_BoardAdjTargetTests {
	
	//create one board
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it		
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ASTS_ClueLayout.csv", "ASTS_ClueLegend.txt");		
		board.initialize();
	}
	
	// Ensure that player does not move around within room
		// These cells are ORANGE on the planning spreadsheet
		@Test
		public void testAdjacenciesInsideRooms()
		{
			// Test a corner (ALSO EDGE LOCATION)
			Set<BoardCell> testList = board.getAdjList(0, 21);
			assertEquals(0, testList.size());
			// Test one that has walkway underneath (ALSO EDGE LOCATION)
			testList = board.getAdjList(17, 0);
			assertEquals(0, testList.size());
		}
		
		// walkway scenarios
		// These tests are WHITE on the planning spreadsheet
		@Test
		public void testAdjacencyWalkways()
		{
			// Test on top edge of board, 3 walkway pieces (ALSO EDGE LOCATION)
			Set<BoardCell> testList = board.getAdjList(0, 5);
			assertTrue(testList.contains(board.getCellAt(0, 4)));
			assertTrue(testList.contains(board.getCellAt(0, 6)));
			assertTrue(testList.contains(board.getCellAt(1, 5)));
			assertEquals(3, testList.size());
			
			// Test middle of board, 4 walkway pieces (ONLY WALKWAY ADJACENT)
			testList = board.getAdjList(12, 7);
			assertTrue(testList.contains(board.getCellAt(12, 6)));
			assertTrue(testList.contains(board.getCellAt(12, 8)));
			assertTrue(testList.contains(board.getCellAt(11, 7)));
			assertTrue(testList.contains(board.getCellAt(13, 7)));
			assertEquals(4, testList.size());
			
			//Test right edge location, beside room cell, 2 pieces (BESIDE ROOM CELL)
			testList = board.getAdjList(12, 21);
			assertTrue(testList.contains(board.getCellAt(12, 20)));
			assertTrue(testList.contains(board.getCellAt(11, 21)));
			assertEquals(2, testList.size());
			
			//Test beside room cell, 3 pieces (BESIDE ROOM CELL)
			testList = board.getAdjList(9, 5);
			assertTrue(testList.contains(board.getCellAt(9, 6)));
			assertTrue(testList.contains(board.getCellAt(10, 5)));
			assertTrue(testList.contains(board.getCellAt(8, 5)));
			assertEquals(3, testList.size());
		}
		
		// Test adjacency at entrance to rooms
		// These tests are GREEN in planning spreadsheet
		@Test
		public void testAdjacencyDoorways()
		{
			// Test beside a door direction RIGHT
			Set<BoardCell> testList = board.getAdjList(16, 7);
			assertTrue(testList.contains(board.getCellAt(16, 8)));
			assertTrue(testList.contains(board.getCellAt(15, 7)));
			assertTrue(testList.contains(board.getCellAt(17, 7)));
			assertTrue(testList.contains(board.getCellAt(16, 6)));//door
			assertEquals(4, testList.size());
			// Test beside a door direction DOWN
			testList = board.getAdjList(4, 20);
			assertTrue(testList.contains(board.getCellAt(4, 19)));
			assertTrue(testList.contains(board.getCellAt(4, 21)));
			assertTrue(testList.contains(board.getCellAt(5, 20)));
			assertTrue(testList.contains(board.getCellAt(3, 20)));//door
			assertEquals(4, testList.size());
			// Test beside a door direction LEFT
			testList = board.getAdjList(14, 15);
			assertTrue(testList.contains(board.getCellAt(13, 15)));
			assertTrue(testList.contains(board.getCellAt(15, 15)));
			assertTrue(testList.contains(board.getCellAt(14, 14)));
			assertTrue(testList.contains(board.getCellAt(14, 16)));//door
			assertEquals(4, testList.size());
			// Test beside a door direction UP
			testList = board.getAdjList(19, 15);
			assertTrue(testList.contains(board.getCellAt(18, 15)));
			assertTrue(testList.contains(board.getCellAt(19, 14)));
			assertTrue(testList.contains(board.getCellAt(19, 16)));
			assertTrue(testList.contains(board.getCellAt(20, 15)));//door
			assertEquals(4, testList.size());
		}
		
		// Ensure that the adjacency list from a doorway is only the walkway
		// These tests are BLUE on the planning spreadsheet
		@Test
		public void testAdjacencyRoomExit()
		{
			// TEST DOORWAY UP 
			Set<BoardCell> testList = board.getAdjList(6, 3);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCellAt(5, 3)));
			//TEST DOORWAY DOWN
			testList = board.getAdjList(3, 6);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCellAt(4, 6)));
		}
		
		
		
		//TARGET TESTS START
		
		
		
		// Tests of just walkways, 1 step, includes on edge of board
		// and beside room
		// Have already tested adjacency lists on all four edges, will
		// only test two edges here
		// These are MAGENTA on the planning spreadsheet
		@Test
		public void testTargetsVariousSteps() {
			
			//left edge 1 step
			board.calcTargets(4, 0, 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(2, targets.size());
			assertTrue(targets.contains(board.getCellAt(4, 1)));
			assertTrue(targets.contains(board.getCellAt(5, 0)));	
			
			//bottom middle 2 steps
			board.calcTargets(18, 8, 2);
			targets= board.getTargets();
			assertEquals(7, targets.size());
			assertTrue(targets.contains(board.getCellAt(18, 6)));
			assertTrue(targets.contains(board.getCellAt(19, 7)));
			assertTrue(targets.contains(board.getCellAt(17, 7)));
			assertTrue(targets.contains(board.getCellAt(16, 8)));
			assertTrue(targets.contains(board.getCellAt(17, 9)));
			assertTrue(targets.contains(board.getCellAt(18, 10)));
			assertTrue(targets.contains(board.getCellAt(19, 9)));
			
			//left middle 3 steps
			board.calcTargets(13, 6, 3);
			targets= board.getTargets();
			assertEquals(12, targets.size());
			assertTrue(targets.contains(board.getCellAt(14, 4)));
			assertTrue(targets.contains(board.getCellAt(11, 5)));
			assertTrue(targets.contains(board.getCellAt(10, 6)));
			assertTrue(targets.contains(board.getCellAt(11, 7)));
			assertTrue(targets.contains(board.getCellAt(12, 8)));
			assertTrue(targets.contains(board.getCellAt(13, 9)));
			assertTrue(targets.contains(board.getCellAt(15, 7)));
			assertTrue(targets.contains(board.getCellAt(12, 6)));
			assertTrue(targets.contains(board.getCellAt(13, 7)));
			assertTrue(targets.contains(board.getCellAt(14, 6)));
			assertTrue(targets.contains(board.getCellAt(14, 8)));
			assertTrue(targets.contains(board.getCellAt(13, 5)));
			
			//right edge 4 steps
			board.calcTargets(11, 21, 4);
			targets= board.getTargets();
			for(BoardCell cell:targets) {
				System.out.println(cell.getRow() + " " + cell.getColumn());
			}
			assertEquals(5, targets.size());
			assertTrue(targets.contains(board.getCellAt(11, 17)));
			assertTrue(targets.contains(board.getCellAt(10, 18)));//door
			assertTrue(targets.contains(board.getCellAt(12, 18)));
			assertTrue(targets.contains(board.getCellAt(12, 20)));
			assertTrue(targets.contains(board.getCellAt(11, 19)));
		}	
		
		// Test getting into a room
		// These are DARK GREEN on the planning spreadsheet
		@Test 
		public void testTargetsIntoRoom()
		{
			// Door 1 away 2 steps
			board.calcTargets(5, 18, 2);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(5, targets.size());			
			assertTrue(targets.contains(board.getCellAt(6, 18)));//door below
			assertTrue(targets.contains(board.getCellAt(5, 16)));
			assertTrue(targets.contains(board.getCellAt(4, 17)));
			assertTrue(targets.contains(board.getCellAt(4, 19)));
			assertTrue(targets.contains(board.getCellAt(5, 20)));
			
			board.calcTargets(7, 5, 4); //four away from doorway
			targets= board.getTargets();
			assertEquals(14, targets.size());
			assertTrue(targets.contains(board.getCellAt(6, 4)));//door
			assertTrue(targets.contains(board.getCellAt(5, 5)));	
			assertTrue(targets.contains(board.getCellAt(6, 6)));
			assertTrue(targets.contains(board.getCellAt(4, 4)));
			assertTrue(targets.contains(board.getCellAt(7, 7)));
			assertTrue(targets.contains(board.getCellAt(8, 6)));
			assertTrue(targets.contains(board.getCellAt(5, 7)));
			assertTrue(targets.contains(board.getCellAt(4, 6)));
			assertTrue(targets.contains(board.getCellAt(3, 5)));
			assertTrue(targets.contains(board.getCellAt(10, 6)));
			assertTrue(targets.contains(board.getCellAt(11, 5)));
			assertTrue(targets.contains(board.getCellAt(9, 7)));
			assertTrue(targets.contains(board.getCellAt(5, 3)));
			assertTrue(targets.contains(board.getCellAt(6, 4)));
		}

		// Test getting out of a room
		// These are PURPLE on the planning spreadsheet
		@Test
		public void testRoomExit()
		{
			// Take one step, essentially just the adj list
			board.calcTargets(20, 3, 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(1, targets.size());
			assertTrue(targets.contains(board.getCellAt(19, 3)));
			// Take three steps
			board.calcTargets(0, 3, 3);
			targets= board.getTargets();
			assertEquals(3, targets.size());
			assertTrue(targets.contains(board.getCellAt(0, 6)));
			assertTrue(targets.contains(board.getCellAt(1, 5)));
			assertTrue(targets.contains(board.getCellAt(2, 4)));
		}

}
