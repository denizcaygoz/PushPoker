package entity



/**
 * CardStack class is used for drawStack and discardStack in PushPoker class.
 *
 */

class CardStack {

    //List of cards which is part of drawStack or discardStack
    var cards: MutableList<Card> = mutableListOf()



    /**
     * Draws a specified amount of cards from the stack.
     * @param amount The number of cards to draw.
     * @return A list of drawn cards, which are removed from the stack.
     */
    fun drawAmount(amount: Int): List<Card> {
        // To make sure the requested amount does not exceed the available number of cards
        val actualAmount = amount.coerceAtMost(cards.size)

        //To get the first 'actualAmount' of cards from the stack
        val drawnCards = cards.take(actualAmount)

        //To remove these cards from the original list
        cards = cards.drop(actualAmount).toMutableList()

        // Return the drawn cards
        return drawnCards
    }
}