package net.rolodophone.brickbreaker.screen

import com.badlogic.gdx.graphics.g2d.Batch
import ktx.app.KtxScreen
import net.rolodophone.brickbreaker.BrickBreaker

abstract class BrickBreakerScreen(
	val game: BrickBreaker,
	val batch: Batch = game.batch
): KtxScreen