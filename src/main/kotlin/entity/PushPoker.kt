package entity

//Push Poker  represents the board game.
class PushPoker {

    var players: MutableList<Player> = mutableListOf()

    // Tracks the number of rounds left in the game.
    var roundsLeft: Int = 0

    // Represents the index or  of the current player.
    // This can be used to keep track of whose turn it is in the game.
    var currentPlayer: Int = 0

    // Represents the stack of cards from which players draw.
    val drawStack = CardStack()

    // Represents the stack of cards where players discard.
    // This stack is used for players to discard cards from their hand.
    val discardStack = CardStack()

    // Holds the cards currently on the table.
    val tableCards: MutableList<Card> = mutableListOf()


}
