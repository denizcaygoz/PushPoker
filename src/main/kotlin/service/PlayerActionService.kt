package service
import entity.CardValue
import entity.Player
import entity.PokerHand

class PlayerActionService(private val rootService: RootService): AbstractRefreshingService() {

    fun startTurn() {
        val game = rootService.currentGame
        checkNotNull(game) { "Game is not started yet." }
        onAllRefreshables { refreshAfterStartTurn(game.players[game.currentPlayer]) }
    }

    fun pushLeft() {
        val game = rootService.currentGame ?: throw IllegalStateException("Game is not started yet.")
        if (game.roundsLeft == 0) throw IllegalStateException("Current game has ended already.")

        val currentPlayer = game.players[game.currentPlayer]
        check(!currentPlayer.hasPushed) { "Player ${currentPlayer.name} has already pushed." }

        game.tableCards?.let { tableCards ->
            val discardCard = tableCards[0]
            game.discardStack?.cards?.add(discardCard)
            tableCards[0] = tableCards[1]
            tableCards[1] = tableCards[2]
            game.drawStack?.drawAmount(1)?.firstOrNull()?.let { newCard ->
                tableCards[2] = newCard
            } ?: throw IllegalStateException("DrawStack is empty or null.")

            currentPlayer.hasPushed = true

            onAllRefreshables {
                refreshTableCards()
                refreshDiscardStack(discardCard)
            }
        } ?: throw IllegalStateException("TableCards are not initialized.")
    }

    fun pushRight() {
        val game = rootService.currentGame ?: throw IllegalStateException("Game is not started yet.")
        if (game.roundsLeft == 0) throw IllegalStateException("Current game has ended already.")

        val currentPlayer = game.players[game.currentPlayer]
        check(!currentPlayer.hasPushed) { "Player ${currentPlayer.name} has already pushed." }

        game.tableCards?.let { tableCards ->
            val discardCard = tableCards[2]
            game.discardStack?.cards?.add(discardCard)
            tableCards[2] = tableCards[1]
            tableCards[1] = tableCards[0]
            game.drawStack?.drawAmount(1)?.firstOrNull()?.let { newCard ->
                tableCards[0] = newCard
            } ?: throw IllegalStateException("DrawStack is empty or null.")

            currentPlayer.hasPushed = true

            onAllRefreshables {
                refreshTableCards()
                refreshDiscardStack(discardCard)
            }
        } ?: throw IllegalStateException("TableCards are not initialized.")
    }


    fun swapAll() {
        val game = rootService.currentGame
        checkNotNull(game) { "No game started yet." }
        check(value = game.roundsLeft > 0) { "Current game has ended already." }

        val currentPlayer = game.players.getOrNull(game.currentPlayer)
            ?: throw IllegalStateException("Invalid current player.")

        check(currentPlayer.hasPushed)
        {"Player ${game.currentPlayer} needs to push first."}
        check(!currentPlayer.hasSwapped)
        {"Player ${game.currentPlayer} has already swapped."}

        currentPlayer.openCards.let { playerOpenCards ->
            game.tableCards?.let { tableCards ->
                for (i in 0..2) {
                    if (i < playerOpenCards.size && i < tableCards.size) {
                        val playerCardTemp = playerOpenCards[i]
                        playerOpenCards[i] = tableCards[i]
                        tableCards[i] = playerCardTemp
                    }
                }
            } ?: throw IllegalStateException("TableCards are not initialized.")
        }

        evaluateHand(game.players[game.currentPlayer])

        onAllRefreshables {
            refreshTableCards()
            refreshPlayerCards(currentPlayer)
            refreshHandValue(currentPlayer)
        }

        currentPlayer.hasSwapped = true

        endTurn()
    }


    fun swapOne(playerCard: Int, tableCard: Int) {
        val game = rootService.currentGame
        checkNotNull(game) { "No game started yet." }
        check(value = game.roundsLeft > 0) { "Current game has ended already." }

        val currentPlayer = game.players.getOrNull(game.currentPlayer)
            ?: throw IllegalStateException("Invalid current player.")

        check(value = (0 <= playerCard) && (playerCard <= 2)) { "Invalid playerCard. $playerCard" }
        check(value = (0 <= tableCard) && (tableCard <= 2)) { "Invalid tableCard. $tableCard" }

        check(currentPlayer.hasPushed)
        {"Player ${game.currentPlayer} needs to push first."}
        check(!currentPlayer.hasSwapped)
        {"Player ${game.currentPlayer} has already swapped."}

        currentPlayer.openCards.let { playerOpenCards ->
            game.tableCards?.let { tableCards ->
                if (playerCard < playerOpenCards.size && tableCard < tableCards.size) {
                    val playerCardTemp = playerOpenCards[playerCard]
                    playerOpenCards[playerCard] = tableCards[tableCard]
                    tableCards[tableCard] = playerCardTemp
                } else {
                    throw IllegalStateException("Card indices are out of bounds.")
                }
            } ?: throw IllegalStateException("TableCards are not initialized.")
        }

        evaluateHand(currentPlayer)

        onAllRefreshables {
            refreshTableCards()
            refreshPlayerCards(currentPlayer)
            refreshHandValue(currentPlayer)
        }

        currentPlayer.hasSwapped = true

        endTurn()
    }

