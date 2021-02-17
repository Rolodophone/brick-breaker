package net.rolodophone.brickbreaker

import com.badlogic.gdx.Application.LOG_DEBUG
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ktx.app.KtxGame
import ktx.log.debug
import ktx.log.logger
import net.rolodophone.brickbreaker.screen.BrickBreakerScreen
import net.rolodophone.brickbreaker.screen.GameScreen

private val LOG = logger<BrickBreaker>()

class BrickBreaker: KtxGame<BrickBreakerScreen>() {
	val batch: Batch by lazy { SpriteBatch() }

	override fun create() {
		Gdx.app.logLevel = LOG_DEBUG

		LOG.debug { "Create game instance" }

		addScreen(GameScreen(this))

		setScreen<GameScreen>()
	}

	override fun dispose() {
		batch.dispose()
	}
}