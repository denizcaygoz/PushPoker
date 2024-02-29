package view

import service.RootService
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.ComboBox
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.TextField
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual

class MainMenuScene(private val rootService: RootService) : MenuScene(400, 1080), Refreshable {
    //is used to store player names.
    private var playerNames: MutableList<String> = mutableListOf("Player1","Player2")
    private var round: Int = 2

    private val headlineLabel = Label(
        width = 300, height = 50, posX = 50, posY = 30,
        text = "Push Poker",
        font = Font(size = 35)
    )

    private val headlineLabel2 = Label(
        width = 300, height = 50, posX = 50, posY = 80,
        text = "Main Menu",
        font = Font(size = 16)
    )

    private val p1Label = Label(
        width = 100, height = 35,
        posX = 50, posY = 125,
        text = "Player 1:"
    )

    // type inference fails here, so explicit  ": TextField" is required
    private val p1Input: TextField = TextField(
        width = 200, height = 35,
        posX = 150, posY = 125,
        text = "Player 1"
    ).apply {
        onKeyTyped = {
            startButton.isDisabled = this.text.isBlank() || p2Input.text.isBlank()
            if(this.text.isBlank()) {
                                    p3Input.isDisabled = true
                                    p3Input.text = ""
                                    p4Input.isDisabled = true
                                    p4Input.text = "" }
            else {
                if(p2Input.text.isNotBlank()) {
                    p3Input.isDisabled = true
                    p4Input.isDisabled = true
                }

            }
        }

    }

    private val p2Label = Label(
        width = 100, height = 35,
        posX = 50, posY = 170,
        text = "Player 2:"
    )

    // type inference fails here, so explicit  ": TextField" is required
    private val p2Input: TextField = TextField(
        width = 200, height = 35,
        posX = 150, posY = 170,
        text = "Player 2"
    ).apply {
        onKeyTyped = {
            p3Input.isDisabled = this.text.isBlank() || p1Input.text.isBlank()
            p4Input.isDisabled = this.text.isBlank() || p1Input.text.isBlank()
            startButton.isDisabled = p1Input.text.isBlank() || this.text.isBlank()
            inputTextCleaner()
        }
    }

    private val p3Label = Label(
        width = 100, height = 35,
        posX = 50, posY = 215,
        text = "Player 3:"
    )

    // type inference fails here, so explicit  ": TextField" is required
    private val p3Input: TextField = TextField(
        width = 200, height = 35,
        posX = 150, posY = 215,
        text = ""
    ).apply {
        if(p1Input.text.isNotBlank() && p2Input.text.isNotBlank()) {
            onKeyTyped = {
                p4Input.isDisabled = this.text.isBlank() || this.isDisabled
                if(this.text.isBlank()) {p4Input.text = ""}
            }
        }
    }
    private val p4Label = Label(
        width = 100, height = 35,
        posX = 50, posY = 260,
        text = "Player 4:"
    )

    // type inference fails here, so explicit  ": TextField" is required
    private val p4Input: TextField = TextField(
        width = 200, height = 35,
        posX = 150, posY = 260,
        text = ""
    ).apply {
        if(p3Input.text.isNotBlank()) {
            onKeyTyped = {

            }
        }
    }

    val quitButton = Button(
        width = 140, height = 35,
        posX = 50, posY = 430,
        text = "Quit"
    ).apply {
        visual = ColorVisual(221, 136, 136)
    }

    private val startButton = Button(
        width = 140, height = 35,
        posX = 210, posY = 430,
        text = "Start"
    ).apply {
        visual = ColorVisual(136, 221, 136)
        onMouseClicked = {
            addPlayersToList()
            setRound()
            rootService.gameService.startNewGame(playerNames,round)
        }
    }


    private val setRoundLabel = Label(
        width = 100, height = 35,
        posX = 50, posY = 305,
        text = "Rounds:"
    )

    private fun inputTextCleaner() {
        if(p3Input != null && p3Input.isDisabled) {
            p3Input.text =  ""
            p4Input.text =  ""
        }
    }

    private fun setRound() {

        round = roundInputBox.selectedItem!!
    }

    private fun addPlayersToList() {
        playerNames.clear()
        if (p1Input.text.isNotBlank()) {playerNames.add(p1Input.text.trim())}
        if (p2Input.text.isNotBlank()) {playerNames.add(p2Input.text.trim())}
        if (p3Input.text.isNotBlank()) {playerNames.add(p3Input.text.trim())}
        if (p4Input.text.isNotBlank()) {playerNames.add(p4Input.text.trim())}
    }


    private val roundInputBox =
        ComboBox<Int>(posX = 150, posY = 305, width = 200, prompt = "Set The Total Rounds!")



    init {
        opacity = .5
        addComponents(
            headlineLabel,
            p1Label, p1Input,
            p2Label, p2Input,
            p3Label, p3Input,
            p4Label, p4Input,
            startButton, quitButton,
            headlineLabel2,
            setRoundLabel,
            roundInputBox


        )
        roundInputBox.items = mutableListOf(2,3,4,5,6,7)
        roundInputBox.selectedItem = 2
        p4Input.isDisabled = true

    }
}