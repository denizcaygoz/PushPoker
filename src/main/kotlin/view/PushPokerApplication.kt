package view

import entity.Player
import service.RootService
import tools.aqua.bgw.core.BoardGameApplication
/**
 * This class works like a main pool for each scene in the game.
 */
class PushPokerApplication : BoardGameApplication("PushPoker"),Refreshable  {
    // Central service from which all others are created/accessed
    // also holds the currently active game
    private val rootService = RootService()

    // This is where the game takes place
    private val inGameScene = InGameScene(rootService)

    // This Scene is shown after each finished game
    private val scoreboardScene = ScoreboardScene(rootService).apply {
        goToMenuButton.onMouseClicked = {
            this@PushPokerApplication.showMenuScene(mainMenuScene)
        }
    }

    // To Exit the game.
    private val mainMenuScene = MainMenuScene(rootService).apply {
        quitButton.onMouseClicked = {
            exit()
        }
    }

    private val nextPlayerScene = NextPlayerScene(rootService)

    init {

        rootService.addRefreshables(
            this,
            inGameScene,
            scoreboardScene,
            mainMenuScene
        )

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