package service

import entity.Card
import entity.Player
import view.Refreshable

/**
 * TestRefreshable creates a refreshables to test the refreshable calls
 * in the functions of Service classes.
 */
class TestRefreshable: Refreshable {
    var refreshAfterStartNewGame : Boolean = false
        private set
    var refreshAfterStartTurn : Boolean = false
        private set
    var refreshTableCards  : Boolean = false
        private set
    var refreshDiscardStack: Boolean = false
        private set
    var refreshPlayerCards : Boolean = false
        private set
    var refreshHandValue : Boolean = false
        private set
    var refreshAfterEndTurn : Boolean = false
        private set
    var refreshAfterEndRound : Boolean = false
        private set
    var refreshAfterEndGame : Boolean = false
        private set

    fun resetRefreshableState() {
        refreshAfterStartNewGame = false
        refreshAfterStartTurn = false
        refreshTableCards = false
        refreshDiscardStack = false
        refreshPlayerCards = false
        refreshHandValue = false
        refreshAfterEndTurn = false
        refreshAfterEndRound = false
        refreshAfterEndGame = false
    }

    override fun refreshAfterStartNewGame() {
        refreshAfterStartNewGame = true
    }

    override fun refreshAfterStartTurn(player : Player) {
        refreshAfterStartTurn = true
    }

    override fun refreshTableCards() {
        refreshTableCards = true
    }

    override fun refreshDiscardStack(card : Card) {
        refreshDiscardStack = true
    }

    override fun refreshPlayerCards(player : Player) {
        refreshPlayerCards = true
    }

    override fun refreshHandValue(player : Player) {
        refreshHandValue = true
    }

    override fun refreshAfterEndTurn() {
        refreshAfterEndTurn = true
    }

    override fun refreshAfterEndRound(roundsLeft: Int) {
        refreshAfterEndRound = true
    }

    override fun refreshAfterEndGame() {
        refreshAfterEndGame = true
    }
}