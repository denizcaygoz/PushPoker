package service
import entity.Card
import entity.CardStack
import entity.CardSuit
import entity.CardValue

class CardService(private val rootService: RootService): AbstractRefreshingService() {
    fun createNewDeck(): CardStack {
        val newDeck = CardStack()
        //To iterate over each suit and value to create the cards
        CardSuit.values().forEach { suit ->
            CardValue.values().forEach { value ->
                newDeck.cards.add(Card(suit, value))
            }
        }
        //To Shuffle the deck
        newDeck.cards.shuffle()

        return newDeck
    }

}