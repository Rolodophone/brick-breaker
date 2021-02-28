package net.rolodophone.brickbreaker

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.Viewport

private val tempVector = Vector2()

fun Viewport.unprojectX(x: Float): Float {
	tempVector.x = x
	return unproject(tempVector).x
}

fun Viewport.unprojectY(y: Float): Float {
	tempVector.y = y
	return unproject(tempVector).y
}

fun Viewport.halfWorldWidth() = worldWidth / 2f
fun Viewport.halfWorldHeight() = worldHeight / 2f

fun Rectangle.halfWidth() = width / 2
fun Rectangle.halfHeight() = height / 2