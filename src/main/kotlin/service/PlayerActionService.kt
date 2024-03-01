package service
import entity.*

/**
 * In the PlayerActionService class the purpose of the functions is mainly to determine the course of the game.
 * And the options the player able to choose in order to perform his actions in the game.
 */

class PlayerActionService(private val rootService: RootService): AbstractRefreshingService() {

    var scoreboardList: List<Player> = mutableListOf()
    /**
     * startTurn is called when the player has the turn.
     * refreshAfterStartTurn is called to update the GUI
     */
    fun startTurn() {
        val game = rootService.currentGame
        checkNotNull(game) { "Game is not started yet." }
        evaluateHand(game.players[game.currentPlayer])
        onAllRefreshables { refreshAfterStartTurn(game.players[game.currentPlayer])
                                            refreshHandValue(game.players[game.currentPlayer])
        }
    }

    /**
     * pushLeft is called when the player has clicked the pushLeft button.
     * refreshTableCards() and refreshDiscardStack(discardCard) are called to update the GUI after the action has done.
     */
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
    /**
     * pushRight is called when the player has clicked the pushRight button.
     * refreshTableCards() and refreshDiscardStack(discardCard) are called to update the GUI after the action has done.
     */
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

    /**
     * swapAll is called when the player has clicked the swapAll button.
     * refreshTableCards() and refreshPlayerCards(currentPlayer) and refreshHandValue(currentPlayer) are called to update the GUI after the action has done.
     */
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

        val tableCards = game.tableCards

        val tempCards = ArrayList<Card>(currentPlayer.openCards)
        currentPlayer.openCards.clear()
        currentPlayer.openCards.addAll(tableCards!!)
        tableCards.clear()
        tableCards.addAll(tempCards)

        evaluateHand(game.players[game.currentPlayer])

        onAllRefreshables {
            refreshTableCards()
            refreshPlayerCards(currentPlayer)
            refreshHandValue(currentPlayer)
        }

        currentPlayer.hasSwapped = true

        //endTurn()
    }



    /**
     * swapOne is called when the player has clicked the swapOne button.
     * refreshTableCards() and refreshPlayerCards(currentPlayer) and refreshHandValue(currentPlayer) are called to update the GUI after the action has done.
     * @param playerCard: index of the card of the player that player wants to swap
     * @param tableCard: index of the table card that player wants to swap with.
     */
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

        //endTurn()
    }
    /**
     * endTurn() is called when the player has clicked the swapOne button.
     * refreshTableCards() and refreshPlayerCards(currentPlayer) and refreshHandValue(currentPlayer) are called to update the GUI after the action has done.
     */
    fun endTurn() {
        val game = rootService.currentGame
        checkNotNull(game) { "No game started yet." }



        //1 Round ends. Every player made their move 1 time.
        if(game.players.size-1 == game.currentPlayer) {
            game.players[game.currentPlayer].hasPushed = false
            game.players[game.currentPlayer].hasSwapped = false
            game.currentPlayer = 0
            game.roundsLeft--
            //Game is over
            if(game.roundsLeft == 0) {
                scoreboardList = rootService.gameService.evaluateGame()
                onAllRefreshables {
                    refreshAfterEndGame()
                }
            }
            //Game is not over but 1 round.
            else {
                onAllRefreshables {
                    refreshAfterEndRound(game.roundsLeft)
                    refreshHandValue(game.players[game.currentPlayer])
                }
            }
        }
        //Round haven't finished yet.
        else {
            game.players[game.currentPlayer].hasPushed = false
            game.players[game.currentPlayer].hasSwapped = false
            game.currentPlayer++
            onAllRefreshables {
                refreshAfterEndTurn()
                refreshHandValue(game.players[game.currentPlayer])
            }
        }
    }

    /**
     * evaluateHand function is called to evaluate the player hand.
     * @param player represents the player from which the hand value is calculated.
     * the function works as following:
     * 1.allCards = Combine the player's open and hidden cards into a single list for evaluation.
     * 2.cardsByValue = Sort this combined list by card value to simplify finding straights.
     * 3.cardsBySuit = Group cards by value and by suit to help identify pairs, three-of-a-kinds,
     * four-of-a-kinds, flushes, and full houses.
     * 4.Check for straight flushes, flushes, straights, and other combinations in descending
     * order of hand strength.
     * 5.Update the player's handResult with the highest value hand.
     */
     fun evaluateHand(player: Player) {
        //combining open and hidden cards
        val allCards: List<Card> = player.openCards + player.hiddenCards

        //sorting cards by value to simplify straight detection
        val sortedCards: List<Card> = allCards.sortedBy {it.cardValue}

        // Grouping cards by value and by suit
        val cardsByValue: Map<CardValue, List<Card>> = sortedCards.groupBy {it.cardValue}
        val cardsBySuit: Map<CardSuit, List<Card>> = sortedCards.groupBy {it.cardsuit}

        // Checking for Flush
        val flush: Boolean = cardsBySuit.values.any {it.size == 5}
        // Checking for Straight (at least 5 serial values)
        var straight = false
        var straightFlush = false
        var highestStraightFlush: CardValue? = null

        if (sortedCards[0].cardValue.ordinal + 4 == sortedCards[4].cardValue.ordinal &&
            sortedCards[0].cardValue.ordinal + 3 == sortedCards[3].cardValue.ordinal &&
            sortedCards[0].cardValue.ordinal + 2 == sortedCards[2].cardValue.ordinal &&
            sortedCards[0].cardValue.ordinal + 1 == sortedCards[1].cardValue.ordinal) {
            straight = true
                // Checking for Straight Flush
            if (flush) {
                straightFlush = true
                highestStraightFlush = sortedCards[4].cardValue
            }
        }
        // Checking for Royal Flush
        val royalFlush: Boolean = straightFlush && highestStraightFlush == CardValue.ACE

        // Checking for other hands
        val fourOfAKind: Boolean = cardsByValue.values.any {it.size >= 4}
        val threeOfAKind: Boolean = cardsByValue.values.any {it.size == 3}
        val pairs: Int = cardsByValue.values.count {it.size == 2}

        //determining hand result
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