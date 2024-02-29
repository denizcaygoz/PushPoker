package entity
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PushPokerTest {

    lateinit var pushPoker: PushPoker
    lateinit var pushPokerToTest3CardsOnTheTable: PushPoker

    @BeforeTest
    fun setUp() {
        pushPokerToTest3CardsOnTheTable = PushPoker()
        pushPokerToTest3CardsOnTheTable.players.addAll(listOf(Player("P1"),Player("P2")))
        pushPokerToTest3CardsOnTheTable.tableCards = mutableListOf()
        pushPokerToTest3CardsOnTheTable.tableCards!!.addAll(
            mutableListOf(
            Card(CardSuit.HEARTS, CardValue.ACE),
            Card(CardSuit.DIAMONDS, CardValue.KING),
            Card(CardSuit.CLUBS, CardValue.QUEEN)
        )
        )

    }
    /**
     * tests if game can be initialized with 2 players.
     */
    @Test
    fun testWithTwoPlayers() {
        pushPoker = PushPoker()
        pushPoker.tableCards = mutableListOf()
        pushPoker.players.addAll(listOf(Player("P1"),Player("P2")))
        pushPoker.roundsLeft = 5
        pushPoker.tableCards!!.addAll(listOf(Card(CardSuit.HEARTS, CardValue.ACE)))

        assertEquals(2, pushPoker.players.size)
        assertEquals(5, pushPoker.roundsLeft)
        assertEquals(1, pushPoker.tableCards!!.size)
    }

    /**
     * tests if game can be initialized with 4 players.
     */
    @Test
    fun testWithFourPlayers() {
        pushPoker = PushPoker()
        pushPoker.tableCards = mutableListOf()
        pushPoker.players.addAll(listOf(Player("P1"),Player("P2"),Player("P3"),Player("P4")))
        pushPoker.roundsLeft = 5
        pushPoker.tableCards!!.addAll(listOf(Card(CardSuit.HEARTS, CardValue.ACE)))

        assertEquals(4, pushPoker.players.size)
        assertEquals(5, pushPoker.roundsLeft)
        assertEquals(1, pushPoker.tableCards!!.size)
    }


    /**
     * tests if there are 3 table cards.
     */
    @Test
    fun testWith3TableCards() {
        assertEquals(3, pushPokerToTest3CardsOnTheTable.tableCards!!.size, "Should have 3 cards on the table")
    }

}
