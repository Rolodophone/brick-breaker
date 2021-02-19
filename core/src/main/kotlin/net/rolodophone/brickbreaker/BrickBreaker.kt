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

const val BATCH_SIZE = 1000

class BrickBreaker: KtxGame<BrickBreakerScreen>() {
	companion object {
		val log = logger<BrickBreaker>()
	}

	val batch: Batch by lazy { SpriteBatch(BATCH_SIZE) }

	override fun create() {
		Gdx.app.logLevel = LOG_DEBUG

		addScreen(GameScreen(this))

		setScreen<GameScreen>()
	}

	override fun dispose() {
		log.debug { "Disposing game" }

		super.dispose()

		log.debug {
			val sb = batch as SpriteBatch
			"Max sprites in batch: ${sb.maxSpritesInBatch}; size of batch: $BATCH_SIZE"
		}
		batch.dispose()
	}
}