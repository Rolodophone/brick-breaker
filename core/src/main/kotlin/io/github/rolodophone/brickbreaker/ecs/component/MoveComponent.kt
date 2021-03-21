package io.github.rolodophone.brickbreaker.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class MoveComponent: Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<MoveComponent>()
	}

	val velocity = Vector2()

	override fun reset() {
		velocity.setZero()
	}
}