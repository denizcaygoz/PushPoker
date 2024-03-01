package view

import service.RootService
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual

/**
 * This class represents the Turn scene which pops up after the end turn of previous player.
 * The player who has turn now has to press start turn button to take the turn.
 */
class NextPlayerScene(private val rootService: RootService) : MenuScene(1920, 1080), Refreshable {

    private val headlineLabel = Label(
        width = 600, height = 150, posX = 1920/2 - 300, posY = 100,
        text = "",
        font = Font(size = 35)
    )

    private val startTurnButton = Button(
        width = 280, height = 70,
        posX = 1920/2- 150 , posY = 400,
        text = "Start Turn"
    ).apply {
        visual = ColorVisual(136, 221, 136)
        onMouseClicked = {
            rootService.playerActionService.startTurn()
        }
    }
    init {
        opacity = .5
        addComponents(
            headlineLabel,
            startTurnButton,
        )
    }

    /**
     * To show who has the following turn.
     */
     fun headlineTextGenerator() {
        val game = rootService.currentGame
        checkNotNull(game) { "No started game found." }
        val str = game.players[game.currentPlayer].name + " has the turn."
        headlineLabel.text = str
    }
    /**
     * To show the remaining rounds.
     */
    fun headlineTextRoundEndGenerator() {
        val game = rootService.currentGame
        checkNotNull(game) { "No started game found." }
        val str = "Player: " + game.players[game.currentPlayer].name + " has the turn.\n"
        val str2 = "            " + game.roundsLeft.toString() + " Round left."
        headlineLabel.text = str + str2
    }
}