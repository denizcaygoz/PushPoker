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
    var drawStack: CardStack? = null
    /**
     * discardStack Represents the stack of cards where players discard. This stack is used for players to discard cards from their hand.
     */
    var discardStack: CardStack? = null
    /**
     * tableCards Holds the cards currently on the table.
     */
    var tableCards: MutableList<Card>? = null


}
