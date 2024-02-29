package view

import entity.Card
import entity.CardStack
import entity.Player
import service.CardImageLoader
import service.RootService
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.util.BidirectionalMap
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.ImageVisual

/**
 * This scene represents everything that can be seen during the play.
 * The class represents the scene of board game, cards, player and other UI Components in the game.
 *
 * Note: I had a very short time while I was implemented this class. And I'm also not familiar with the framework. So
 * the approaches or solutions I use in my implementation could be better and more efficient.
 * And the comments could be more.
 */
class InGameScene(val rootService: RootService) : BoardGameScene(1920, 1080), Refreshable {
    // Player 1 open cards
    private val player1OpenCard1 = LabeledStackView(posX = 750, posY = 670, "OpenCard 1").apply {
        onMouseClicked = {
            if(duringSwapOne && openCardsAreClickable && player1OpenCard1IsClickable) {
                middleCardsAreClickable = true
                openCardsAreClickable = false
                swapOneHandCardIndex = 0

            }
            rootService.currentGame?.let { game ->

            }
        }
    }
    private val player1OpenCard2 = LabeledStackView(posX = 900, posY = 670, "OpenCard 2").apply {
        onMouseClicked = {
            if(duringSwapOne && openCardsAreClickable && player1OpenCard2IsClickable) {
                middleCardsAreClickable = true
                openCardsAreClickable = false
                swapOneHandCardIndex = 1

            }
        }
    }
    private val player1OpenCard3 = LabeledStackView(posX = 1050, posY = 670, "OpenCard 3").apply {
        onMouseClicked = {
            if(duringSwapOne && openCardsAreClickable && player1OpenCard3IsClickable) {
                middleCardsAreClickable = true
                openCardsAreClickable = false
                swapOneHandCardIndex = 2

            }
            rootService.currentGame?.let { game ->

            }
        }
    }
    private val player1HiddenCard1 = LabeledStackView(posX = 825, posY = 875, "Hidden Card 1").apply {
        onMouseClicked = {
            rootService.currentGame?.let { game ->

            }
        }

    }

    private val player1HiddenCard2 = LabeledStackView(posX = 975, posY = 875, "Hidden Card 2").apply {
        onMouseClicked = {
            rootService.currentGame?.let { game ->

            }
        }

    }

    // Player 2 open cards
    private val player2OpenCard1 = LabeledStackView(posX = 750, posY = 210, "OpenCard 1",180).apply {
        onMouseClicked = {
            if(duringSwapOne && openCardsAreClickable && player2OpenCard1IsClickable) {
                middleCardsAreClickable = true
                openCardsAreClickable = false
                swapOneHandCardIndex = 0

            }
        }
    }
    private val player2OpenCard2 = LabeledStackView(posX = 900, posY = 210, "OpenCard 2",180).apply {
        onMouseClicked = {
            if(duringSwapOne && openCardsAreClickable && player2OpenCard2IsClickable) {
                middleCardsAreClickable = true
                openCardsAreClickable = false
                swapOneHandCardIndex = 1

            }
        }
    }
    private val player2OpenCard3 = LabeledStackView(posX = 1050, posY = 210, "OpenCard 3",180).apply {
        onMouseClicked = {
            if(duringSwapOne && openCardsAreClickable && player2OpenCard3IsClickable) {
                middleCardsAreClickable = true
                openCardsAreClickable = false
                swapOneHandCardIndex = 2

            }
        }
    }
    private val player2HiddenCard1 = LabeledStackView(posX = 825, posY = 5, "Hidden Card 1",180).apply {
        onMouseClicked = {
            rootService.currentGame?.let { game ->

            }
        }

    }

    private val player2HiddenCard2 = LabeledStackView(posX = 975, posY = 5, "Hidden Card 2",180).apply {
        onMouseClicked = {
            rootService.currentGame?.let { game ->

            }
        }

    }
    // Player 3 open cards
    private val player3OpenCard1 = LabeledStackView(posX = 245, posY = 290, "OpenCard 1",90).apply {
        onMouseClicked = {
            if(duringSwapOne && openCardsAreClickable && player3OpenCard1IsClickable) {
                middleCardsAreClickable = true
                openCardsAreClickable = false
                swapOneHandCardIndex = 0

            }
        }
    }
    private val player3OpenCard2 = LabeledStackView(posX = 245, posY = 440, "OpenCard 2",90).apply {
        onMouseClicked = {
            if(duringSwapOne && openCardsAreClickable && player3OpenCard2IsClickable) {
                middleCardsAreClickable = true
                openCardsAreClickable = false
                swapOneHandCardIndex = 1

            }
        }
    }
    private val player3OpenCard3 = LabeledStackView(posX = 245, posY = 590, "OpenCard3",90).apply {
        onMouseClicked = {
            if(duringSwapOne && openCardsAreClickable && player3OpenCard3IsClickable) {
                middleCardsAreClickable = true
                openCardsAreClickable = false
                swapOneHandCardIndex = 2

            }
        }
    }
    private val player3HiddenCard1 = LabeledStackView(posX = 40, posY = 365, "Hidden Card 1",90).apply {
        onMouseClicked = {
            rootService.currentGame?.let { game ->

            }
        }

    }

