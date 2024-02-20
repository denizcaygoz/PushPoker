package entity

/**
 * CardSuit class represents 4 different type of cards:
 * clubs, spades, hearts, or diamonds
 */
enum class CardSuit {
    CLUBS,
    SPADES,
    HEARTS,
    DIAMONDS,
    ;

    /**
     * provide a single character to represent this suit.
     * Returns one of: ♣/♠/♥/♦
     */
    override fun toString() = when(this) {
        CLUBS -> "♣"
        SPADES -> "♠"
        HEARTS -> "♥"
        DIAMONDS -> "♦"
    }


}