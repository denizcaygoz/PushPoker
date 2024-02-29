package service
import entity.CardStack
import entity.Player
import entity.PushPoker

/**
 * GameService test contains 2 important functions.
 * startNewGame() function is called when the game is first initialized.
 * evaluateGame() function is called when the game is over.
 */
class GameService(private val rootService: RootService): AbstractRefreshingService() {
    /**
     * This function is to set up the game properly.
     * @param players: this is the names of all players in the game.
     * @param rounds: this is the number of total rounds that is set before the initialization of the game.
     */
    fun startNewGame(players: List<String>, rounds: Int) {
        // Check for valid number of rounds
        check(rounds >= 2) {"minimum 2 rounds"}
        check(rounds <= 7) {"maximum 7 rounds"}

        // Check for valid number of players
        check(players.size >= 2) {"minimum 2 players"}
        check(players.size <= 4) {"maximum 4 players"}

        //to shuffle the order of the players' List
        //players.shuffled()
        //draw stack is by createNewDeck() initialized
        val drawStack: CardStack = rootService.cardService.createNewDeck()
        //game is initialized.
        val game = PushPoker()
        game.drawStack = drawStack
        game.discardStack = CardStack()
        //Player objects are created and added to the playerList in PushPoker.
        players.forEach { playerName ->
            game.players.add(Player(playerName))
        }
        //3 tableCards in the middle are set up
        game.tableCards = drawStack.drawAmount(3)
        game.roundsLeft = rounds
        game.currentPlayer = 0

        //each player's open and hidden Cards are given from the cards in drawStack
        for(i in game.players.indices) {
            game.players[i].hiddenCards = drawStack.drawAmount(2)
            game.players[i].openCards = drawStack.drawAmount(3)
        }
        //currentGame is assigned the game variable (which is a PushPoker class)
        rootService.currentGame = game

        /**
         * this is calculated so that we can see the hand value of player 1 right after the game starts.
         */
        rootService.playerActionService.evaluateHand(rootService.currentGame!!.players[rootService.currentGame!!.currentPlayer])

        onAllRefreshables {
            refreshAfterStartNewGame()
        }
    }

    /**
     * evaluateGame() is called when the game is over in order to sort the players according to their Hand values.
     * The one the highest PokerHand value gets the first place.
     * The one the lowest PokerHand value gets the last place.
     * @return List<Player>: returns the list of players with sorted order by their Hand values.
     */

    fun evaluateGame(): List<Player> {
        //If currentGame is null then throw IllegalStateException
        val game = rootService.currentGame ?: throw IllegalStateException("Game has not been started")
        return game.players.sortedByDescending { it.handResult }.toMutableList()
    }
}