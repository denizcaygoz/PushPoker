package service
import entity.PokerHand
import kotlin.test.*

/**
 * GameServiceTest class is to test GameService class.
 * In GameService class there are two functions needs to be tested,
 * which are startNewGameTest() and evaluateTest()
 */
class GameServiceTest {

    private lateinit var game: RootService

    /**
     * calling startNewGame() function.
     */
    @BeforeTest
    fun setUp() {
        game = RootService()
        game.gameService.startNewGame(mutableListOf("p1","p2"),7)


    }

    /**
     * This is a comprehensive test for startNewGame()
     * validation of 2 players is tested.
     * validation of 2 rounds is tested.
     * tested if players have proper hand cards.
     * validation of 7 rounds is tested.
     * tested if drawStack has proper amount of cards after distribution of table cards and player's hand cards.
     */
    @Test
    fun startNewGameTest() {
        val game = requireNotNull(game.currentGame) { "currentGame must not be null" }


        // Check for correct number of players
        assertEquals(2, game.players.size, "Game should have the correct number of players.")

        // Check for proper initial card distribution to player hand cards.
        game.players.forEach { player ->
            assertEquals(2, player.hiddenCards.size, "Each player should have 2 hidden cards.")
            assertEquals(3, player.openCards.size, "Each player should have 3 open cards.")
        }

        // Check valid rounds setup
        assertEquals(7, game.roundsLeft, "Game should be initialized with the correct number of rounds.")



        //Check the size of the deck after the cards are given to each player from drawStack.
        val expectedDeckSize = 52 - (game.players.size * (2 + 3)) - 3
        assertEquals(expectedDeckSize, game.drawStack?.cards?.size,
            "Draw stack should be reduced after the initial card distribution.")
    }

    /**
     * To test the game with 3 and 4 players. (starting a game with 2 players is already tested in startNewGameTest())
     */
    @Test
    fun startNewGameTestWithValidPlayerNumbers() {
        game = RootService()
        game.gameService.startNewGame(mutableListOf("p1","p2","p3"),7)

        // Checking for correct number of players
        assertEquals(3, game.currentGame!!.players.size, "Number of players in the game is valid.")

        game.gameService.startNewGame(mutableListOf("p1","p2","p3","p4"),7)

        // Checking for correct number of players
        assertEquals(4, game.currentGame!!.players.size, "Number of players in the game is valid.")
    }
    /**
     * To test the game with invalid player numbers.
     */
    @Test
    fun startNewGameTestWithInvalidPlayerNumbers() {
        val gameService = RootService().gameService

        // There is only 1 player in the game. Error is expected.
        assertFailsWith<IllegalStateException> {
            gameService.startNewGame(listOf("p1"), 5)
        }.also { exception ->
            assertEquals("minimum 2 players", exception.message)
        }

        // There are 5 players in the game. Error is expected.
        assertFailsWith<IllegalStateException> {
            gameService.startNewGame(listOf("p1", "p2", "p3", "p4", "p5"), 5)
        }.also { exception ->
            assertEquals("maximum 4 players", exception.message)
        }
    }

    /**
     * Test the game with valid number of Rounds which is between 2 and 7.
     */
    @Test
    fun startNewGameTestWithValidRoundNumbers() {
        game = RootService()
        game.gameService.startNewGame(mutableListOf("p1","p2","p3","p4"),2)
        // Checking for correct number of rounds
        assertEquals(2, game.currentGame!!.roundsLeft, "Round value is valid.")

        game.gameService.startNewGame(mutableListOf("p1","p2","p3","p4"),3)
        // Checking for correct number of rounds
        assertEquals(3, game.currentGame!!.roundsLeft, "Round value is valid.")

        game.gameService.startNewGame(mutableListOf("p1","p2","p3","p4"),4)
        // Checking for correct number of rounds
        assertEquals(4, game.currentGame!!.roundsLeft, "Round value is valid.")

        game.gameService.startNewGame(mutableListOf("p1","p2","p3","p4"),5)
        // Checking for correct number of rounds
        assertEquals(5, game.currentGame!!.roundsLeft, "Round value is valid.")

        game.gameService.startNewGame(mutableListOf("p1","p2","p3","p4"),6)
        // Checking for correct number of rounds
        assertEquals(6, game.currentGame!!.roundsLeft, "Round value is valid.")

        game.gameService.startNewGame(mutableListOf("p1","p2","p3","p4"),7)
        // Checking for correct number of rounds
        assertEquals(7, game.currentGame!!.roundsLeft, "Round value is valid.")
    }

