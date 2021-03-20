package io.github.rolodophone.brickbreaker.util

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

operator fun Vector2.plus(vec: Vector2): Vector2 = this.add(vec)

operator fun Vector2.times(scalar: Float): Vector2 = this.scl(scalar)
operator fun Float.times(vec: Vector2) = vec * this