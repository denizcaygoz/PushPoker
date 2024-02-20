package entity
/**
 * PokerHand stores the player Hands.
 * NONE is the worst player Hand and ROYAL_FLUSH is the best one.
 */
enum class PokerHand {
    NONE,
    PAIR,
    TWO_PAIR,
    THREE_OF_A_KIND,
    STRAIGHT,
    FLUSH,
    FULL_HOUSE,
    FOUR_OF_A_KIND,
    STRAIGHT_FLUSH,
    ROYAL_FLUSH,
    ;
}