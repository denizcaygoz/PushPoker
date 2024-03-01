package service
import entity.*
import kotlin.test.*

/**
 * PlayerActionServiceTest is to test PlayerActionService class.
 */
class PlayerActionServiceTest {

    private lateinit var game: RootService
    private lateinit var testRefreshable: TestRefreshable

    /**
     * starting a new game.
     */
    @BeforeTest
    fun setUp() {
        game = RootService()
        game.gameService.startNewGame(mutableListOf("p1","p2","p3"),7)
        game.currentGame?.currentPlayer = 0

    }

    /**
     * tested if:
     * game is successfully initialized.
     * refreshAfterStartTurn is called in startTurn() function.
     */
    @Test
    fun startTurnTest() {
        testRefreshable = TestRefreshable()
        game.addRefreshable(testRefreshable)
        game.playerActionService.startTurn()

        assertNotEquals(null,game.currentGame,"game is excepted to be initialized.")

        assertEquals(true,testRefreshable.refreshAfterStartTurn)
    }


    /**
     * pushLeftTest tests the pushLeft() function.
     */
    @Test
    fun pushLeftTest() {
        testRefreshable = TestRefreshable()
        game.addRefreshable(testRefreshable)
        //setting up cards of the drawStack
        val cardStack1 = CardStack().apply {
            cards = mutableListOf(
                Card(CardSuit.HEARTS, CardValue.TEN),
                Card(CardSuit.DIAMONDS, CardValue.JACK),
                Card(CardSuit.CLUBS, CardValue.QUEEN)
            )
        }
        //setting up cards of the tableCards.
        val cardStack2: MutableList<Card> = mutableListOf(
            Card(CardSuit.SPADES, CardValue.ACE),
            Card(CardSuit.HEARTS, CardValue.TWO),
            Card(CardSuit.DIAMONDS, CardValue.THREE)
        )

        //assigning cardStack1 to drawStack
        game.currentGame?.drawStack = cardStack1
        //assigning cardStack2 to tableCards
        game.currentGame?.tableCards = cardStack2

        //to check the discard stack size before pushLeft action
        val initialDiscardStackSize = game.currentGame?.discardStack?.cards?.size ?: 0

        //applying pushLeft action.
        game.playerActionService.pushLeft()

        //initializing discardStack
        val discardStack = game.currentGame?.discardStack?.cards ?: mutableListOf()
        //initializing tableCards
        val tableCards = game.currentGame?.tableCards ?: mutableListOf()

        //to check the discard stack size after pushLeft action
        assertEquals(initialDiscardStackSize + 1, discardStack.size)

        //to check the table card on the most left went to the discardStack
        assertEquals(CardValue.ACE, discardStack.last().cardValue)
        assertEquals(CardSuit.SPADES, discardStack.last().cardsuit)

        //to check if cards are shifted to the left
        assertEquals(CardValue.TWO, tableCards[0].cardValue)
        assertEquals(CardValue.THREE, tableCards[1].cardValue)
        //to ensure you check the expected card after pushLeft operation
        assertEquals(CardValue.TEN, tableCards[2].cardValue)

        //testing refreshing functions.
        assertEquals(true,testRefreshable.refreshTableCards)
        assertEquals(true,testRefreshable.refreshDiscardStack)

    }

