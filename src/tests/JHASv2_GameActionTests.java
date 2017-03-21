package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

public class JHASv2_GameActionTests {
	
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
	
	//TODO: Test - selecting a target (random with no rooms)
	@Test
	public void testSelectTargetNoRoom(){
		
		ComputerPlayer player = new ComputerPlayer();
		board.calcTargets(11, 14, 1);
		//System.out.println(board.getTargets());
		boolean location10_14 = false;
		boolean location11_13 = false;
		boolean location11_15 = false;
		boolean location12_14 = false;
		
		Set<BoardCell> targets= board.getTargets();
		
		for(int i = 0; i < 200; i++){
			board.calcTargets(11, 14, 1);
			targets = board.getTargets();
			BoardCell selectedCell = player.pickLocation(targets);
			//System.out.println(selectedCell.toString());
			if(selectedCell == board.getCellAt(10, 14))
				location10_14 = true;
			else if(selectedCell == board.getCellAt(11, 13))
				location11_13 = true;
			else if(selectedCell == board.getCellAt(11, 15))
				location11_15 = true;
			else if(selectedCell == board.getCellAt(12, 14))
				location12_14 = true;
			else{
				fail(); //invalid target selected
			}
		}
		
		assertTrue(location10_14);
		assertTrue(location11_13);
		assertTrue(location11_15);
		assertTrue(location12_14);
		
	}
	
	//TODO: Test - selecting a target (Room in target list)
	@Test
	public void testSelectTargetRoomNotVisited(){
		
		ComputerPlayer player = new ComputerPlayer();
		BoardCell roomVisit = new BoardCell(14, 14,'W');
		player.setLastVisitedOn(roomVisit);

		board.calcTargets(14, 15, 1);
		//for(BoardCell s:board.getTargets()) {
			//System.out.println(s.toString());
		//}
		
		Set<BoardCell> targets= board.getTargets();
		BoardCell selectedCell = player.pickLocation(targets);
		//System.out.println(selectedCell.toString());
		//System.out.println(selectedCell.isRoom());
		assertTrue(selectedCell.isRoom());

	}
	
	//TODO: Test - selecting a target (Room in target list but the room was just visited)
	@Test
	public void testSelectTargetRoomVisited(){
		
		ComputerPlayer player = new ComputerPlayer();
		BoardCell roomVisit = new BoardCell(14, 16, 'K');
		player.setLastVisitedOn(roomVisit);
		board.calcTargets(14, 15, 1);
		boolean location14_14 = false;
		boolean location14_16 = false;
		boolean location13_15 = false;
		boolean location15_15 = false;
		
		Set<BoardCell> targets= board.getTargets();
		
		for(int i = 0; i < 200; i++){
			board.calcTargets(14, 15, 1);
			targets = board.getTargets();
			BoardCell selectedCell = player.pickLocation(targets);
			if(selectedCell == board.getCellAt(14, 14))
				location14_14 = true;
			else if(selectedCell == board.getCellAt(14, 16))
				location14_16 = true;
			else if(selectedCell == board.getCellAt(13, 15))
				location13_15 = true;
			else if(selectedCell == board.getCellAt(15, 15))
				location15_15 = true;
			else{
				fail(); //invalid target selected
			}
		}
		
		assertTrue(location14_14);
		assertTrue(location14_16);
		assertTrue(location13_15);
		assertTrue(location15_15);

	}
	
	
	//TODO: Test - making an accusation
	@Test
	public void testAccusation() {
		//correct solution
		board.solution.person = "Miss Scarlet";
		board.solution.room = "Library";
		board.solution.weapon = "Candlestick";
		ComputerPlayer player = new ComputerPlayer();
		player.setSuggPerson("Miss Scarlet");
		player.setSuggRoom("Library");
		player.setSuggWeapon("Candlestick");
		player.makeAccusation();
		assertTrue(board.checkAccusation(player.getMySolution())); 
		
		//solution with wrong person
		player.setSuggPerson("Mr. Green");
		player.setSuggRoom("Library");
		player.setSuggWeapon("Candlestick");
		player.makeAccusation();
		assertFalse(board.checkAccusation(player.getMySolution()));
		 
		
		//solution with wrong weapon
		player.setSuggPerson("Miss Scarlet");
		player.setSuggRoom("Library");
		player.setSuggWeapon("Lead Pipe");
		player.makeAccusation();
		assertFalse(board.checkAccusation(player.getMySolution()));
		
		//solution with wrong room
		player.setSuggPerson("Miss Scarlet");
		player.setSuggRoom("Attic");
		player.setSuggWeapon("Candlestick");
		player.makeAccusation();
		assertFalse(board.checkAccusation(player.getMySolution())); 
	}
	