    private val player3HiddenCard2 = LabeledStackView(posX = 40, posY = 515, "Hidden Card 2",90).apply {
        onMouseClicked = {
            rootService.currentGame?.let { game ->

            }
        }

    }
    // Player 4 open cards
    private val player4OpenCard1 = LabeledStackView(posX = 1545, posY = 290, "OpenCard 1",270).apply {
        onMouseClicked = {
            if(duringSwapOne && openCardsAreClickable && player4OpenCard1IsClickable) {
                middleCardsAreClickable = true
                openCardsAreClickable = false
                swapOneHandCardIndex = 0

            }
        }
    }
    private val player4OpenCard2 = LabeledStackView(posX = 1545, posY = 440, "OpenCard 2",270).apply {
        onMouseClicked = {
            if(duringSwapOne && openCardsAreClickable && player4OpenCard2IsClickable) {
                middleCardsAreClickable = true
                openCardsAreClickable = false
                swapOneHandCardIndex = 1

            }
        }
    }
    private val player4OpenCard3 = LabeledStackView(posX = 1545, posY = 590, "OpenCard3",270).apply {
        onMouseClicked = {
            if(duringSwapOne && openCardsAreClickable && player4OpenCard3IsClickable) {
                middleCardsAreClickable = true
                openCardsAreClickable = false
                swapOneHandCardIndex = 2

            }
        }
    }
    private val player4HiddenCard1 = LabeledStackView(posX = 1750, posY = 365, "Hidden Card 1",270).apply {
        onMouseClicked = {
            rootService.currentGame?.let { game ->

            }
        }

    }

    private val player4HiddenCard2 = LabeledStackView(posX = 1750, posY = 515, "Hidden Card 2",270).apply {
        onMouseClicked = {
            rootService.currentGame?.let { game ->

            }
        }

    }

    //Middle Cards
    private val MiddleCard1 = LabeledStackView(posX = 750, posY = 440, "Middle Card 1").apply {
        onMouseClicked = {
            if(duringSwapOne && middleCardsAreClickable) {
                middleCardsAreClickable = false
                swapOneMiddleCardIndex = 0
                rootService.playerActionService.swapOne(swapOneHandCardIndex,swapOneMiddleCardIndex)
            }
        }
    }
    private val MiddleCard2 = LabeledStackView(posX = 900, posY = 440, "Middle Card 2").apply {
        onMouseClicked = {
            if(duringSwapOne && middleCardsAreClickable) {
                middleCardsAreClickable = false
                swapOneMiddleCardIndex = 1
                rootService.playerActionService.swapOne(swapOneHandCardIndex,swapOneMiddleCardIndex)
            }
        }
    }
    private val MiddleCard3 = LabeledStackView(posX = 1050, posY = 440, "Middle Card 3").apply {
        onMouseClicked = {
            if(duringSwapOne && middleCardsAreClickable) {
                middleCardsAreClickable = false
                swapOneMiddleCardIndex = 2
                rootService.playerActionService.swapOne(swapOneHandCardIndex,swapOneMiddleCardIndex)
            }
        }
    }

    //Push Buttons
    val pushLeft = Button(
        width = 90, height = 90,
        posX = 650, posY = 500,
        text = "Push Left"
    ).apply {
        visual = ColorVisual.YELLOW
        onMouseClicked = {
            leftPushClicked = true
            rootService.playerActionService.pushLeft()
        }
    }

    val pushRight = Button(
        width = 90, height = 90,
        posX = 1190, posY = 500,
        text = "Push Right"
    ).apply {
        visual = ColorVisual.YELLOW
        onMouseClicked = {
            onMouseClicked = {
                rightPushClicked = true
                rootService.playerActionService.pushRight()
            }
        }
    }

    //Draw And DisCard Decks.
    private val drawStack = LabeledStackView(posX = 10, posY = 875, "Draw Stack").apply {
        onMouseClicked = {
            rootService.currentGame?.let { game ->

            }
        }
    }
    private val discardStack = LabeledStackView(posX = 150, posY = 875, "Discard Stack").apply {
        onMouseClicked = {
            rootService.currentGame?.let { game ->

            }
        }
    }
    //Swap, Swap All, End Turn Buttons.
    val swap = Button(
        width = 180, height = 90,
        posX = 1700, posY = 775,
        text = "Swap"
    ).apply {
        onMouseClicked = {
            duringSwapOne = true
            this.isDisabled = true
            swapAll.isDisabled = true
            endTurn.isDisabled = true
        }
        visual = ColorVisual.GRAY
    }
    val swapAll = Button(
        width = 180, height = 90,
        posX = 1700, posY = 875,
        text = "Swap All"
    ).apply {
        visual = ColorVisual.GRAY
        onMouseClicked = {
            swapAllClicked = true
            rootService.playerActionService.swapAll()
        }
    }
    val endTurn = Button(
        width = 180, height = 90,
        posX = 1700, posY = 975,
        text = "End Turn"
    ).apply {
        visual = ColorVisual.GRAY
        onMouseClicked = {
            rootService.playerActionService.endTurn()
            this.isDisabled = true
        }

    }

