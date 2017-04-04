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
		board.setCardConfig("JHASv2_ClueCards.txt");
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
		
		assertEquals(board.getCards().size(), 21); //testing deck size
		
		int people = 0;
		int weapon = 0;
		int room = 0;
		for(int i = 0; i < board.getCards().size(); i++){
			if(board.getCards().get(i).getCardType() == CardType.PERSON){
				people++;
			}
			if(board.getCards().get(i).getCardType() == CardType.WEAPON){
				weapon++;
			}
			if(board.getCards().get(i).getCardType() == CardType.ROOM){
				room++;
			}
		}
		
		//Testing correct number of types of card
		assertEquals(people, 6);
		assertEquals(weapon, 6);
		assertEquals(room, 9);
		
		//Testing that deck contains actual cards
		assertEquals(board.getCards().get(4).getCardName(), "Mr. Green");
		assertEquals(board.getCards().get(11).getCardName(), "Candlestick");
		assertEquals(board.getCards().get(17).getCardName(), "Attic");

	}
	
	@Test
	public void dealCardsTest() {
		
		//All cards are dealt
		for(int i = 0; i < board.getCards().size(); i++){
			if(board.getCards().get(i).isDealt() == false){
				fail();
			}
		}
		
		//test no player has solution
		for(int i = 0; i < board.getPlayers().length; i++){
			for(int j =0; j < board.getPlayers()[i].getCards().size(); j++) {
				if(board.getPlayers()[i].getCards().get(j).getCardName().equals(board.solution.person) || board.getPlayers()[i].getCards().get(j).getCardName().equals(board.solution.room) || board.getPlayers()[i].getCards().get(j).getCardName().equals(board.solution.weapon)) {
					fail();
				}
			}
		}
		
		
		
		//All players have same amt of cards (roughly)
		int cardTestNum = board.getPlayers()[0].getCards().size();
		for(int i = 1; i < board.getPlayers().length; i++){
			int compareCardNum = board.getPlayers()[i].getCards().size();
			if(compareCardNum - cardTestNum <= 1 && compareCardNum - cardTestNum >= -1){
				//System.out.println(compareCardNum - cardTestNum);
				continue;
			}
			fail();
		}
		
		//same card not given to multiple players
		for(int i = 0; i < board.getPlayers().length; i++){
			for(int j = 0; j < board.getPlayers().length; j++){
				if(i != j){
					for(int k = 0; k < board.getPlayers()[i].getCards().size(); k++){
						for(int l = 0; l < board.getPlayers()[j].getCards().size(); l++){
							if(board.getPlayers()[i].getCards().get(k) == board.getPlayers()[j].getCards().get(l)){
								fail();
							}else{
								continue;
							}
						}
					}
				}
			}
		}

	}
}








