package net.rolodophone.brickbreaker.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class TransformComponent: Component, Pool.Poolable, Comparable<TransformComponent> {
	companion object {
		val mapper = mapperFor<TransformComponent>()
	}

	val rect = Rectangle()
	var z = 0
	var rotation = 0f

	override fun reset() {
		//Note the rect is not reset. If you don't define the rect when using this component behaviour is undefined
		z = 0
		rotation = 0f
	}

	override fun compareTo(other: TransformComponent): Int {
		return z - other.z
	}

	fun setSizeFromTexture(texture: TextureRegion) {
		rect.setSize(texture.regionWidth.toFloat(), texture.regionHeight.toFloat())
	}
}