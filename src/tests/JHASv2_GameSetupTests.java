package tests;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;

public class JHASv2_GameSetupTests {

	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it		
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ASTS_ClueLayout.csv", "ASTS_ClueLegend.txt");	
		board.setPlayerConfig("JHASv2_CluePlayer.txt");
		board.initialize();
	}
	
	
	//COMMMENT HERE
	@Test
	public void loadPlayersTest() {
		//look at players in board, ensure the name matches, as well as other attributes
		Player[] players = new Player[6];
		players = board.getPlayers();
		assertEquals(players[0].getPlayerName(), "Colonel Mustard"); //testing name
		assertEquals(players[1].getPlayerName(), "Mrs. White"); //testing name
		assertEquals(players[2].getPlayerName(), "Professor Plum"); //testing name
		assertEquals(players[3].getPlayerName(), "Mrs. Peacock"); //testing name
		assertEquals(players[4].getPlayerName(), "Mr. Green"); //testing name
		assertEquals(players[5].getPlayerName(), "Miss Scarlet"); //testing name
		assertTrue(players[0].getColor().equals(Color.yellow)); //testing color colonel mustard
		assertTrue(players[3].getColor().equals(Color.blue)); //testing color mrs. peacock
		assertEquals(players[2].getColumn(), 17); //testing column
		assertEquals(players[5].getColumn(), 15); //testing column
		assertEquals(players[2].getRow(), 19); //testing row
		assertEquals(players[5].getRow(), 2); //testing row
	}


	@Test
	public void loadCardsTest() {
		
		assertEquals(board.getCards().length, 21); //testing deck size
		
		int people = 0;
		int weapon = 0;
		int room = 0;
		for(int i = 0; i < board.getCards().length; i++){
			if(board.getCards()[i].getCardType() == CardType.PERSON){
				people++;
			}
			if(board.getCards()[i].getCardType() == CardType.WEAPON){
				weapon++;
			}
			if(board.getCards()[i].getCardType() == CardType.ROOM){
				room++;
			}
		}
		
		//Testing correct number of types of card
		assertEquals(people, 6);
		assertEquals(weapon, 6);
		assertEquals(room, 9);
		
		//Testing that deck contains actual cards
		assertEquals(board.getCards()[4].getCardName(), "Mr. Green");
		assertEquals(board.getCards()[11].getCardName(), "Candlestick");
		assertEquals(board.getCards()[17].getCardName(), "Attic");

	}
}








