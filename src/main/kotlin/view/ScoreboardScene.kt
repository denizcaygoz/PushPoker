package view

import entity.Player
import service.RootService
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual

/**
 * This class represents the score board scene at the end of the game.
 */
class ScoreboardScene(private val rootService: RootService) : MenuScene(400, 1080), Refreshable {
    var scoreboard: List<Player> = listOf()
    private val headlineLabel = Label(
        width = 300, height = 50, posX = 50, posY = 30,
        text = "Scoreboard",
        font = Font(size = 35)
    )

    private val p1Label = Label(
        width = 100, height = 35,
        posX = 50, posY = 125,
        text = "1."
    )


    private val firstPlayerLabel = Label(
        width = 200, height = 35,
        posX = 150, posY = 125,
        text = ""
    ).apply {

    }

    private val p2Label = Label(
        width = 100, height = 35,
        posX = 50, posY = 170,
        text = "2."
    )


    private val secondPlayerLabel = Label(
        width = 200, height = 35,
        posX = 150, posY = 170,
        text = ""
    )

    private val p3Label = Label(
        width = 100, height = 35,
        posX = 50, posY = 215,
        text = "3."
    )


    private val thirdPlayerLabel = Label(
        width = 200, height = 35,
        posX = 150, posY = 215,
        text = ""
    ).apply {


    }

    private val p4Label = Label(
        width = 100, height = 35,
        posX = 50, posY = 260,
        text = "4."
    )


    private val fourthPlayerLabel = Label(
        width = 200, height = 35,
        posX = 150, posY = 260,
        text = ""
    ).apply {



    }

    val goToMenuButton = Button(
        width = 140, height = 35,
        posX = 140, posY = 430,
        text = "Go To Menu"
    ).apply {
        visual = ColorVisual(221, 136, 136)

    }

     private fun generateFourthPlayerText() {
        if(scoreboard.size  == 4) {
            fourthPlayerLabel.text = scoreboard[3].name + " with " + scoreboard[3].handResult
        }
        else {
            p4Label.isDisabled = true
            fourthPlayerLabel.isDisabled = true
        }
    }

    private fun generateThirdPlayerText() {
        if(scoreboard.size >= 3) {
            thirdPlayerLabel.text = scoreboard[2].name + " with " + scoreboard[2].handResult
        }
        else {
            p3Label.isDisabled = true
            thirdPlayerLabel.isDisabled = true
        }
    }

    private fun generateSecondPlayerText() {
        secondPlayerLabel.text = scoreboard[1].name + " with " + scoreboard[1].handResult
    }

    private fun generateFirstPlayerText() {
        firstPlayerLabel.text = scoreboard[0].name + " with " + scoreboard[0].handResult
    }

    fun generateTexts() {
        generateFirstPlayerText()
        generateSecondPlayerText()
        generateThirdPlayerText()
        generateFourthPlayerText()
    }

    init {
        opacity = .5
        addComponents(
            headlineLabel,
            p1Label, firstPlayerLabel,
            p2Label, secondPlayerLabel,
            p3Label, thirdPlayerLabel,
            p4Label, fourthPlayerLabel,
            goToMenuButton,
        )
        p3Label.isDisabled = false
        thirdPlayerLabel.isDisabled = false
        p4Label.isDisabled = false
        fourthPlayerLabel.isDisabled = false


    }
}