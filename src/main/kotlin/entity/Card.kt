package entity
/**
 * Card Class represents 52 different cards that used in the game.
 *@param cardsuit: represents 4 different type of card suits.
 * @param cardValue: represents 13 different type of card values.
 */
class Card(val cardsuit: CardSuit,val cardValue: CardValue) {
    override fun toString() = "$cardsuit$cardValue"
}