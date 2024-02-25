package view

import entity.Card
import entity.Player


interface Refreshable {
    fun refreshAfterStartNewGame() {}
    fun refreshAfterStartTurn(player: Player) {}
    fun refreshTableCards() {}
    fun refreshDiscardStack(card: Card) {}
    fun refreshPlayerCards(player: Player) {}
    fun refreshHandValue(player: Player) {}
    fun refreshAfterEndTurn() {}
    fun refreshAfterEndRound(roundsLeft: Int) {}
    fun refreshAfterEndGame() {}


}