    /**
     * pushRightTest tests the pushRight() function.
     */
    @Test
    fun pushRightTest() {
        testRefreshable = TestRefreshable()
        game.addRefreshable(testRefreshable)
        //setting up cards of the drawStack
        val cardStack1 = CardStack().apply {
            cards = mutableListOf(
                Card(CardSuit.HEARTS, CardValue.TEN),
                Card(CardSuit.DIAMONDS, CardValue.JACK),
                Card(CardSuit.CLUBS, CardValue.QUEEN)
            )
        }
        //setting up cards of the tableCards.
        val cardStack2: MutableList<Card> = mutableListOf(
            Card(CardSuit.SPADES, CardValue.ACE),
            Card(CardSuit.HEARTS, CardValue.TWO),
            Card(CardSuit.DIAMONDS, CardValue.THREE)
        )

        //assigning cardStack1 to drawStack
        game.currentGame?.drawStack = cardStack1
        //assigning cardStack2 to tableCards
        game.currentGame?.tableCards = cardStack2

        //to check the discard stack size before pushRight action
        val initialDiscardStackSize = game.currentGame?.discardStack?.cards?.size ?: 0

        //Applying pushRight action
        game.playerActionService.pushRight()

        //initializing discardStack
        val discardStack = game.currentGame?.discardStack?.cards ?: mutableListOf()
        //initializing tableCards
        val tableCards = game.currentGame?.tableCards ?: mutableListOf()

        //Expected the discard stack size to increase by one
        assertEquals(initialDiscardStackSize + 1, discardStack.size)
        // The last card of discard stack should now be the last card from the original tableCards
        assertEquals(CardValue.THREE, discardStack.last().cardValue)
        assertEquals(CardSuit.DIAMONDS, discardStack.last().cardsuit)


        // Checking the new state of tableCards after pushRight action
        // The first card of tableCards should now be the first card from the original drawStack
        assertEquals(CardValue.TEN, tableCards[0].cardValue)
        assertEquals(CardSuit.HEARTS, tableCards[0].cardsuit)
        // The rest of tableCards should shift right accordingly
        assertEquals(CardValue.ACE, tableCards[1].cardValue)
        assertEquals(CardValue.TWO, tableCards[2].cardValue)

        //testing refreshing functions.
        assertEquals(true,testRefreshable.refreshTableCards)
        assertEquals(true,testRefreshable.refreshDiscardStack)
    }

    /**
     * swapAllTest tests swapAll function.
     */
    @Test
    fun swapAllTest() {
        testRefreshable = TestRefreshable()
        game.addRefreshable(testRefreshable)
        val currentPlayer = game.currentGame?.players?.get(game.currentGame!!.currentPlayer)

        //setting up the cards of the table
        val cardStackForTable = mutableListOf(
                Card(CardSuit.HEARTS, CardValue.TEN),
                Card(CardSuit.DIAMONDS, CardValue.JACK),
                Card(CardSuit.CLUBS, CardValue.QUEEN)
            )
        //setting up the cards of the player
        val cardStackForPlayer: MutableList<Card> = mutableListOf(
            Card(CardSuit.SPADES, CardValue.ACE),
            Card(CardSuit.HEARTS, CardValue.TWO),
            Card(CardSuit.DIAMONDS, CardValue.THREE)
        )

        game.currentGame?.tableCards = cardStackForTable
        currentPlayer?.openCards = cardStackForPlayer

        //assigning initial table cards.
        val initialTableCards = mutableListOf(
            Card(CardSuit.HEARTS, CardValue.TEN),
            Card(CardSuit.DIAMONDS, CardValue.JACK),
            Card(CardSuit.CLUBS, CardValue.QUEEN)
        )
        //assigning initial player cards.
        val initialPlayerCards = mutableListOf(
            Card(CardSuit.SPADES, CardValue.ACE),
            Card(CardSuit.HEARTS, CardValue.TWO),
            Card(CardSuit.DIAMONDS, CardValue.THREE)
        )


        currentPlayer?.hasPushed = true
        //initializing table Cards.
        val tableCards = game.currentGame?.tableCards
        //initializing open cards of the player.
        val playerCards = currentPlayer?.openCards

        //Executing swapAll action
        game.playerActionService.swapAll()

        // to ensure the specific cards are correctly swapped
        for (i in playerCards!!.indices) {
            assertEquals(initialPlayerCards[i].cardValue, game.currentGame?.tableCards?.get(i)?.cardValue)
            assertEquals(initialPlayerCards[i].cardsuit, game.currentGame?.tableCards?.get(i)?.cardsuit)
        }

        for (i in tableCards!!.indices) {
            assertEquals(initialTableCards[i].cardValue, currentPlayer.openCards[i].cardValue)
            assertEquals(initialTableCards[i].cardsuit, currentPlayer.openCards[i].cardsuit)
        }

        //testing refreshing functions.
        assertEquals(true,testRefreshable.refreshTableCards)
        assertEquals(true,testRefreshable.refreshPlayerCards)
        assertEquals(true,testRefreshable.refreshHandValue)
    }

