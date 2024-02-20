package entity
/**
 *PushPoker class represents the board of the game on which players can play.
 */


class PushPoker {
    /**
     * players represent the list of players in the game.
     */
    var players: MutableList<Player> = mutableListOf()
    /**
     * currentPlayer Represents the index or  of the current player. This can be used to keep track of whose turn it is in the game.
     */
    var currentPlayer: Int = 0
    /**
     * roundsLeft Tracks the number of rounds left in the game.
     */
    var roundsLeft: Int = 0
    /**
     *drawStack Represents the stack of cards from which players draw.
     */
    val drawStack = CardStack()
    /**
     * discardStack Represents the stack of cards where players discard. This stack is used for players to discard cards from their hand.
     */
    val discardStack = CardStack()
    /**
     * tableCards Holds the cards currently on the table.
     */
    val tableCards: MutableList<Card> = mutableListOf()

    init {
        require(players.size in 2..4) {"Number of Players has to be between 2-4"}
        require(tableCards.size <= 3) {"More than 3 Cards on the table is not possible"}
    }

}
