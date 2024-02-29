package entity

import kotlin.test.*

/**
 * Test class to test Player class
 */
class PlayerTest {
    lateinit var p1: Player
    lateinit var p2: Player
    lateinit var p3: Player
    lateinit var p4: Player

    var p2handResult: PokerHand = PokerHand.FULL_HOUSE
    var p4handResult: PokerHand = PokerHand.FLUSH

    @BeforeTest
    fun setUp() {
        p1 = Player("P1")
        p2 = Player("P2")
        p3 = Player("P3")
        p4 = Player("P4")

        p1.hasPushed = true
        p1.hasSwapped = true

        p2.hasPushed = true
        p2.hasSwapped = false

        p2.handResult = p2handResult
        p4.handResult = p4handResult

    }
    @Test
    fun testHandResult() {
        assertEquals(PokerHand.FULL_HOUSE, p2.handResult, "P2's handResult should be FULL_HOUSE")
        assertEquals(PokerHand.FLUSH, p4.handResult, "P4's handResult should be FLUSH")
    }
    @Test
    fun testPlayersName() {
        assertEquals("P1",p1.name)
        assertEquals("P2",p2.name)
        assertEquals("P3",p3.name)
        assertEquals("P4",p4.name)

        assertFalse("P1" == p2.name)
        assertFalse("P2" == p3.name)
        assertFalse("P3" == p4.name)
        assertFalse("P4" == p1.name)
    }

    @Test
    fun testHasPushedAndHasSwapped()
    {
        assertTrue(p1.hasPushed)
        assertTrue(p1.hasSwapped)

        assertTrue(p2.hasPushed)
        assertFalse(p2.hasSwapped)
    }

    /**
     * To Test if List of cards initialized successfully.
     */
    @Test
    fun TestHiddenAndOpenCards()
    {
        assertNotNull(p3.openCards)
        assertNotNull(p3.hiddenCards)
    }

}