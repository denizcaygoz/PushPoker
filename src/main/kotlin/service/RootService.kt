package service

import entity.PushPoker
import view.Refreshable

/**
 * RootService class assigns RootService to gameService, playerActionService, cardService.
 */
class RootService {
    var currentGame: PushPoker? = null
    val gameService = GameService(this)
    val playerActionService = PlayerActionService(this)
    val cardService = CardService(this)

    /**
     * addRefreshable() function that allows for a Refreshable object
     * to be registered with the game's services.
     * @param newRefreshable: Refresable interface to apply the changes in GUI.
     */
    fun addRefreshable(newRefreshable: Refreshable) {
        gameService.addRefreshable(newRefreshable)
        playerActionService.addRefreshable(newRefreshable)
        cardService.addRefreshable(newRefreshable)
    }

    /**
     * This function calls addRefreshables with any number of Refreshable objects as parameters.
     *
     *@param newRefreshables: The vararg keyword before the parameter newRefreshables indicates that zero or more Refreshable
     * objects can be passed to addRefreshables.
     */
    fun addRefreshables(vararg newRefreshables: Refreshable) {
        newRefreshables.forEach { addRefreshable(it) }
    }


}