    fun endTurn() {
        val game = rootService.currentGame
        checkNotNull(game) { "No game started yet." }



        //1 Round ends. Every player made their move 1 time.
        if(game.players.size-1 == game.currentPlayer) {
            game.currentPlayer = 0
            game.roundsLeft--
            //Game is over
            if(game.roundsLeft == 0) {
                rootService.gameService.evaluateGame()
                onAllRefreshables {
                    refreshAfterEndGame()
                }
            }
            //Game is not over but 1 round.
            else {
                onAllRefreshables {
                    refreshAfterEndRound(game.roundsLeft)
                }
            }
        }
        //Round haven't finished yet.
        else {
            game.currentPlayer++
            onAllRefreshables {
                refreshAfterEndTurn()
            }
        }
    }

    /**
     * In order to evaluate the player's hand followings are implemented:
     * 1.Combine the player's open and hidden cards into a single list for evaluation.
     * 2.Sort this combined list by card value to simplify finding straights.
     * 3.Group cards by value and by suit to help identify pairs, three-of-a-kinds, four-of-a-kinds, flushes, and full houses.
     * 4.Check for straight flushes, flushes, straights, and other combinations in descending order of hand strength.
     * 5.Update the player's handResult with the highest value hand identified.
     */
     fun evaluateHand(player: Player) {
        //To combine open and hidden cards
        val allCards = player.openCards + player.hiddenCards

        //to sort cards by value to simplify straight detection
        val sortedCards = allCards.sortedBy { it.cardValue }

        // Group cards by value and by suit
        val cardsByValue = sortedCards.groupBy { it.cardValue }
        val cardsBySuit = sortedCards.groupBy { it.cardsuit }

        // Check for Flush
        val flush = cardsBySuit.values.any { it.size >= 5 }
        // Check for Straight (at least 5 consecutive values)
        var straight = false
        var straightFlush = false
        var highestStraightFlush: CardValue? = null
        for (i in 0 until sortedCards.size - 4) {
            if (sortedCards[i].cardValue.ordinal + 4 == sortedCards[i + 4].cardValue.ordinal &&
                sortedCards[i].cardValue.ordinal + 3 == sortedCards[i + 3].cardValue.ordinal &&
                sortedCards[i].cardValue.ordinal + 2 == sortedCards[i + 2].cardValue.ordinal &&
                sortedCards[i].cardValue.ordinal + 1 == sortedCards[i + 1].cardValue.ordinal) {
                straight = true
                // Check for Straight Flush
                if (flush && cardsBySuit[sortedCards[i].cardsuit]?.containsAll(sortedCards.subList(i, i + 5)) == true) {
                    straightFlush = true
                    highestStraightFlush = sortedCards[i + 4].cardValue
                }
            }
        }

        // Check for Royal Flush
        val royalFlush = straightFlush && highestStraightFlush == CardValue.ACE

        // Check for other hands
        val fourOfAKind = cardsByValue.values.any { it.size == 4 }
        val threeOfAKind = cardsByValue.values.any { it.size == 3 }
        val pairs = cardsByValue.values.count { it.size == 2 }

        //to determine hand result
        player.handResult = when {
            royalFlush -> PokerHand.ROYAL_FLUSH
            straightFlush -> PokerHand.STRAIGHT_FLUSH
            fourOfAKind -> PokerHand.FOUR_OF_A_KIND
            threeOfAKind && pairs >= 1 -> PokerHand.FULL_HOUSE
            flush -> PokerHand.FLUSH
            straight -> PokerHand.STRAIGHT
            threeOfAKind -> PokerHand.THREE_OF_A_KIND
            pairs >= 2 -> PokerHand.TWO_PAIR
            pairs == 1 -> PokerHand.PAIR
            else -> PokerHand.NONE
        }


        onAllRefreshables {
            refreshHandValue(player)
        }
    }
}