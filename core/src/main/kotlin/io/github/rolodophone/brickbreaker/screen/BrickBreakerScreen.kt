package io.github.rolodophone.brickbreaker.screen

import io.github.rolodophone.brickbreaker.BrickBreaker
import ktx.app.KtxScreen

abstract class BrickBreakerScreen(val game: BrickBreaker): KtxScreen {
	val batch = game.batch
	val gameViewport = game.gameViewport
	val engine = game.engine
	val textures = game.brickBreakerTextures
}