    /**
     * Tests that an exception is thrown if `swapAll` is called with an invalid current player index.
     */
    @Test
    fun testSwapAllWithInvalidCurrentPlayer() {
        game.currentGame?.currentPlayer = -1
        // Attempt to call swapAll, expecting an IllegalStateException
        assertFailsWith<IllegalStateException> { game.playerActionService.swapAll() }
    }

    /**
     * Tests that an exception is thrown if `swapAll` is called before the player has pushed.
     */
    @Test
    fun testSwapAllWithoutPush() {
        val currentPlayer = game.currentGame?.players?.first()
        currentPlayer?.hasPushed = false
        assertFailsWith<IllegalStateException> { game.playerActionService.swapAll() }
    }

    /**
     * Tests that an exception is thrown if `swapAll` is called after the player has already swapped.
     */
    @Test
    fun testSwapAllAfterSwap() {
        val currentPlayer = game.currentGame?.players?.first()
        currentPlayer?.hasSwapped = true
        assertFailsWith<IllegalStateException> { game.playerActionService.swapAll() }
    }

    /**
     * SwapOneTest tests the SwapOne function
     */
    @Test
    fun swapOneTest() {
        testRefreshable = TestRefreshable()
        game.addRefreshable(testRefreshable)
        val currentPlayer = game.currentGame?.players?.get(game.currentGame!!.currentPlayer)

        //setting up the cards of the table
        val cardStack1 = mutableListOf(
            Card(CardSuit.HEARTS, CardValue.TEN),
            Card(CardSuit.DIAMONDS, CardValue.JACK),
            Card(CardSuit.CLUBS, CardValue.QUEEN)
        )

        //setting up the cards of the player
        val cardStack2: MutableList<Card> = mutableListOf(
            Card(CardSuit.SPADES, CardValue.ACE),
            Card(CardSuit.HEARTS, CardValue.TWO),
            Card(CardSuit.DIAMONDS, CardValue.THREE)
        )

        game.currentGame?.tableCards = cardStack1
        currentPlayer?.openCards = cardStack2

        currentPlayer?.hasPushed = true

        //to execute swapOne function
        game.playerActionService.swapOne(0,1)

        //to validate the swap operation
        assertEquals(CardValue.JACK, currentPlayer?.openCards?.get(0)?.cardValue)
        assertEquals(CardSuit.DIAMONDS, currentPlayer?.openCards?.get(0)?.cardsuit)

        assertEquals(CardValue.ACE, game.currentGame?.tableCards?.get(1)?.cardValue)
        assertEquals(CardSuit.SPADES, game.currentGame?.tableCards?.get(1)?.cardsuit)

        //to ensure the other cards remain unchanged
        assertEquals(CardValue.TWO, currentPlayer?.openCards?.get(1)?.cardValue)
        assertEquals(CardValue.THREE, currentPlayer?.openCards?.get(2)?.cardValue)
        assertEquals(CardValue.TEN, game.currentGame?.tableCards?.get(0)?.cardValue)
        assertEquals(CardValue.QUEEN, game.currentGame?.tableCards?.get(2)?.cardValue)

        //testing refreshing functions.
        assertEquals(true,testRefreshable.refreshTableCards)
        assertEquals(true,testRefreshable.refreshPlayerCards)
        assertEquals(true,testRefreshable.refreshHandValue)
    }
    /**
     * Tests that an exception is thrown if `swapOne` is called with an invalid current player index.
     */
    @Test
    fun testSwapOneWithInvalidCurrentPlayer() {
        game.currentGame?.currentPlayer = -1
        // Attempt to call swapOne, expecting an IllegalStateException for invalid current player
        assertFailsWith<IllegalStateException> {
            game.playerActionService.swapOne(0, 1)
        }
    }