    private val roundLabel = Label(
        posX = 1600,
        posY = 10,
        width = 300,
        height = 200,
        font = Font(size = 30),
        text = "Remaining Rounds: ",
        alignment = Alignment.CENTER_LEFT,
        isWrapText = true

    )

    private val handValueLabel = Label(
        posX = 10,
        posY = 10,
        width = 300,
        height = 200,
        font = Font(size = 30),
        text = "Hand Value: ",
        alignment = Alignment.CENTER_LEFT,
        isWrapText = true

    )


    init {
        background = ColorVisual(108, 168, 59)
        addComponents(
            player1OpenCard1,
            player1OpenCard2,
            player1OpenCard3,
            player1HiddenCard1,
            player1HiddenCard2,
            player2OpenCard1,
            player2OpenCard2,
            player2OpenCard3,
            player2HiddenCard1,
            player2HiddenCard2,
            player3OpenCard1,
            player3OpenCard2,
            player3OpenCard3,
            player3HiddenCard1,
            player3HiddenCard2,
            player4OpenCard1,
            player4OpenCard2,
            player4OpenCard3,
            player4HiddenCard1,
            player4HiddenCard2,
            MiddleCard1,
            MiddleCard2,
            MiddleCard3,
            pushLeft,
            pushRight,
            discardStack,
            drawStack,
            swap,
            swapAll,
            endTurn,
            roundLabel,
            handValueLabel
        )
        /**
         * These are disabled. Since player
         * has to do push first.
         */
        swap.isDisabled = true
        swapAll.isDisabled = true
        endTurn.isDisabled = true
    }
    /**
     * structure to hold pairs of (card, cardView) that can be used
     *
     * 1. To identify the appropriate view for a card when it is updated by
     * a refresh method (known as forward lookup).
     *
     * 2. To determine the specific card associated with a user interface
     * event occurring on a view (referred to as backward lookup).
     */
    private val cardMap: BidirectionalMap<Card, CardView> = BidirectionalMap()
    override fun refreshAfterStartNewGame() {
        val game = rootService.currentGame
        checkNotNull(game) { "No started game found." }

        cardMap.clear()

        val cardImageLoader = CardImageLoader()

        //Initializing GUI of draw- and discardStack.
        game.drawStack?.let { initializeStackView(it, drawStack, cardImageLoader) }
        game.discardStack?.let { initializeStackView(it, discardStack, cardImageLoader) }

        //Initializing GUI of Middle Cards.
        initializeCardView(game.tableCards!![0],MiddleCard1,CardImageLoader())
        initializeCardView(game.tableCards!![1],MiddleCard2,CardImageLoader())
        initializeCardView(game.tableCards!![2],MiddleCard3,CardImageLoader())

        //Initializing GUI of open- and hidden cards.
        for (i in 0 until game.players.size) {
            when (i) {
                0 -> {
                    initializeCardView(game.players[i].openCards[0],player1OpenCard1,CardImageLoader())
                    initializeCardView(game.players[i].openCards[1],player1OpenCard2,CardImageLoader())
                    initializeCardView(game.players[i].openCards[2],player1OpenCard3,CardImageLoader())
                    initializeCardView(game.players[i].hiddenCards[0],player1HiddenCard1,CardImageLoader())
                    initializeCardView(game.players[i].hiddenCards[1],player1HiddenCard2,CardImageLoader())
                }
                1 -> {
                    initializeCardView(game.players[i].openCards[0],player2OpenCard1,CardImageLoader())
                    initializeCardView(game.players[i].openCards[1],player2OpenCard2,CardImageLoader())
                    initializeCardView(game.players[i].openCards[2],player2OpenCard3,CardImageLoader())
                    initializeCardView(game.players[i].hiddenCards[0],player2HiddenCard1,CardImageLoader())
                    initializeCardView(game.players[i].hiddenCards[1],player2HiddenCard2,CardImageLoader())
                }
                2 -> {
                    initializeCardView(game.players[i].openCards[0],player3OpenCard1,CardImageLoader())
                    initializeCardView(game.players[i].openCards[1],player3OpenCard2,CardImageLoader())
                    initializeCardView(game.players[i].openCards[2],player3OpenCard3,CardImageLoader())
                    initializeCardView(game.players[i].hiddenCards[0],player3HiddenCard1,CardImageLoader())
                    initializeCardView(game.players[i].hiddenCards[1],player3HiddenCard2,CardImageLoader())
                }
                3 -> {
                    initializeCardView(game.players[i].openCards[0],player4OpenCard1,CardImageLoader())
                    initializeCardView(game.players[i].openCards[1],player4OpenCard2,CardImageLoader())
                    initializeCardView(game.players[i].openCards[2],player4OpenCard3,CardImageLoader())
                    initializeCardView(game.players[i].hiddenCards[0],player4HiddenCard1,CardImageLoader())
                    initializeCardView(game.players[i].hiddenCards[1],player4HiddenCard2,CardImageLoader())
                }
            }

        }
        /**
         *for swapOne
         */
        player1OpenCard1IsClickable = true
        player1OpenCard2IsClickable = true
        player1OpenCard3IsClickable = true
        pushLeft.isDisabled = false
        pushRight.isDisabled = false
        swap.isDisabled = true
        swapAll.isDisabled = true
        endTurn.isDisabled = true
        handValueLabel.text = "Hand Value: " + game.players[game.currentPlayer].handResult
        roundLabel.text = "Remaining Rounds: " + game.roundsLeft

    }

