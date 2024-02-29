package service

import entity.*
import kotlin.test.*

/**
 *This class is to test CardService and specifically the createNewDeck() function
 */
class CardServiceTest {

    lateinit var game: RootService
    lateinit var deck: CardStack

    /**
     * Setting up game, currentGame and deck to test createNewDeck() function.
     * The CardStack that is by createNewDeck() created is assign to deck variable.
     */
    @BeforeTest
    fun setUp() {
        game = RootService()
        game.currentGame = PushPoker()
        deck = game.cardService.createNewDeck()
    }

    /**
     *assertEquals on the line 36 tests if the deck contains 52 cards when it's first initialized.
     *assertEquals on the line 39 tests checks if the deck contains 4 different suits
     *groupBy {it.cardsuit} function iterates over the deck and creates a map where each key
     *is a distinct cardSuit found in the deck.
     *assertEquals on the line 45 checks if the deck contains 13 different CardValue.
     */
    @Test
    fun createNewDeckTest() {
        assertEquals(52, deck.cards.size, "Deck should contain 52 cards")

        val suitGroups: Map<CardSuit, List<Card>> = deck.cards.groupBy { it.cardsuit }
        assertEquals(4, suitGroups.size, "Deck should contain 4 suits")
        suitGroups.forEach { (_, cards) ->
            assertEquals(13, cards.size, "Each suit should be represented in all 13 different cards.")
        }

        val valueGroups: Map<CardValue, List<Card>> = deck.cards.groupBy { it.cardValue }
        assertEquals(13, valueGroups.size, "Deck should contain 13 values")
        valueGroups.forEach { (_, cards) ->
            assertEquals(4, cards.size, "Each value should be represented in all 4 different suits.")
        }
    }


}
