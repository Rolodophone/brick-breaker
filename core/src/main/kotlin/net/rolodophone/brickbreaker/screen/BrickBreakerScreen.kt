package net.rolodophone.brickbreaker.screen

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.app.KtxScreen
import net.rolodophone.brickbreaker.BrickBreaker

abstract class BrickBreakerScreen(
	val game: BrickBreaker,
	val batch: Batch = game.batch,
	val gameViewport: Viewport = game.gameViewport,
	val engine: Engine = game.engine
): KtxScreen {
	override fun resize(width: Int, height: Int) {
		gameViewport.update(width, height, true)
	}

}