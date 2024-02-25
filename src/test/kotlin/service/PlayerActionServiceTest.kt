package service
import entity.*
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals


class PlayerActionServiceTest {

    private lateinit var game: RootService
    @BeforeTest
    fun setUp() {
        game = RootService()
        game.gameService.startNewGame(mutableListOf("p1","p2","p3"),7)
        game.currentGame?.currentPlayer = 0
    }


    @Test
    fun pushLeftTest() {
        val cardStack1 = CardStack().apply {
            cards = mutableListOf(
                Card(CardSuit.HEARTS, CardValue.TEN),
                Card(CardSuit.DIAMONDS, CardValue.JACK),
                Card(CardSuit.CLUBS, CardValue.QUEEN)
            )
        }

        val cardStack2: MutableList<Card> = mutableListOf(
            Card(CardSuit.SPADES, CardValue.ACE),
            Card(CardSuit.HEARTS, CardValue.TWO),
            Card(CardSuit.DIAMONDS, CardValue.THREE)
        )


        game.currentGame?.drawStack = cardStack1
        game.currentGame?.tableCards = cardStack2

        val initialDiscardStackSize = game.currentGame?.discardStack?.cards?.size ?: 0

        game.playerActionService.pushLeft()


        val discardStack = game.currentGame?.discardStack?.cards ?: mutableListOf()
        assertEquals(initialDiscardStackSize + 1, discardStack.size)
        assertEquals(CardValue.ACE, discardStack.last().cardValue)
        assertEquals(CardSuit.SPADES, discardStack.last().cardsuit)

        val tableCards = game.currentGame?.tableCards ?: mutableListOf()
        assertEquals(CardValue.TWO, tableCards[0].cardValue)
        assertEquals(CardValue.THREE, tableCards[1].cardValue)
        // Ensure you check the expected card after pushLeft operation
        assertEquals(CardValue.TEN, tableCards[2].cardValue)
    }


    @Test
    fun pushRightTest() {
        // Preparing card stacks similar to pushLeftTest but with a focus on pushRight functionality
        val cardStack1 = CardStack().apply {
            cards = mutableListOf(
                Card(CardSuit.HEARTS, CardValue.TEN),
                Card(CardSuit.DIAMONDS, CardValue.JACK),
                Card(CardSuit.CLUBS, CardValue.QUEEN)
            )
        }

        val cardStack2: MutableList<Card> = mutableListOf(
            Card(CardSuit.SPADES, CardValue.ACE),
            Card(CardSuit.HEARTS, CardValue.TWO),
            Card(CardSuit.DIAMONDS, CardValue.THREE)
        )

        // Set the card lists
        game.currentGame?.drawStack = cardStack1
        game.currentGame?.tableCards = cardStack2

        val initialDiscardStackSize = game.currentGame?.discardStack?.cards?.size ?: 0

        // Execute pushRight action
        game.playerActionService.pushRight()

        // Assertions to validate the state after pushRight
        val discardStack = game.currentGame?.discardStack?.cards ?: mutableListOf()
        // Expect the discard stack size to increase by one
        assertEquals(initialDiscardStackSize + 1, discardStack.size)
        // The last card of discard stack should now be the last card from the original tableCards
        assertEquals(CardValue.THREE, discardStack.last().cardValue)
        assertEquals(CardSuit.DIAMONDS, discardStack.last().cardsuit)

        val tableCards = game.currentGame?.tableCards ?: mutableListOf()
        // Check the new state of tableCards after pushRight operation
        // The first card of tableCards should now be the first card from the original drawStack
        assertEquals(CardValue.TEN, tableCards[0].cardValue)
        assertEquals(CardSuit.HEARTS, tableCards[0].cardsuit)
        // The rest of tableCards should shift right accordingly
        assertEquals(CardValue.ACE, tableCards[1].cardValue)
        assertEquals(CardValue.TWO, tableCards[2].cardValue)
    }


