package service

import entity.CardStack
import entity.PushPoker
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CardServiceTest {

    lateinit var game: RootService
    lateinit var deck: CardStack
    @BeforeTest
    fun setUp() {
        game = RootService()
        game.currentGame = PushPoker()
        deck = game.cardService.createNewDeck()
    }

    @Test
    fun createNewDeckTest() {
        assertEquals(52, deck.cards.size, "Deck should contain 52 cards")

        val suitGroups = deck.cards.groupBy { it.cardsuit }
        assertEquals(4, suitGroups.size, "Deck should contain 4 suits")
        suitGroups.forEach { (_, cards) ->
            assertEquals(13, cards.size, "Each suit should contain 13 cards")
        }

        val valueGroups = deck.cards.groupBy { it.cardValue }
        assertEquals(13, valueGroups.size, "Deck should contain 13 values")
        valueGroups.forEach { (_, cards) ->
            assertEquals(4, cards.size, "Each value should be represented in all 4 suits")
        }
    }


}
