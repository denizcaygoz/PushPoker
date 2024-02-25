package service

import view.Refreshable

/**
 * AbstractRefreshingService is inherited by Service classes. In order to use addRefreshable
 * and onAllRefreshables functions.
 */
abstract class AbstractRefreshingService {
    var refreshables = mutableListOf<Refreshable>()
    fun addRefreshable(newRefreshable: Refreshable) {
        refreshables += newRefreshable
    }

    fun onAllRefreshables(method: Refreshable.() -> Unit) =
        refreshables.forEach { it.method() }

}


