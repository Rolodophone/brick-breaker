package io.github.rolodophone.brickbreaker.screen

import io.github.rolodophone.brickbreaker.BrickBreaker
import ktx.app.KtxScreen

abstract class BrickBreakerScreen(val game: BrickBreaker): KtxScreen {
	override fun resize(width: Int, height: Int) {
		gameViewport.update(width, height, true)
	}

	val batch = game.batch
	val gameViewport = game.gameViewport
	val engine = game.engine
	val textures = game.brickBreakerTextures
}