    /**
     * clears [stackView], adds a new [CardView] for each
     * element of [stack] onto it, and adds the newly created view/card pair
     * to the global [cardMap].
     */
    private fun initializeStackView(stack: CardStack, stackView: LabeledStackView, cardImageLoader: CardImageLoader) {
        stackView.clear()
        stack.cards.reversed().forEach { card ->
            val cardView = CardView(
                height = 200,
                width = 130,
                front = ImageVisual(cardImageLoader.frontImageFor(card.cardsuit, card.cardValue)),
                back = ImageVisual(cardImageLoader.backImage)
            )
            stackView.add(cardView)
            cardMap.add(card to cardView)
        }
    }

    private fun initializeCardView(card1: Card, stackView: LabeledStackView, cardImageLoader: CardImageLoader) {
        stackView.clear()
        card1.let { card ->
            val cardView = CardView(
                height = 200,
                width = 130,
                front = ImageVisual(cardImageLoader.frontImageFor(card.cardsuit, card.cardValue)),
                back = ImageVisual(cardImageLoader.backImage)
            )
            if(stackView == player2HiddenCard1 || stackView == player2HiddenCard2 ||
                stackView == player3HiddenCard1 || stackView == player3HiddenCard2 ||
                stackView == player4HiddenCard1 || stackView == player4HiddenCard2) {
                cardView.showCardSide(CardView.CardSide.BACK)

            }
            else {
                cardView.showCardSide(CardView.CardSide.FRONT)
            }

            stackView.add(cardView)
            cardMap.add(card to cardView)
        }
    }

    private var leftPushClicked = false
    private var rightPushClicked = false
    private var swapAllClicked = false
    override fun refreshTableCards() {
        pushLeft.isDisabled = true
        pushRight.isDisabled = true

        //To check if leftPush or Right Push done.
        if(leftPushClicked && !rightPushClicked) {
            refreshCardsTablePushLeft()
            leftPushClicked = false
            rightPushClicked = false

            swap.isDisabled = false
            swapAll.isDisabled = false
            endTurn.isDisabled = false
        }
        else if(!leftPushClicked && rightPushClicked) {
            refreshCardsTablePushRight()
            leftPushClicked = false
            rightPushClicked = false

            swap.isDisabled = false
            swapAll.isDisabled = false
            endTurn.isDisabled = false
        }
        //To check if swap One done.
        else if(duringSwapOne) {

            executeSwapOneForGUI()

            duringSwapOne = false
            endTurn.isDisabled = false
        }
        else if(swapAllClicked) {
            refreshCardsTableSwapAll()
            swapAllClicked = false

            swap.isDisabled = true
            swapAll.isDisabled = true
        }

        //checkAllStackViews(game)
    }

    /****************************************
     * swapOne() related variables and functions
     * **************************************
     */

    private var swapOneHandCardIndex: Int = -1
    private var swapOneMiddleCardIndex: Int = -1
    private var duringSwapOne = false
    private var openCardsAreClickable = true
    private var middleCardsAreClickable = false
    private var player1OpenCard1IsClickable = false
    private var player1OpenCard2IsClickable = false
    private var player1OpenCard3IsClickable = false
    private var player2OpenCard1IsClickable = false
    private var player2OpenCard2IsClickable = false
    private var player2OpenCard3IsClickable = false
    private var player3OpenCard1IsClickable = false
    private var player3OpenCard2IsClickable = false
    private var player3OpenCard3IsClickable = false
    private var player4OpenCard1IsClickable = false
    private var player4OpenCard2IsClickable = false
    private var player4OpenCard3IsClickable = false

    private var player1OpenCard1IsClicked = false
    private var player1OpenCard2IsClicked = false
    private var player1OpenCard3IsClicked = false
    private var player2OpenCard1IsClicked = false
    private var player2OpenCard2IsClicked = false
    private var player2OpenCard3IsClicked = false
    private var player3OpenCard1IsClicked = false
    private var player3OpenCard2IsClicked = false
    private var player3OpenCard3IsClicked = false
    private var player4OpenCard1IsClicked = false
    private var player4OpenCard2IsClicked = false
    private var player4OpenCard3IsClicked = false

