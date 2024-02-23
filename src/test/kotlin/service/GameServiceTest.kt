package service
import entity.PokerHand
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GameServiceTest {

    private lateinit var game: RootService
    @BeforeTest
    fun setUp() {
        game = RootService()
        game.gameService.startNewGame(mutableListOf("p1","p2"),7)
        game.currentGame?.players?.get(0)?.handResult = PokerHand.ROYAL_FLUSH
        game.currentGame?.players?.get(1)?.handResult = PokerHand.STRAIGHT_FLUSH

    }

    @Test
    fun startNewGameTest() {
        val game = requireNotNull(game.currentGame) { "currentGame must not be null" }


        // Check for correct number of players
        assertEquals(2, game.players.size, "Game should have the correct number of players.")

        // Check for proper initial card distribution
        game.players.forEach { player ->
            assertEquals(2, player.hiddenCards.size, "Each player should have 2 hidden cards.")
            assertEquals(3, player.openCards.size, "Each player should have 3 open cards.")
        }

        // Check valid rounds setup
        assertEquals(7, game.roundsLeft, "Game should be initialized with the correct number of rounds.")



        // Optional: Check deck contains 52 cards
        val expectedDeckSize = 52 - (game.players.size * (2 + 3)) - 3
        assertEquals(expectedDeckSize, game.drawStack?.cards?.size, "Draw stack should be reduced appropriately by the initial card distribution.")
    }

    @Test
    fun evaluateGameTest() {

        val sortedPlayers = game.gameService.evaluateGame()


        assertEquals(PokerHand.ROYAL_FLUSH, sortedPlayers.first().handResult, "The first player should have a ROYAL_FLUSH.")
        assertEquals(PokerHand.STRAIGHT_FLUSH, sortedPlayers.last().handResult, "The last player should have a STRAIGHT_FLUSH.")


        assertEquals(2, sortedPlayers.size, "There should be exactly 2 players in the sorted list.")
    }


}