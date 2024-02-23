package entity
/**
 *This class to represents a player in the game.
 */
class Player(val name: String) {
    /**
      *Indicates whether the player has performed a 'push' action in the current round.
      */
    var hasPushed: Boolean = false
    /**
     *Indicates whether the player has performed a 'swap' action in the current round.
     */
    var hasSwapped: Boolean = false
    /**Represents the cards that are hidden from other players.
     * These are the cards in the player's hand that only the player can see.
     */
    var hiddenCards: MutableList<Card> = mutableListOf()
    /**
     *Represents the cards that are visible to all players.
     *These are the cards placed on the table in front of the player, face up.
     */
    var openCards: MutableList<Card> = mutableListOf()

    var handResult: PokerHand = PokerHand.NONE
}
