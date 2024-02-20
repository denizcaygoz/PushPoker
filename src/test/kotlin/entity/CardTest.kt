package entity

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * To Test if cards are initialized correctly.
 */
class CardTest {
    lateinit var cardHeartAce: Card
    lateinit var cardSpadeKing: Card
    lateinit var cardDiamondQueen: Card
    lateinit var cardClubJack: Card

    @BeforeTest
    fun setUp() {
        cardHeartAce = Card(CardSuit.HEARTS, CardValue.ACE)
        cardSpadeKing = Card(CardSuit.SPADES, CardValue.KING)
        cardDiamondQueen = Card(CardSuit.DIAMONDS, CardValue.QUEEN)
        cardClubJack = Card(CardSuit.CLUBS, CardValue.JACK)
    }

    @Test
    fun testCardInitialization() {
        assertEquals(CardSuit.HEARTS, cardHeartAce.cardsuit)
        assertEquals(CardValue.ACE, cardHeartAce.cardValue)

        assertEquals(CardSuit.SPADES, cardSpadeKing.cardsuit)
        assertEquals(CardValue.KING, cardSpadeKing.cardValue)

        assertEquals(CardSuit.DIAMONDS, cardDiamondQueen.cardsuit)
        assertEquals(CardValue.QUEEN, cardDiamondQueen.cardValue)

        assertEquals(CardSuit.CLUBS, cardClubJack.cardsuit)
        assertEquals(CardValue.JACK, cardClubJack.cardValue)
    }

}
