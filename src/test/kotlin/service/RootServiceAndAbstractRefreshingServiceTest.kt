package service
import view.Refreshable
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * A class which inherits Refreshable to help testing
 */
class TestRefreshable0: Refreshable {

}

/**
 * RootServiceTest class tests mainly the addRefreshable and addRefreshables functions in RootService class.
 */
class RootServiceAndAbstractRefreshingServiceTest {

    private lateinit var rootService: RootService

    @BeforeTest
    fun setUp() {
        rootService = RootService()
        rootService.gameService.startNewGame(listOf("p1", "p2"), 5)
    }

    /**
     * to test the addRefreshable(newRefreshable: Refreshable) function in class RootService
     */
    @Test
    fun addRefreshableTest() {
        val testRefreshable1 = TestRefreshable0()
        val testRefreshable2 = TestRefreshable0()
        rootService.gameService.addRefreshable(testRefreshable1)
        assertEquals(1,rootService.gameService.refreshables.size,"Size of the refresables should be 1")

        rootService.gameService.addRefreshable(testRefreshable2)
        assertEquals(2,rootService.gameService.refreshables.size,"Size of the refresables should be 2")

    }

    /**
     * to test the addRefreshables(vararg newRefreshables: Refreshable) function in class RootService
     */
    @Test
    fun addRefreshablesTest() {
        val testRefreshable1 = TestRefreshable()
        val testRefreshable2 = TestRefreshable()
        rootService.addRefreshables(testRefreshable1, testRefreshable2)

    }
}