	//TODO: Test - creating a suggestion
	@Test
	public void testCreateSuggestion() {
		//room matches current location
		board.solution.person = "Colonel Mustard";
		board.solution.weapon = "Pistol";
		board.solution.room = "Balcony";
		ComputerPlayer player = new ComputerPlayer("Rob", 6, 4, Color.yellow);
		ArrayList<Card> testSeen = new ArrayList<Card>();
		ArrayList<Card> testUnSeen = new ArrayList<Card>();
		
		
		
		//testSeen = board.getCards();
		testUnSeen.add(board.getCards().get(0));
		testUnSeen.add(board.getCards().get(8));
		testUnSeen.add(board.getCards().get(18));
		
		//testSeen.remove(0);
		//System.out.println(board.getCards().get(0).getCardName());
		
		//player.setSeenCards(testSeen);
		player.setUnSeenCards(testUnSeen);
		BoardCell playerLoc = board.getCellAt(player.getRow(), player.getColumn());
		Card tempCard = new Card(board.getLegend().get(playerLoc.getInitial()),CardType.ROOM);
		player.createSuggestion(player.getSeenCards(), player.getUnSeenCards(), tempCard);
		//room is same as suggestion
		assertTrue(player.getSuggRoom().equals(board.getLegend().get(playerLoc.getInitial()))); //check parens
		//System.out.println(player.getSuggPerson());
		assertTrue(player.getSuggPerson().equals("Colonel Mustard"));
		
		//testSeen = board.getCards();
		testUnSeen.clear();
		testUnSeen.add(board.getCards().get(0));
		testUnSeen.add(board.getCards().get(8));
		testUnSeen.add(board.getCards().get(18));
		//testSeen.remove(8);
		
		player.setSeenCards(testSeen);
		player.setUnSeenCards(testUnSeen);
		player.createSuggestion(player.getSeenCards(), player.getUnSeenCards(), tempCard);
		//weapon is same as suggestion
		//System.out.println(player.getSuggWeapon());
		assertTrue(player.getSuggWeapon().equals("Pistol"));
		
		//testing that a random card is chosen for suggestion
		//testSeen = board.getCards();
		testUnSeen.clear();
		testUnSeen.add(board.getCards().get(0));
		testUnSeen.add(board.getCards().get(8));
		testUnSeen.add(board.getCards().get(18));
		//System.out.println(board.getCards().get(11).getCardName());
		testUnSeen.add(board.getCards().get(11));
		testUnSeen.add(board.getCards().get(10));
		testUnSeen.add(board.getCards().get(9));
		testUnSeen.add(board.getCards().get(5));
		testUnSeen.add(board.getCards().get(4));
		testUnSeen.add(board.getCards().get(3));
		/*testSeen.remove(11);
		testSeen.remove(10);
		testSeen.remove(9);
		testSeen.remove(5);
		testSeen.remove(4);
		testSeen.remove(3);
		player.setSeenCards(testSeen);
		*/
		player.setUnSeenCards(testUnSeen);
		int mustard = 0;
		int white = 0;
		int plum = 0;
		int peacock = 0;
		int green = 0;
		int scar = 0;
		int rope = 0;
		int knife = 0;
		int pistol = 0;
		int pipe = 0;
		int wrench = 0;
		int candle = 0;
		
		for(int i = 0; i < 300; i++){
			player.createSuggestion(player.getSeenCards(), player.getUnSeenCards(), tempCard);
			//System.out.println(player.getSuggPerson() + " "+ player.getSuggWeapon());
			if(player.getSuggPerson().equals("Colonel Mustard")){
				mustard = mustard+ 1;
			}else if(player.getSuggPerson().equals("Mrs. White")){
				white = white + 1 ;
			}else if(player.getSuggPerson().equals("Professor Plum")){
				plum = plum + 1;
			}else if(player.getSuggPerson().equals("Mrs. Peacock")){
				peacock = peacock + 1;
			}else if(player.getSuggPerson().equals("Mr. Green")){
				green = green + 1;
			}else if(player.getSuggPerson().equals("Miss Scarlet")){
				scar = scar + 1;
			}
			if(player.getSuggWeapon().equals("Rope")){
				rope = rope + 1;
			}else if(player.getSuggWeapon().equals("Knife")){
				knife = knife + 1;
			}else if(player.getSuggWeapon().equals("Pistol")){
				pistol = pistol + 1;
			}else if(player.getSuggWeapon().equals("Lead pipe")){
				pipe = pipe + 1;
			}else if(player.getSuggWeapon().equals("Wrench")){
				wrench = wrench + 1;
			}else if(player.getSuggWeapon().equals("Candlestick")){
				candle = candle + 1;
			}
		}
		//System.out.println(mustard + " " + wrench + " " + pipe + " " + candle);
		//System.out.println(plum + " " + knife + " " + rope + " ");
		assertTrue(candle > 0);
		assertTrue(wrench > 0);
		assertTrue(pipe > 0);
		assertTrue(scar > 0);
		assertTrue(green > 0);
		assertTrue(peacock > 0);
		assertTrue(pistol > 0);
		assertTrue(knife == 0);
		assertTrue(rope == 0);
		assertTrue(plum == 0);
		assertTrue(white == 0);
		assertTrue(mustard > 0);
		
	}
	//TODO: Test - Disproving a suggestion (Computer Player)
	@Test
	public void testDisproveSuggestionComputer() {
		ComputerPlayer player = new ComputerPlayer();
		ArrayList<Card> hand = new ArrayList<Card>();
		
		Solution sol = new Solution();
		sol.person = "Colonel Mustard";
		sol.room = "Game Room";
		sol.weapon = "Wrench";
		
		//one matching card
		hand.add(board.getCards().get(0));
		hand.add(board.getCards().get(1));
		hand.add(board.getCards().get(2));
		hand.add(board.getCards().get(6));
		hand.add(board.getCards().get(12));
		
		player.setMyCards(hand);
		
		
		
		Card tempCard = new Card();
		tempCard = player.disproveSuggestion(sol);
		assertTrue(tempCard.equals(player.getMyCards().get(0)));
		
		//no matching cards
		hand.clear();
		hand.add(board.getCards().get(1));
		hand.add(board.getCards().get(2));
		hand.add(board.getCards().get(6));
		hand.add(board.getCards().get(12));
		player.setMyCards(hand);
		
		tempCard = player.disproveSuggestion(sol);
		assertEquals(tempCard, null);
		//multiple matching cards
		
		hand.clear();
		hand.add(board.getCards().get(0));
		hand.add(board.getCards().get(1));
		hand.add(board.getCards().get(14));
		hand.add(board.getCards().get(10));
		hand.add(board.getCards().get(2));
		hand.add(board.getCards().get(6));
		hand.add(board.getCards().get(12));
		player.setMyCards(hand);
		
		int mustard = 0;
		int wrench = 0;
		int game = 0;
		
		for(int i = 0; i < 300; i++) {
			tempCard = player.disproveSuggestion(sol);
			if(tempCard.equals(player.getMyCards().get(0))) {
				mustard = mustard + 1;
			} else if(tempCard.equals(player.getMyCards().get(2))) {
				game = game + 1;
			} else if(tempCard.equals(player.getMyCards().get(3))) {
				wrench = wrench + 1;
			}
		}
		
		assertTrue(mustard > 0);
		assertTrue(game > 0);
		assertTrue(wrench > 0);
		
		
		
		
		
	}

