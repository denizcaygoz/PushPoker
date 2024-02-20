package entity


import kotlin.test.*

/**
 * To Test CardStack Class especially drawAmount function
 */
internal class CardStackTest {
     lateinit var cardStack: CardStack
     @BeforeTest
     fun setUp() {
        cardStack = CardStack()
        cardStack.cards.addAll(listOf(
            Card(CardSuit.HEARTS, CardValue.ACE),
            Card(CardSuit.DIAMONDS, CardValue.KING),
            Card(CardSuit.CLUBS, CardValue.QUEEN),
            Card(CardSuit.SPADES, CardValue.JACK)
        ))
     }

    /**
     * Expected that cards list in cardStack is initialized as a List when the object cardStack is created.
     */
    @Test
    fun testCardsNotNull() {
        assertNotNull(cardStack.cards)
    }




    @Test
    fun testDrawAmountExact()  {
        val drawnCards = cardStack.drawAmount(2)
        assertEquals(2, drawnCards.size, "Should have drawn 2 cards")
        assertEquals(2, cardStack.cards.size, "Stack should have 2 cards left")
    }

    @Test
    fun testDrawAmountLessThanAvailable() {
        val drawnCards = cardStack.drawAmount(3)
        assertEquals(3, drawnCards.size, "Should not have drawn 3 cards")
        assertEquals(1, cardStack.cards.size, "Stack should have 1 cards left")
    }

    @Test
    fun testDrawAmountMoreThanAvailable() {
        val drawnCards = cardStack.drawAmount(5)
        assertEquals(4, drawnCards.size, "Should have drawn all cards")
        assertTrue(cardStack.cards.isEmpty(), "Stack should be empty")
    }

    @Test
    fun testDrawAmountNone() {
        val drawnCards = cardStack.drawAmount(0)
        assertEquals(0, drawnCards.size, "Should not have drawn any cards")
        assertEquals(4, cardStack.cards.size, "Stack should remain full")
    }
}