    /**
     * Test the game invalid number of rounds.
     */
    @Test
    fun startNewGameTestWithInValidRoundNumbers() {
        val gameService = RootService().gameService

        //To test rounds with a value less than 2
        assertFailsWith<IllegalStateException> {
            gameService.startNewGame(listOf("p1", "p2"), 1)
        }.also { exception ->
            assertEquals("minimum 2 rounds", exception.message)
        }

        //To test rounds with a value more than 7
        assertFailsWith<IllegalStateException> {
            gameService.startNewGame(listOf("p1", "p2", "p3", "p4"), 8)
        }.also { exception ->
            assertEquals("maximum 7 rounds", exception.message)
        }
    }

    /**
     * evaluateGameTest tests if evaluateGame() function sort the player
     * in a correct way by hand values(PokerHand).
     */
    @Test
    fun evaluateGameTest() {

        val rootService = RootService()
        val gameService = GameService(rootService)

        // First test
        gameService.startNewGame(listOf("p1", "p2", "p3", "p4"), 5)

        //To test if the game is initialized.
        assertNotEquals(null,rootService.currentGame,"is excepted the game is not null and initialized.")

        // setting up hand results of players for the first test
        rootService.currentGame!!.players[0].handResult = PokerHand.STRAIGHT // p1
        rootService.currentGame!!.players[1].handResult = PokerHand.NONE // p2
        rootService.currentGame!!.players[2].handResult = PokerHand.FULL_HOUSE // p3
        rootService.currentGame!!.players[3].handResult = PokerHand.TWO_PAIR // p4

        // Evaluate the game and sort players
        var sortedPlayers = gameService.evaluateGame()

        assertEquals(PokerHand.FULL_HOUSE, sortedPlayers[0].handResult, "p3 should be first with FULL_HOUSE.")
        assertEquals(PokerHand.STRAIGHT, sortedPlayers[1].handResult, "p1 should be second with STRAIGHT.")
        assertEquals(PokerHand.TWO_PAIR, sortedPlayers[2].handResult, "p4 should be third with TWO_PAIR.")
        assertEquals(PokerHand.NONE, sortedPlayers[3].handResult, "p2 should be last with NONE.")

        // Second test
        gameService.startNewGame(listOf("p1", "p2", "p3", "p4"), 4)

        // setting up hand results of players for the second test
        rootService.currentGame!!.players[0].handResult = PokerHand.FOUR_OF_A_KIND // p1
        rootService.currentGame!!.players[1].handResult = PokerHand.THREE_OF_A_KIND // p2
        rootService.currentGame!!.players[2].handResult = PokerHand.FLUSH // p3
        rootService.currentGame!!.players[3].handResult = PokerHand.STRAIGHT_FLUSH // p4

        // Evaluating the game and sorting players
        sortedPlayers = gameService.evaluateGame()

        assertEquals(PokerHand.STRAIGHT_FLUSH, sortedPlayers[0].handResult, "p4 should be first with STRAIGHT_FLUSH.")
        assertEquals(PokerHand.FOUR_OF_A_KIND, sortedPlayers[1].handResult, "p1 should be second with FOUR_OF_A_KIND.")
        assertEquals(PokerHand.FLUSH, sortedPlayers[2].handResult, "p3 should be third with FLUSH.")
        assertEquals(PokerHand.THREE_OF_A_KIND, sortedPlayers[3].handResult, "p2 should be last with THREE_OF_A_KIND.")
    }

    @Test
    fun testStartNewGameCallsRefreshAfterStartNewGame() {
        val rootService = RootService()
        val gameService = GameService(rootService)
        val testRefreshable = TestRefreshable()

        gameService.addRefreshable(testRefreshable)
        gameService.startNewGame(listOf("Alice", "Bob"), 5)

        assertTrue(testRefreshable.refreshAfterStartNewGame, "refreshAfterStartNewGame was not called.")
    }
}