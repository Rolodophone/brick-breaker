package io.github.rolodophone.brickbreaker

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Application.LOG_DEBUG
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FitViewport
import io.github.rolodophone.brickbreaker.screen.BrickBreakerScreen
import io.github.rolodophone.brickbreaker.screen.GameScreen
import ktx.app.KtxGame
import ktx.log.debug
import ktx.log.logger

private const val BATCH_SIZE = 1000

private val log = logger<BrickBreaker>()

class BrickBreaker: KtxGame<BrickBreakerScreen>() {
	val gameViewport = FitViewport(180f, 320f)
	lateinit var batch: Batch
	lateinit var brickBreakerTextures: BrickBreakerTextures
	lateinit var engine: Engine

	override fun create() {
		Gdx.app.logLevel = LOG_DEBUG

		//init stuff
		batch = SpriteBatch(BATCH_SIZE)
		brickBreakerTextures = BrickBreakerTextures()
		engine = PooledEngine()

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

		brickBreakerTextures.dispose()
	}
}