    private var middleCard1IsClicked = false
    private var middleCard2IsClicked = false
    private var middleCard3IsClicked = false
    private fun executeSwapOneForGUI() {
        val game = rootService.currentGame
        checkNotNull(game) { "No game found." }

        when (game.currentPlayer) {
            0 -> {
                when (swapOneHandCardIndex) {
                    0 -> {
                        val openCard1 = player1OpenCard1.pop()
                        when (swapOneMiddleCardIndex) {
                            0 -> {
                                moveCardView(MiddleCard1.peek(), player1OpenCard1, false)
                                moveCardViewForSwap(openCard1, MiddleCard1, false)
                            }
                            1 -> {
                                moveCardView(MiddleCard2.peek(), player1OpenCard1, false)
                                moveCardViewForSwap(openCard1, MiddleCard2, false)
                            }
                            2 -> {
                                moveCardView(MiddleCard3.peek(), player1OpenCard1, false)
                                moveCardViewForSwap(openCard1, MiddleCard3, false)
                            }
                        }
                    }
                    1 -> {
                        val openCard2 = player1OpenCard2.pop()
                        when (swapOneMiddleCardIndex) {
                            0 -> {
                                moveCardView(MiddleCard1.peek(), player1OpenCard2, false)
                                moveCardViewForSwap(openCard2, MiddleCard1, false)
                            }
                            1 -> {
                                moveCardView(MiddleCard2.peek(), player1OpenCard2, false)
                                moveCardViewForSwap(openCard2, MiddleCard2, false)
                            }
                            2 -> {
                                moveCardView(MiddleCard3.peek(), player1OpenCard2, false)
                                moveCardViewForSwap(openCard2, MiddleCard3, false)
                            }
                        }
                    }
                    2 -> {
                        val openCard3 = player1OpenCard3.pop()
                        when (swapOneMiddleCardIndex) {
                            0 -> {
                                moveCardView(MiddleCard1.peek(), player1OpenCard3, false)
                                moveCardViewForSwap(openCard3, MiddleCard1, false)
                            }
                            1 -> {
                                moveCardView(MiddleCard2.peek(), player1OpenCard3, false)
                                moveCardViewForSwap(openCard3, MiddleCard2, false)
                            }
                            2 -> {
                                moveCardView(MiddleCard3.peek(), player1OpenCard3, false)
                                moveCardViewForSwap(openCard3, MiddleCard3, false)
                            }
                        }
                    }
                }
            }
            1 -> {
                when (swapOneHandCardIndex) {
                    0 -> {
                        val openCard1 = player2OpenCard1.pop()
                        when (swapOneMiddleCardIndex) {
                            0 -> {
                                moveCardView(MiddleCard1.peek(), player2OpenCard1, false)
                                moveCardViewForSwap(openCard1, MiddleCard1, false)
                            }
                            1 -> {
                                moveCardView(MiddleCard2.peek(), player2OpenCard1, false)
                                moveCardViewForSwap(openCard1, MiddleCard2, false)
                            }
                            2 -> {
                                moveCardView(MiddleCard3.peek(), player2OpenCard1, false)
                                moveCardViewForSwap(openCard1, MiddleCard3, false)
                            }
                        }
                    }
                    1 -> {
                        val openCard2 = player2OpenCard2.pop()
                        when (swapOneMiddleCardIndex) {
                            0 -> {
                                moveCardView(MiddleCard1.peek(), player2OpenCard2, false)
                                moveCardViewForSwap(openCard2, MiddleCard1, false)
                            }
                            1 -> {
                                moveCardView(MiddleCard2.peek(), player2OpenCard2, false)
                                moveCardViewForSwap(openCard2, MiddleCard2, false)
                            }
                            2 -> {
                                moveCardView(MiddleCard3.peek(), player2OpenCard2, false)
                                moveCardViewForSwap(openCard2, MiddleCard3, false)
                            }
                        }
                    }
                    2 -> {
                        val openCard3 = player2OpenCard3.pop()
                        when (swapOneMiddleCardIndex) {
                            0 -> {
                                moveCardView(MiddleCard1.peek(), player2OpenCard3, false)
                                moveCardViewForSwap(openCard3, MiddleCard1, false)
                            }
                            1 -> {
                                moveCardView(MiddleCard2.peek(), player2OpenCard3, false)
                                moveCardViewForSwap(openCard3, MiddleCard2, false)
                            }
                            2 -> {
                                moveCardView(MiddleCard3.peek(), player2OpenCard3, false)
                                moveCardViewForSwap(openCard3, MiddleCard3, false)
                            }
                        }
                    }
                }
            }
            2 -> {
                when (swapOneHandCardIndex) {
                    0 -> {
                        val openCard1 = player3OpenCard1.pop()
                        when (swapOneMiddleCardIndex) {
                            0 -> {
                                moveCardView(MiddleCard1.peek(), player3OpenCard1, false)
                                moveCardViewForSwap(openCard1, MiddleCard1, false)
                            }
                            1 -> {
                                moveCardView(MiddleCard2.peek(), player3OpenCard1, false)
                                moveCardViewForSwap(openCard1, MiddleCard2, false)
                            }
                            2 -> {
                                moveCardView(MiddleCard3.peek(), player3OpenCard1, false)
                                moveCardViewForSwap(openCard1, MiddleCard3, false)
                            }
                        }
                    }
                    1 -> {
                        val openCard2 = player3OpenCard2.pop()
                        when (swapOneMiddleCardIndex) {
                            0 -> {
                                moveCardView(MiddleCard1.peek(), player3OpenCard2, false)
                                moveCardViewForSwap(openCard2, MiddleCard1, false)
                            }
                            1 -> {
                                moveCardView(MiddleCard2.peek(), player3OpenCard2, false)
                                moveCardViewForSwap(openCard2, MiddleCard2, false)
                            }
                            2 -> {
                                moveCardView(MiddleCard3.peek(), player3OpenCard2, false)
                                moveCardViewForSwap(openCard2, MiddleCard3, false)
                            }
                        }
                    }
                    2 -> {
                        val openCard3 = player3OpenCard3.pop()
                        when (swapOneMiddleCardIndex) {
                            0 -> {
                                moveCardView(MiddleCard1.peek(), player3OpenCard3, false)
                                moveCardViewForSwap(openCard3, MiddleCard1, false)
                            }
                            1 -> {
                                moveCardView(MiddleCard2.peek(), player3OpenCard3, false)
                                moveCardViewForSwap(openCard3, MiddleCard2, false)
                            }
                            2 -> {
                                moveCardView(MiddleCard3.peek(), player3OpenCard3, false)
                                moveCardViewForSwap(openCard3, MiddleCard3, false)
                            }
                        }
                    }
                }
            }
            3 -> {
                when (swapOneHandCardIndex) {
                    0 -> {
                        val openCard1 = player4OpenCard1.pop()
                        when (swapOneMiddleCardIndex) {
                            0 -> {
                                moveCardView(MiddleCard1.peek(), player4OpenCard1, false)
                                moveCardViewForSwap(openCard1, MiddleCard1, false)
                            }
                            1 -> {
                                moveCardView(MiddleCard2.peek(), player4OpenCard1, false)
                                moveCardViewForSwap(openCard1, MiddleCard2, false)
                            }
                            2 -> {
                                moveCardView(MiddleCard3.peek(), player4OpenCard1, false)
                                moveCardViewForSwap(openCard1, MiddleCard3, false)
                            }
                        }
                    }
                    1 -> {
                        val openCard2 = player4OpenCard2.pop()
                        when (swapOneMiddleCardIndex) {
                            0 -> {
                                moveCardView(MiddleCard1.peek(), player4OpenCard2, false)
                                moveCardViewForSwap(openCard2, MiddleCard1, false)
                            }
                            1 -> {
                                moveCardView(MiddleCard2.peek(), player4OpenCard2, false)
                                moveCardViewForSwap(openCard2, MiddleCard2, false)
                            }
                            2 -> {
                                moveCardView(MiddleCard3.peek(), player4OpenCard2, false)
                                moveCardViewForSwap(openCard2, MiddleCard3, false)
                            }
                        }
                    }
                    2 -> {
                        val openCard3 = player4OpenCard3.pop()
                        when (swapOneMiddleCardIndex) {
                            0 -> {
                                moveCardView(MiddleCard1.peek(), player4OpenCard3, false)
                                moveCardViewForSwap(openCard3, MiddleCard1, false)
                            }
                            1 -> {
                                moveCardView(MiddleCard2.peek(), player4OpenCard3, false)
                                moveCardViewForSwap(openCard3, MiddleCard2, false)
                            }
                            2 -> {
                                moveCardView(MiddleCard3.peek(), player4OpenCard3, false)
                                moveCardViewForSwap(openCard3, MiddleCard3, false)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun refreshHandValue(player: Player) {
        val game = rootService.currentGame
        checkNotNull(game) { "No game found." }
        handValueLabel.text = "Hand Value: " + game.players[game.currentPlayer].handResult
    }
    override fun refreshAfterStartTurn(player: Player) {
        val game = rootService.currentGame
        checkNotNull(game) { "No game found." }

        when (game.currentPlayer) {
            0 -> {
                player1OpenCard1IsClickable = true
                player1OpenCard2IsClickable = true
                player1OpenCard3IsClickable = true
                when (game.players.size) {
                    4 -> {
                        player4HiddenCard1.peek().showCardSide(CardView.CardSide.BACK)
                        player4HiddenCard2.peek().showCardSide(CardView.CardSide.BACK)
                    }
                    3 -> {
                        player3HiddenCard1.peek().showCardSide(CardView.CardSide.BACK)
                        player3HiddenCard2.peek().showCardSide(CardView.CardSide.BACK)
                    }
                    2 -> {
                        player2HiddenCard1.peek().showCardSide(CardView.CardSide.BACK)
                        player2HiddenCard2.peek().showCardSide(CardView.CardSide.BACK)
                    }
                }
                player1HiddenCard1.peek().showCardSide(CardView.CardSide.FRONT)
                player1HiddenCard2.peek().showCardSide(CardView.CardSide.FRONT)
            }
            1 -> {
                player2OpenCard1IsClickable = true
                player2OpenCard2IsClickable = true
                player2OpenCard3IsClickable = true
                player1HiddenCard1.peek().showCardSide(CardView.CardSide.BACK)
                player1HiddenCard2.peek().showCardSide(CardView.CardSide.BACK)
                player2HiddenCard1.peek().showCardSide(CardView.CardSide.FRONT)
                player2HiddenCard2.peek().showCardSide(CardView.CardSide.FRONT)


            }
            2 -> {
                player3OpenCard1IsClickable = true
                player3OpenCard2IsClickable = true
                player3OpenCard3IsClickable = true
                player2HiddenCard1.peek().showCardSide(CardView.CardSide.BACK)
                player2HiddenCard2.peek().showCardSide(CardView.CardSide.BACK)
                if(game.players.size >= 3) {
                    player3HiddenCard1.peek().showCardSide(CardView.CardSide.FRONT)
                    player3HiddenCard2.peek().showCardSide(CardView.CardSide.FRONT)
                }

            }
            3 -> {
                player4OpenCard1IsClickable = true
                player4OpenCard2IsClickable = true
                player4OpenCard3IsClickable = true
                player3HiddenCard1.peek().showCardSide(CardView.CardSide.BACK)
                player3HiddenCard2.peek().showCardSide(CardView.CardSide.BACK)
                if(game.players.size  == 4) {
                    player4HiddenCard1.peek().showCardSide(CardView.CardSide.FRONT)
                    player4HiddenCard2.peek().showCardSide(CardView.CardSide.FRONT)
                }
            }
        }

        handValueLabel.text ="Hand Value: " + game.players[game.currentPlayer].handResult

        pushLeft.isDisabled = false
        pushRight.isDisabled = false
        swap.isDisabled = true
        swapAll.isDisabled = true
        endTurn.isDisabled = true
        middleCardsAreClickable = false
    }

    /****************************************
     * swapOne() related variables and functions
     * **************************************
     */
    private fun refreshCardsTableSwapAll() {
        val game = rootService.currentGame
        checkNotNull(game) { "No game found." }

        if(game.currentPlayer == 0) {
            var openCard1 = player1OpenCard1.pop()
            var openCard2 = player1OpenCard2.pop()
            var openCard3 = player1OpenCard3.pop()


            moveCardView(MiddleCard1.peek(), player1OpenCard1, false)
            moveCardView(MiddleCard2.peek(), player1OpenCard2, false)
            moveCardView(MiddleCard3.peek(), player1OpenCard3, false)
            moveCardViewForSwap(openCard1, MiddleCard1, false)
            moveCardViewForSwap(openCard2, MiddleCard2, false)
            moveCardViewForSwap(openCard3, MiddleCard3, false)
        }
        else if(game.currentPlayer == 1) {
            var openCard1 = player2OpenCard1.pop()
            var openCard2 = player2OpenCard2.pop()
            var openCard3 = player2OpenCard3.pop()


            moveCardView(MiddleCard1.peek(), player2OpenCard1, false)
            moveCardView(MiddleCard2.peek(), player2OpenCard2, false)
            moveCardView(MiddleCard3.peek(), player2OpenCard3, false)
            moveCardViewForSwap(openCard1, MiddleCard1, false)
            moveCardViewForSwap(openCard2, MiddleCard2, false)
            moveCardViewForSwap(openCard3, MiddleCard3, false)
        }
        else if(game.currentPlayer == 2) {
            var openCard1 = player3OpenCard1.pop()
            var openCard2 = player3OpenCard2.pop()
            var openCard3 = player3OpenCard3.pop()


            moveCardView(MiddleCard1.peek(), player3OpenCard1, false)
            moveCardView(MiddleCard2.peek(), player3OpenCard2, false)
            moveCardView(MiddleCard3.peek(), player3OpenCard3, false)
            moveCardViewForSwap(openCard1, MiddleCard1, false)
            moveCardViewForSwap(openCard2, MiddleCard2, false)
            moveCardViewForSwap(openCard3, MiddleCard3, false)
        }
        else if(game.currentPlayer == 3) {
            var openCard1 = player4OpenCard1.pop()
            var openCard2 = player4OpenCard2.pop()
            var openCard3 = player4OpenCard3.pop()


            moveCardView(MiddleCard1.peek(), player4OpenCard1, false)
            moveCardView(MiddleCard2.peek(), player4OpenCard2, false)
            moveCardView(MiddleCard3.peek(), player4OpenCard3, false)
            moveCardViewForSwap(openCard1, MiddleCard1, false)
            moveCardViewForSwap(openCard2, MiddleCard2, false)
            moveCardViewForSwap(openCard3, MiddleCard3, false)
        }
    }


    private fun refreshCardsTablePushLeft() {
        val game = rootService.currentGame
        checkNotNull(game) { "No game found." }

        //Push Left
        moveCardView(MiddleCard1.peek(), discardStack, false)
        moveCardView(MiddleCard2.peek(), MiddleCard1, false)
        moveCardView(MiddleCard3.peek(), MiddleCard2, false)
        moveCardView(drawStack.peek(), MiddleCard3, true)
    }

    private fun refreshCardsTablePushRight() {
        val game = rootService.currentGame
        checkNotNull(game) { "No game found." }

        //Push Right
        moveCardView(MiddleCard3.peek(), discardStack, false)
        moveCardView(MiddleCard2.peek(), MiddleCard3, false)
        moveCardView(MiddleCard1.peek(), MiddleCard2, false)
        moveCardView(drawStack.peek(), MiddleCard1, true)
    }

    private fun moveCardView(cardView: CardView, toStack: LabeledStackView, flip: Boolean = false) {
        if (flip) {
            when (cardView.currentSide) {
                CardView.CardSide.BACK -> cardView.showFront()
                CardView.CardSide.FRONT -> cardView.showBack()
            }
        }
        cardView.removeFromParent()
        toStack.add(cardView)
    }

    private fun moveCardViewForSwap(cardView: CardView, toStack: LabeledStackView, flip: Boolean = false) {
        if (flip) {
            when (cardView.currentSide) {
                CardView.CardSide.BACK -> cardView.showFront()
                CardView.CardSide.FRONT -> cardView.showBack()
            }
        }
        toStack.add(cardView)
    }



    override fun refreshAfterEndTurn() {
        val game = rootService.currentGame
        checkNotNull(game) { "No started game found." }
        player1OpenCard1IsClickable = false
        player1OpenCard2IsClickable = false
        player1OpenCard3IsClickable = false
        player2OpenCard1IsClickable = false
        player2OpenCard2IsClickable = false
        player2OpenCard3IsClickable = false
        player3OpenCard1IsClickable = false
        player3OpenCard2IsClickable = false
        player3OpenCard3IsClickable = false
        player4OpenCard1IsClickable = false
        player4OpenCard2IsClickable = false
        player4OpenCard3IsClickable = false
        openCardsAreClickable = true
        middleCardsAreClickable = false
        player1OpenCard2IsClicked = false
        player1OpenCard1IsClicked = false
        player1OpenCard3IsClicked = false
        player2OpenCard1IsClicked = false
        player2OpenCard2IsClicked = false
        player2OpenCard3IsClicked = false
        player3OpenCard1IsClicked = false
        player3OpenCard2IsClicked = false
        player3OpenCard3IsClicked = false
        player4OpenCard1IsClicked = false
        player4OpenCard2IsClicked = false
        player4OpenCard3IsClicked = false

        middleCard1IsClicked = false
        middleCard2IsClicked = false
        middleCard3IsClicked = false
        swapOneHandCardIndex = -1
        swapOneMiddleCardIndex = -1
        handValueLabel.text = "Hand Value: " + game.players[game.currentPlayer].handResult
        roundLabel.text = "Remaining Rounds:" + game.roundsLeft
    }

    override fun refreshAfterEndRound(roundsLeft: Int) {
        val game = rootService.currentGame
        checkNotNull(game) { "No started game found." }
        player1OpenCard1IsClickable = false
        player1OpenCard2IsClickable = false
        player1OpenCard3IsClickable = false
        player2OpenCard1IsClickable = false
        player2OpenCard2IsClickable = false
        player2OpenCard3IsClickable = false
        player3OpenCard1IsClickable = false
        player3OpenCard2IsClickable = false
        player3OpenCard3IsClickable = false
        player4OpenCard1IsClickable = false
        player4OpenCard2IsClickable = false
        player4OpenCard3IsClickable = false
        openCardsAreClickable = true
        middleCardsAreClickable = false
        player1OpenCard2IsClicked = false
        player1OpenCard1IsClicked = false
        player1OpenCard3IsClicked = false
        player2OpenCard1IsClicked = false
        player2OpenCard2IsClicked = false
        player2OpenCard3IsClicked = false
        player3OpenCard1IsClicked = false
        player3OpenCard2IsClicked = false
        player3OpenCard3IsClicked = false
        player4OpenCard1IsClicked = false
        player4OpenCard2IsClicked = false
        player4OpenCard3IsClicked = false

        middleCard1IsClicked = false
        middleCard2IsClicked = false
        middleCard3IsClicked = false
        swapOneHandCardIndex = -1
        swapOneMiddleCardIndex = -1
        roundLabel.text = "Remaining Rounds:" + roundsLeft
        handValueLabel.text = "Hand Value: " + game.players[game.currentPlayer].handResult

    }
}