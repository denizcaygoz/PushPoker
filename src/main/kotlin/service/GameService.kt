package service
import entity.CardStack
import entity.Player
import entity.PushPoker

class GameService(private val rootService: RootService): AbstractRefreshingService() {

    fun startNewGame(players: List<String>, rounds: Int) {
        // Check for valid number of rounds
        check(rounds >= 2) { "minimum 2 rounds" }
        check(rounds <= 7) { "maximum 7 rounds" }

        // Check for valid number of players
        check(players.size >= 2) { "minimum 2 players" }
        check(players.size <= 4) { "maximum 4 players" }

        players.shuffled()

        val drawStack = rootService.cardService.createNewDeck()

        val game = PushPoker()
        game.drawStack = drawStack
        game.discardStack = CardStack()
        players.forEach { playerName ->
            game.players.add(Player(playerName))
        }
        game.tableCards = drawStack.drawAmount(3)
        game.roundsLeft = rounds
        game.currentPlayer = 0

        for(i in game.players.indices) {
            game.players[i].hiddenCards = drawStack.drawAmount(2)
            game.players[i].openCards = drawStack.drawAmount(3)
        }

        rootService.currentGame = game

        onAllRefreshables {
            refreshAfterStartNewGame()
        }
    }


    fun evaluateGame(): List<Player> {
        val game = rootService.currentGame ?: throw IllegalStateException("Game has not been started")
        return game.players.sortedByDescending { it.handResult }
    }
}