package view

import entity.Card
import entity.Player

/**
 * Refreshable class updates the GUI every time when an action is happened in the game.
 */
interface Refreshable {
    /**
     * is called after the game is started.
     */
    fun refreshAfterStartNewGame() {}
    /**
     * is called after the player has the turn.
     */
    fun refreshAfterStartTurn(player: Player) {}

    /**
     * is called when the table cards alters.
     */
    fun refreshTableCards() {}

    /**
     * to update the discardStack when necessary
     */
    fun refreshDiscardStack(card: Card) {}

    /**
     * to update the player cards.
     */
    fun refreshPlayerCards(player: Player) {}

    /**
     * to update handValue
     */
    fun refreshHandValue(player: Player) {}

    /**
     * to update the GUI when the player's turn ends.
     */
    fun refreshAfterEndTurn() {}

    /**
     * to update the GUI after player's round ends.
     */
    fun refreshAfterEndRound(roundsLeft: Int) {}

    /**
     * update the GUI to scoreboard screen when game ends.
     */
    fun refreshAfterEndGame() {}



}