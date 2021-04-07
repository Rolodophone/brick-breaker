package io.github.rolodophone.brickbreaker.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class BallComponent: Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<BallComponent>()
	}

	override fun reset() {}
}