	//TODO: Test - handle suggestion
	@Test
	public void testHandleSuggestion() {
		
		HumanPlayer p1 = new HumanPlayer();
		ComputerPlayer p2 = new ComputerPlayer();
		ComputerPlayer p3 = new ComputerPlayer();
		ArrayList<Player> tempPlayers = new ArrayList<Player>();
		
		tempPlayers.add(p1);
		tempPlayers.add(p2);
		tempPlayers.add(p3);
		
		
		ArrayList<Card> hand1 = new ArrayList<Card>();
		ArrayList<Card> hand2 = new ArrayList<Card>();
		ArrayList<Card> hand3 = new ArrayList<Card>();

		Solution sol = new Solution();
		sol.person = "Colonel Mustard";
		sol.room = "Game Room";
		sol.weapon = "Wrench";

		//no players can disprove
		hand1.add(board.getCards().get(1));
		hand1.add(board.getCards().get(6));
		hand1.add(board.getCards().get(16));
		tempPlayers.get(0).setMyCards(hand1);
		
		//hand1.clear();
		//for(int i = 0 ; i < board.getCards().size(); i++) {
		//	System.out.println(i + " " + board.getCards().get(i).getCardName());
		//}
		
		hand2.add(board.getCards().get(2));
		hand2.add(board.getCards().get(7));
		hand2.add(board.getCards().get(17));
		tempPlayers.get(1).setMyCards(hand2);
		
		//hand2.clear();
		hand3.add(board.getCards().get(3));
		hand3.add(board.getCards().get(8));
		hand3.add(board.getCards().get(18));
		tempPlayers.get(2).setMyCards(hand3);
		
		//no players can disprove
		Card tempCard = new Card();
		tempCard = board.handleSuggestion(sol, p1, tempPlayers);
		//System.out.println(tempCard.getCardName());
		assertEquals(tempCard, (null));
		
		//only accusing player can disprove
		tempPlayers.get(0).getMyCards().add((board.getCards().get(0)));
		tempCard = board.handleSuggestion(sol, tempPlayers.get(0), tempPlayers);
		//System.out.println(tempCard.getCardName());
		assertEquals(tempCard, null);
		
		//only human can disprove, human is not the accuser
		tempCard = board.handleSuggestion(sol, p2, tempPlayers);
		assertEquals(tempCard.getCardName(), "Colonel Mustard");
		
		//only human can disprove, human is the accuser
		tempCard = board.handleSuggestion(sol, p1, tempPlayers);
		assertEquals(tempCard, null);
		
		//two players can disprove, correct player (next in list) returns answer
		tempPlayers.get(0).getMyCards().remove(board.getCards().get(0));
		for(int i = 0 ; i < tempPlayers.get(0).getMyCards().size(); i++) {
				System.out.println("player 1 " + i + " " + tempPlayers.get(0).getMyCards().get(i).getCardName());
		}
		tempPlayers.get(1).getMyCards().add((board.getCards().get(14)));
		tempPlayers.get(2).getMyCards().add((board.getCards().get(10)));
		
		for(int i = 0 ; i < tempPlayers.get(1).getMyCards().size(); i++) {
			System.out.println("player 2 " + i + " " + tempPlayers.get(1).getMyCards().get(i).getCardName());
		}
		
		for(int i = 0 ; i < tempPlayers.get(2).getMyCards().size(); i++) {
			System.out.println("player 3 " + i + " " + tempPlayers.get(2).getMyCards().get(i).getCardName());
		}
		
		
		tempCard = board.handleSuggestion(sol, tempPlayers.get(0), tempPlayers);
		assertEquals(tempCard.getCardName(), "Game Room");
		
		//players are queried in order (human and another player can disprove, ensure
		//other player who is next in list returns answer
		Player tempPlayer = new Player();
		tempPlayer = p1;
		tempPlayers.set(0, p2);
		tempPlayers.set(1, tempPlayer);
		tempPlayers.get(0).getMyCards().remove(3);
		tempCard = board.handleSuggestion(sol, p2, tempPlayers);
		assertEquals(tempCard.getCardName(), "Wrench");
		
		
		
		
		
		
	}
}