    /**
     * Tests that an exception is thrown if `swapOne` is called before the player has pushed.
     */
    @Test
    fun testSwapOneWithoutPush() {
        val currentPlayer = game.currentGame?.players?.first()
        currentPlayer?.hasPushed = false
        // Attempt to call swapOne, expecting an IllegalStateException for not having pushed
        assertFailsWith<IllegalStateException> {
            game.playerActionService.swapOne(0, 1)
        }
    }

    /**
     * Tests that an exception is thrown if `swapOne` is called after the player has already swapped.
     */
    @Test
    fun testSwapOneAfterSwap() {
        val currentPlayer = game.currentGame?.players?.first()
        currentPlayer?.hasSwapped = true
        // Attempt to call swapOne, expecting an IllegalStateException for already having swapped
        assertFailsWith<IllegalStateException> {
            game.playerActionService.swapOne(0, 1)
        }
    }

    /**
     * Tests that an exception is thrown if `swapOne` is called with out-of-bounds card indices.
     */
    @Test
    fun testSwapOneWithOutOfBoundsIndices() {
        // Assuming the currentPlayer is valid and has pushed but not swapped.
        val currentPlayer = game.currentGame?.players?.first()
        currentPlayer?.hasPushed = true
        currentPlayer?.hasSwapped = false

        // Attempt to call swapOne with invalid indices, expecting an IllegalStateException for out-of-bounds indices
        assertFailsWith<IllegalStateException> {
            game.playerActionService.swapOne(-1, 5) // Out-of-bounds indices
        }
    }

    /**
     * EndTurnTest tests the endTurn function.
     * For the case EndTurn: player has finished his turn but nor the game or the round
     */
    @Test
    fun endTurnTest() {
        testRefreshable = TestRefreshable()
        game.addRefreshable(testRefreshable)
        val initialCurrentPlayerIndex = game.currentGame?.currentPlayer ?: 0
        val initialRoundsLeft = game.currentGame?.roundsLeft ?: 3

        // to execute endTurn function
        game.playerActionService.endTurn()

        //testing refreshing functions.
        assertEquals(true,testRefreshable.refreshAfterEndTurn)

        //To test if game exists.
        assertNotEquals(null,game.currentGame,"The game is not null")

        //to check if the hasPushed and hasSwapped set to false.
        assertEquals(false,game.currentGame!!.players[game.currentGame!!.currentPlayer].hasPushed)
        assertEquals(false,game.currentGame!!.players[game.currentGame!!.currentPlayer].hasSwapped)



        //testing to transition to the next player
        val newCurrentPlayerIndex = game.currentGame?.currentPlayer
        assertEquals((initialCurrentPlayerIndex + 1) , newCurrentPlayerIndex, "Should move to the next player.")
        //testing roundsLeft.
        assertEquals(initialRoundsLeft, game.currentGame?.roundsLeft,
            "Rounds left should remain the same if not last player's turn.")
    }
    /**
     * EndTurnTest tests the endTurn function.
     * For the case endRound: player has finished and the round also has finished but not the game.
     */
    @Test
    fun endTurnTest2() {
        testRefreshable = TestRefreshable()
        game.addRefreshable(testRefreshable)
        //the last player suppose to have the turn.
        game.currentGame?.currentPlayer = (game.currentGame!!.players.size - 1)
        //
        val initialRoundsLeft = game.currentGame?.roundsLeft ?: 4

        // to execute endTurn function
        game.playerActionService.endTurn()

        //testing refreshing functions.
        assertEquals(true,testRefreshable.refreshAfterEndRound)

        //to check if the hasPushed and hasSwapped set to false.
        assertEquals(false,game.currentGame!!.players[game.currentGame!!.currentPlayer].hasPushed)
        assertEquals(false,game.currentGame!!.players[game.currentGame!!.currentPlayer].hasSwapped)

        //testing to transition to the next player
        val newCurrentPlayerIndex = game.currentGame?.currentPlayer
        assertEquals(0 , newCurrentPlayerIndex, "Should move to the next player.")
        //testing roundsLeft.
        assertEquals(initialRoundsLeft - 1, game.currentGame?.roundsLeft, "Rounds left should be decremented by 1.")
    }

