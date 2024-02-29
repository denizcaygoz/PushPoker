package service

import view.Refreshable

/**
 * AbstractRefreshingService is inherited by Service classes. In order to use addRefreshable
 * and onAllRefreshables functions.
 */
abstract class AbstractRefreshingService {
    /**
     * list that stores refreshables.
     */
    var refreshables = mutableListOf<Refreshable>()

    /**
     *adds new refreshable to the list refreshables.
     */
    fun addRefreshable(newRefreshable: Refreshable) {
        refreshables += newRefreshable
    }

    /**
     *This function is designed to execute a given action or method
     * on every Refreshable instance currently stored in the refreshables list.
     */
    fun onAllRefreshables(method: Refreshable.() -> Unit) =
        refreshables.forEach { it.method() }

}


