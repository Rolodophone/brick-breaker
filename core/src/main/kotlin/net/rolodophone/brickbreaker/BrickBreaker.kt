package net.rolodophone.brickbreaker

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Application.LOG_DEBUG
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.app.KtxGame
import ktx.log.debug
import ktx.log.logger
import net.rolodophone.brickbreaker.ecs.system.RenderSystem
import net.rolodophone.brickbreaker.screen.BrickBreakerScreen
import net.rolodophone.brickbreaker.screen.GameScreen

const val BATCH_SIZE = 1000

private val log = logger<BrickBreaker>()

class BrickBreaker: KtxGame<BrickBreakerScreen>() {
	val gameViewport = FitViewport(9 * 30f, 16 * 30f)
	val batch: Batch by lazy { SpriteBatch(BATCH_SIZE) }
	val engine: Engine by lazy { PooledEngine().apply {
		addSystem(RenderSystem(batch, gameViewport))
	} }

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