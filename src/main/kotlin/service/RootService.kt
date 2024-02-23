package service

import entity.PushPoker
import view.Refreshable

class RootService {
    var currentGame: PushPoker? = null
    val gameService = GameService(this)
    val playerActionService = PlayerActionService(this)
    val cardService = CardService(this)


    fun addRefreshable(newRefreshable: Refreshable) {
        gameService.addRefreshable(newRefreshable)
        playerActionService.addRefreshable(newRefreshable)
        cardService.addRefreshable(newRefreshable)
    }
    fun addRefreshables(vararg newRefreshables: Refreshable) {
        newRefreshables.forEach { addRefreshable(it) }
    }


}