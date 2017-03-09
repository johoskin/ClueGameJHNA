package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class ASTS_FileInitTets {
	// Constants that I will use to test whether the file was loaded correctly
		public static final int LEGEND_SIZE = 11;
		public static final int NUM_ROWS = 22;
		public static final int NUM_COLUMNS = 22;
		public static final int numOfDoors = 26;

		// NOTE: I made Board static because I only want to set it up one 
		// time (using @BeforeClass), no need to do setup before each test.
		private static Board board;
		
		@BeforeClass
		public static void setUp() {
			// Board is singleton, get the only instance
			board = Board.getInstance();
			// set the file names to use my config files
			board.setConfigFiles("ASTS_ClueLayout.csv", "ASTS_ClueLegend.txt");		
			// Initialize will load BOTH config files 
			board.initialize();
		}
		@Test
		public void testRooms() {
			// Get the map of initial => room 
			Map<Character, String> legend = board.getLegend();
			// Ensure we read the correct number of rooms
			assertEquals(LEGEND_SIZE, legend.size());
			// To ensure data is correctly loaded, test retrieving a few rooms 
			// from the hash, including the first and last in the file and a few others
			assertEquals("Bathroom", legend.get('B'));
			assertEquals("Theater", legend.get('T'));
			assertEquals("Game Room", legend.get('G'));
			assertEquals("Office", legend.get('O'));
			assertEquals("Hallway", legend.get('H'));
			assertEquals("Closet", legend.get('X'));
		}
		
		@Test
		public void testBoardDimensions() {
			// Ensure we have the proper number of rows and columns
			assertEquals(NUM_ROWS, board.getNumRows());
			assertEquals(NUM_COLUMNS, board.getNumColumns());		
		}
		
		// Test a doorway in each direction (RIGHT/LEFT/UP/DOWN), plus 
		// two cells that are not a doorway.
		// These cells are grey on the planning spreadsheet
		@Test
		public void FourDoorDirections() {
			BoardCell room = board.getCellAt(0, 3);//Attic right door
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
			room = board.getCellAt(3, 6);//Theater down door
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.DOWN, room.getDoorDirection());
			room = board.getCellAt(14, 16);//Kitchen left door
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.LEFT, room.getDoorDirection());
			room = board.getCellAt(13, 11);//Annex up door
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.UP, room.getDoorDirection());
			// Test that room pieces that aren't doors know it
			room = board.getCellAt(9, 9); //closet
			assertFalse(room.isDoorway());	
			// Test that walkways are not doors
			BoardCell cell = board.getCellAt(16, 3); //Bathroom cell
			assertFalse(cell.isDoorway());		

		}
		// Test that we have the correct number of doors
		@Test
		public void testNumberOfDoorways() 
		{
			int numDoors = 0;
			for (int row=0; row<board.getNumRows(); row++)
				for (int col=0; col<board.getNumColumns(); col++) {
					BoardCell cell = board.getCellAt(row, col);
					if (cell.isDoorway())
						numDoors++;
				}
			Assert.assertEquals(numOfDoors, numDoors);
		}

		// Test a few room cells to ensure the room initial is correct.
		@Test
		public void testRoomInitials() {
			// Test first cell in room
			assertEquals('C', board.getCellAt(0, 0).getInitial());//Attic
			assertEquals('T', board.getCellAt(2, 10).getInitial());
			assertEquals('B', board.getCellAt(16, 3).getInitial());
			// Test last cell in room
			assertEquals('Y', board.getCellAt(21, 21).getInitial());
			assertEquals('K', board.getCellAt(15, 19).getInitial());
			assertEquals('O', board.getCellAt(10,21).getInitial());
			// Test a hallway
			assertEquals('W', board.getCellAt(9, 6).getInitial());
			// Test the closet
			assertEquals('X', board.getCellAt(10,8).getInitial());
		}
}
