package service
import entity.Card
import entity.CardStack
import entity.CardSuit
import entity.CardValue

/**
 * CardService class is responsible to the initial deck of drawCard.
 * createNewDeck() function is used in the createNewGame() function in the GameService class
 */
class CardService(private val rootService: RootService): AbstractRefreshingService() {
    /**
     * This function creates a deck of 52 Cards with all possible combination of CardSuit and CardValue values.
     * @return: CardStack which is then assigned in drawStack in createNewGame() function.
     */
    fun createNewDeck(): CardStack {
        val newDeck = CardStack()
        //To iterate over each suit and value to create the cards
        CardSuit.values().forEach { suit ->
            CardValue.values().forEach { value ->
                newDeck.cards.add(Card(suit, value))
            }
        }
        //To shuffle the deck
        newDeck.cards.shuffle()

        return newDeck
    }

}