package view

import entity.Player
import service.RootService
import tools.aqua.bgw.core.BoardGameApplication
/**
 * This class provides to test each scene in the game.
 */
/**
 * I did the whole view layer in 2-3 days so the comments in this package aren't that useful :/
 */
class PushPokerApplication : BoardGameApplication("PushPoker"),Refreshable  {
    // Central service from which all others are created/accessed
    // also holds the currently active game
    private val rootService = RootService()

    // This is where the actual game takes place
    private val inGameScene = InGameScene(rootService)

    // This menu scene is shown after each finished game (i.e. no more cards to draw)
    private val scoreboardScene = ScoreboardScene(rootService).apply {
        goToMenuButton.onMouseClicked = {
            this@PushPokerApplication.showMenuScene(mainMenuScene)
        }
    }

    // This menu scene is shown after application start and if the "new game" button
    // is clicked in the gameFinishedMenuScene
    private val mainMenuScene = MainMenuScene(rootService).apply {
        quitButton.onMouseClicked = {
            exit()
        }
    }

    private val nextPlayerScene = NextPlayerScene(rootService)

    init {

        // all scenes and the application itself need too
        // react to changes done in the service layer
        rootService.addRefreshables(
            this,
            inGameScene,
            scoreboardScene,
            mainMenuScene
        )

        // This is just done so that the blurred background when showing
        // the new game menu has content and looks nicer
        rootService.gameService.startNewGame(mutableListOf("P1", "P2"),5)

        this.showGameScene(inGameScene)
        this.showMenuScene(mainMenuScene, 0)

    }

    override fun refreshAfterStartNewGame() {
        this.hideMenuScene()
    }

    override fun refreshAfterEndGame() {
        scoreboardScene.scoreboard = rootService.playerActionService.scoreboardList
        scoreboardScene.generateTexts()
        this@PushPokerApplication.showMenuScene(scoreboardScene)
    }

    override fun refreshAfterEndTurn() {
        nextPlayerScene.headlineTextGenerator()
        this@PushPokerApplication.showMenuScene(nextPlayerScene)
    }

    override fun refreshAfterStartTurn(player: Player) {
        this.hideMenuScene()
    }

    override fun refreshAfterEndRound(roundsLeft: Int) {
        nextPlayerScene.headlineTextRoundEndGenerator()
        this@PushPokerApplication.showMenuScene(nextPlayerScene)
    }

}