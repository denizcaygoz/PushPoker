package entity
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PushPokerTest {

    lateinit var pushPoker: PushPoker
    lateinit var pushPokerToTest3CardsOnTheTable: PushPoker

    @BeforeTest
    fun setUp() {
        pushPokerToTest3CardsOnTheTable = PushPoker()
        pushPokerToTest3CardsOnTheTable.players.addAll(listOf(Player("P1"),Player("P2")))
        pushPokerToTest3CardsOnTheTable.tableCards!!.addAll(listOf(
            Card(CardSuit.HEARTS, CardValue.ACE),
            Card(CardSuit.DIAMONDS, CardValue.KING),
            Card(CardSuit.CLUBS, CardValue.QUEEN)
        ))

    }
    /**
     * this test didn't give the result I expected
     */
    @Test
    fun testWithTwoPlayers() {
        pushPoker = PushPoker()
        pushPoker.players.addAll(listOf(Player("P1"),Player("P2")))
        pushPoker.roundsLeft = 5
        pushPoker.tableCards!!.addAll(listOf(Card(CardSuit.HEARTS, CardValue.ACE)))

        assertEquals(2, pushPoker.players.size)
        assertEquals(5, pushPoker.roundsLeft)
        assertEquals(2, pushPoker.tableCards!!.size)
    }

    @Test
    fun testWithOnePlayer() {
        assertFailsWith<IllegalArgumentException> {
            PushPoker().apply {
                this.players.addAll(listOf(Player("P1")))
            }
        }
    }

    @Test
    fun testWith4TableCards() {
        val players = mutableListOf(Player("P1"), Player("P2"))
        assertFailsWith<IllegalArgumentException> {
            PushPoker().apply {
                this.players.addAll(players)
                this.tableCards!!.addAll(listOf(
                    Card(CardSuit.HEARTS, CardValue.ACE),
                    Card(CardSuit.DIAMONDS, CardValue.KING),
                    Card(CardSuit.CLUBS, CardValue.QUEEN),
                    Card(CardSuit.SPADES, CardValue.JACK)
                ))
            }
        }
    }
    /**
     * this test didn't give the result I expected
     */
    @Test
    fun testWith3TableCards() {
        assertEquals(3, pushPokerToTest3CardsOnTheTable.tableCards!!.size, "Should have 3 cards on the table")
    }

}