    @Test
    fun swapAllTest() {

        val currentPlayer = game.currentGame?.players?.get(game.currentGame!!.currentPlayer)

        //set the cards of the table
        val cardStack1 = mutableListOf(
                Card(CardSuit.HEARTS, CardValue.TEN),
                Card(CardSuit.DIAMONDS, CardValue.JACK),
                Card(CardSuit.CLUBS, CardValue.QUEEN)
            )

        //set the cards of the player
        val cardStack2: MutableList<Card> = mutableListOf(
            Card(CardSuit.SPADES, CardValue.ACE),
            Card(CardSuit.HEARTS, CardValue.TWO),
            Card(CardSuit.DIAMONDS, CardValue.THREE)
        )

        game.currentGame?.tableCards = cardStack1
        currentPlayer?.openCards = cardStack2

        currentPlayer?.hasPushed = true

        val tableCards = game.currentGame?.tableCards
        val playerCards = currentPlayer?.openCards

        // Execute swapAll action
        game.playerActionService.swapAll()

        //to validate the swap operation
        // The player's open cards should match the original table cards
        assertEquals(tableCards, currentPlayer?.openCards)

        // The table cards should  match the original player's open cards
        assertEquals(playerCards, game.currentGame?.tableCards)

        // to ensure the specific cards are correctly swapped
        for (i in playerCards!!.indices) {
            assertEquals(playerCards[i].cardValue, game.currentGame?.tableCards?.get(i)?.cardValue)
            assertEquals(playerCards[i].cardsuit, game.currentGame?.tableCards?.get(i)?.cardsuit)
        }

        for (i in tableCards!!.indices) {
            assertEquals(tableCards[i].cardValue, currentPlayer.openCards.get(i).cardValue)
            assertEquals(tableCards[i].cardsuit, currentPlayer.openCards.get(i).cardsuit)
        }

    }


    @Test
    fun swapOneTest() {
        val currentPlayer = game.currentGame?.players?.get(game.currentGame!!.currentPlayer)

        //set the cards of the table
        val cardStack1 = mutableListOf(
            Card(CardSuit.HEARTS, CardValue.TEN),
            Card(CardSuit.DIAMONDS, CardValue.JACK),
            Card(CardSuit.CLUBS, CardValue.QUEEN)
        )

        //set the cards of the player
        val cardStack2: MutableList<Card> = mutableListOf(
            Card(CardSuit.SPADES, CardValue.ACE),
            Card(CardSuit.HEARTS, CardValue.TWO),
            Card(CardSuit.DIAMONDS, CardValue.THREE)
        )

        game.currentGame?.tableCards = cardStack1
        currentPlayer?.openCards = cardStack2

        currentPlayer?.hasPushed = true

        val tableCards = game.currentGame?.tableCards
        val playerCards = currentPlayer?.openCards

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
    }

    @Test
    fun endTurnTest() {

        val initialCurrentPlayerIndex = game.currentGame?.currentPlayer ?: 0
        val initialRoundsLeft = game.currentGame?.roundsLeft ?: 0

        // to execute endTurn function
        game.playerActionService.endTurn()

        // transition to the next player
        val newCurrentPlayerIndex = game.currentGame?.currentPlayer
        assertEquals((initialCurrentPlayerIndex + 1) , newCurrentPlayerIndex, "Should move to the next player.")



        assertEquals(initialRoundsLeft, game.currentGame?.roundsLeft, "Rounds left should remain the same if not last player's turn.")
    }



    @Test
    fun evaluateHandTest() {

        val currentPlayer = game.currentGame?.players?.get(game.currentGame!!.currentPlayer)

        // to set up a  hand ards for the player
        val playerOpenHand: MutableList<Card> = mutableListOf(
            Card(CardSuit.HEARTS, CardValue.TEN),
            Card(CardSuit.HEARTS, CardValue.JACK),
            Card(CardSuit.HEARTS, CardValue.QUEEN)
        )

        val playerHiddenHand: MutableList<Card> = mutableListOf(
            Card(CardSuit.HEARTS, CardValue.TEN),
            Card(CardSuit.HEARTS, CardValue.JACK),
        )


        currentPlayer?.openCards = playerOpenHand
        currentPlayer?.hiddenCards = playerHiddenHand

        game.playerActionService.evaluateHand(currentPlayer!!)


        assertEquals(PokerHand.FLUSH, currentPlayer.handResult, "The hand evaluation did not match the expected score.")


    }



}