    /**
     * EndTurnTest tests the endTurn function.
     * For the case endGame: player has finished and the round also has finished and the game as well.
     */
    @Test
    fun endTurnTest3() {
        testRefreshable = TestRefreshable()
        game.addRefreshable(testRefreshable)
        //the last player suppose to have the turn.
        game.currentGame?.currentPlayer = (game.currentGame!!.players.size - 1)
        //to ensure that the last round is being played.
        game.currentGame?.roundsLeft = 1

        // to execute endTurn function
        game.playerActionService.endTurn()

        //testing refreshing functions.
        assertEquals(true,testRefreshable.refreshAfterEndGame)

        //to check if the hasPushed and hasSwapped set to false.
        assertEquals(false,game.currentGame!!.players[game.currentGame!!.currentPlayer].hasPushed)
        assertEquals(false,game.currentGame!!.players[game.currentGame!!.currentPlayer].hasSwapped)

        //testing to transition to the next player
        val newCurrentPlayerIndex = game.currentGame?.currentPlayer
        assertEquals(0 , newCurrentPlayerIndex, "Should move to the next player.")
        //testing roundsLeft.
        assertEquals(0, game.currentGame?.roundsLeft, "Rounds left should be decremented by 1.")
    }

    /**
     * To test evaluateHand function.
     * Every possible possibility is tested, where player has every type of PokerHand values.
     */

    @Test
    fun evaluateHandTest() {
        testRefreshable = TestRefreshable()
        game.addRefreshable(testRefreshable)
        val currentPlayer = game.currentGame?.players?.get(game.currentGame!!.currentPlayer)

        //Test 1: To test royal Flush

        //setting up player open hand
        var playerOpenHand: MutableList<Card> = mutableListOf(
            Card(CardSuit.HEARTS, CardValue.ACE),
            Card(CardSuit.HEARTS, CardValue.JACK),
            Card(CardSuit.HEARTS, CardValue.QUEEN)
        )
        //setting up player hidden hand
        var playerHiddenHand: MutableList<Card> = mutableListOf(
            Card(CardSuit.HEARTS, CardValue.KING),
            Card(CardSuit.HEARTS, CardValue.TEN),
        )

        currentPlayer?.openCards = playerOpenHand
        currentPlayer?.hiddenCards = playerHiddenHand

        game.playerActionService.evaluateHand(currentPlayer!!)

        //testing refreshing functions.
        assertEquals(true,testRefreshable.refreshHandValue)

        assertEquals(PokerHand.ROYAL_FLUSH, currentPlayer.handResult, "Royal Flush expected.")


        //Test 2: To test Straight Flush

        playerOpenHand = mutableListOf(
            Card(CardSuit.CLUBS, CardValue.THREE),
            Card(CardSuit.CLUBS, CardValue.FOUR),
            Card(CardSuit.CLUBS, CardValue.SIX)
        )
        //setting up player hidden hand
        playerHiddenHand = mutableListOf(
            Card(CardSuit.CLUBS, CardValue.FIVE),
            Card(CardSuit.CLUBS, CardValue.SEVEN),
        )
        currentPlayer.openCards = playerOpenHand
        currentPlayer.hiddenCards = playerHiddenHand

        game.playerActionService.evaluateHand(currentPlayer)


        assertEquals(PokerHand.STRAIGHT_FLUSH, currentPlayer.handResult, "Straight Flush expected.")

        //Test 3: To test Four Of A Kind

        playerOpenHand = mutableListOf(
            Card(CardSuit.CLUBS, CardValue.EIGHT),
            Card(CardSuit.DIAMONDS, CardValue.EIGHT),
            Card(CardSuit.SPADES, CardValue.EIGHT)
        )
        //setting up player hidden hand
        playerHiddenHand = mutableListOf(
            Card(CardSuit.CLUBS, CardValue.EIGHT),
            Card(CardSuit.CLUBS, CardValue.SEVEN),
        )
        currentPlayer.openCards = playerOpenHand
        currentPlayer.hiddenCards = playerHiddenHand

        game.playerActionService.evaluateHand(currentPlayer)


        assertEquals(PokerHand.FOUR_OF_A_KIND, currentPlayer.handResult, "Four Of A Kind expected.")

        //Test 3.1: To test Four Of A Kind in an alternative situation where we have 5 same card value

        playerOpenHand = mutableListOf(
            Card(CardSuit.CLUBS, CardValue.EIGHT),
            Card(CardSuit.DIAMONDS, CardValue.EIGHT),
            Card(CardSuit.SPADES, CardValue.EIGHT)
        )
        //setting up player hidden hand
        playerHiddenHand = mutableListOf(
            Card(CardSuit.CLUBS, CardValue.EIGHT),
            Card(CardSuit.CLUBS, CardValue.EIGHT),
        )
        currentPlayer.openCards = playerOpenHand
        currentPlayer.hiddenCards = playerHiddenHand

        game.playerActionService.evaluateHand(currentPlayer)


        assertEquals(PokerHand.FOUR_OF_A_KIND, currentPlayer.handResult, "Four Of A Kind expected.")

        //Test 3.2: To test Full House

        playerOpenHand = mutableListOf(
            Card(CardSuit.CLUBS, CardValue.ACE),
            Card(CardSuit.DIAMONDS, CardValue.FIVE),
            Card(CardSuit.SPADES, CardValue.FIVE)
        )
        //setting up player hidden hand
        playerHiddenHand = mutableListOf(
            Card(CardSuit.CLUBS, CardValue.ACE),
            Card(CardSuit.DIAMONDS, CardValue.ACE),
        )
        currentPlayer.openCards = playerOpenHand
        currentPlayer.hiddenCards = playerHiddenHand

        game.playerActionService.evaluateHand(currentPlayer)


        assertEquals(PokerHand.FULL_HOUSE, currentPlayer.handResult, "Full House expected.")

        //Test 4: To test Full House

        playerOpenHand = mutableListOf(
            Card(CardSuit.CLUBS, CardValue.ACE),
            Card(CardSuit.DIAMONDS, CardValue.FIVE),
            Card(CardSuit.SPADES, CardValue.FIVE)
        )
        //setting up player hidden hand
        playerHiddenHand = mutableListOf(
            Card(CardSuit.CLUBS, CardValue.ACE),
            Card(CardSuit.DIAMONDS, CardValue.ACE),
        )
        currentPlayer.openCards = playerOpenHand
        currentPlayer.hiddenCards = playerHiddenHand

        game.playerActionService.evaluateHand(currentPlayer)


        assertEquals(PokerHand.FULL_HOUSE, currentPlayer.handResult, "Full House expected.")

        //Test 5: To test Flush

        playerOpenHand = mutableListOf(
            Card(CardSuit.CLUBS, CardValue.FIVE),
            Card(CardSuit.CLUBS, CardValue.ACE),
            Card(CardSuit.CLUBS, CardValue.FOUR)
        )
        //setting up player hidden hand
        playerHiddenHand = mutableListOf(
            Card(CardSuit.CLUBS, CardValue.QUEEN),
            Card(CardSuit.CLUBS, CardValue.QUEEN),
        )
        currentPlayer.openCards = playerOpenHand
        currentPlayer.hiddenCards = playerHiddenHand

        game.playerActionService.evaluateHand(currentPlayer)


        assertEquals(PokerHand.FLUSH, currentPlayer.handResult, "Flush expected.")

        //Test 6: To test straight

        playerOpenHand = mutableListOf(
            Card(CardSuit.CLUBS, CardValue.FIVE),
            Card(CardSuit.DIAMONDS, CardValue.SIX),
            Card(CardSuit.HEARTS, CardValue.SEVEN)
        )
        //setting up player hidden hand
        playerHiddenHand = mutableListOf(
            Card(CardSuit.CLUBS, CardValue.EIGHT),
            Card(CardSuit.SPADES, CardValue.NINE),
        )
        currentPlayer.openCards = playerOpenHand
        currentPlayer.hiddenCards = playerHiddenHand

        game.playerActionService.evaluateHand(currentPlayer)


        assertEquals(PokerHand.STRAIGHT, currentPlayer.handResult, "Straight expected.")

        //Test 7: To test three of a kind

        playerOpenHand = mutableListOf(
            Card(CardSuit.CLUBS, CardValue.FIVE),
            Card(CardSuit.DIAMONDS, CardValue.SIX),
            Card(CardSuit.HEARTS, CardValue.SIX)
        )
        //setting up player hidden hand
        playerHiddenHand = mutableListOf(
            Card(CardSuit.CLUBS, CardValue.EIGHT),
            Card(CardSuit.SPADES, CardValue.SIX),
        )
        currentPlayer.openCards = playerOpenHand
        currentPlayer.hiddenCards = playerHiddenHand

        game.playerActionService.evaluateHand(currentPlayer)


        assertEquals(PokerHand.THREE_OF_A_KIND, currentPlayer.handResult, "Three of a kind expected.")

        //Test 8: To test three of a kind

        playerOpenHand = mutableListOf(
            Card(CardSuit.CLUBS, CardValue.FIVE),
            Card(CardSuit.DIAMONDS, CardValue.SIX),
            Card(CardSuit.HEARTS, CardValue.SIX)
        )
        //setting up player hidden hand
        playerHiddenHand = mutableListOf(
            Card(CardSuit.CLUBS, CardValue.EIGHT),
            Card(CardSuit.SPADES, CardValue.SIX),
        )
        currentPlayer.openCards = playerOpenHand
        currentPlayer.hiddenCards = playerHiddenHand

        game.playerActionService.evaluateHand(currentPlayer)


        assertEquals(PokerHand.THREE_OF_A_KIND, currentPlayer.handResult, "Three of a kind expected.")

        //Test 9: To test two pair

        playerOpenHand = mutableListOf(
            Card(CardSuit.CLUBS, CardValue.FIVE),
            Card(CardSuit.DIAMONDS, CardValue.ACE),
            Card(CardSuit.HEARTS, CardValue.EIGHT)
        )
        //setting up player hidden hand
        playerHiddenHand = mutableListOf(
            Card(CardSuit.CLUBS, CardValue.EIGHT),
            Card(CardSuit.SPADES, CardValue.ACE),
        )
        currentPlayer.openCards = playerOpenHand
        currentPlayer.hiddenCards = playerHiddenHand

        game.playerActionService.evaluateHand(currentPlayer)


        assertEquals(PokerHand.TWO_PAIR, currentPlayer.handResult, "Two Pair expected.")

        //Test 10: To test pair

        playerOpenHand = mutableListOf(
            Card(CardSuit.CLUBS, CardValue.FIVE),
            Card(CardSuit.DIAMONDS, CardValue.ACE),
            Card(CardSuit.HEARTS, CardValue.EIGHT)
        )
        //setting up player hidden hand
        playerHiddenHand = mutableListOf(
            Card(CardSuit.CLUBS, CardValue.SEVEN),
            Card(CardSuit.SPADES, CardValue.ACE),
        )
        currentPlayer.openCards = playerOpenHand
        currentPlayer.hiddenCards = playerHiddenHand

        game.playerActionService.evaluateHand(currentPlayer)


        assertEquals(PokerHand.PAIR, currentPlayer.handResult, "Pair expected.")

        //Test 11: To test None

        playerOpenHand = mutableListOf(
            Card(CardSuit.CLUBS, CardValue.FIVE),
            Card(CardSuit.DIAMONDS, CardValue.ACE),
            Card(CardSuit.HEARTS, CardValue.EIGHT)
        )
        //setting up player hidden hand
        playerHiddenHand = mutableListOf(
            Card(CardSuit.CLUBS, CardValue.SEVEN),
            Card(CardSuit.SPADES, CardValue.THREE),
        )
        currentPlayer.openCards = playerOpenHand
        currentPlayer.hiddenCards = playerHiddenHand

        game.playerActionService.evaluateHand(currentPlayer)


        assertEquals(PokerHand.NONE, currentPlayer.handResult, "None expected.")
    }
}