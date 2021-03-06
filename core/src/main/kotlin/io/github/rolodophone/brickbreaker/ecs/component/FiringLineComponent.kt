package io.github.rolodophone.brickbreaker.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class FiringLineComponent: Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<FiringLineComponent>()
	}

	override fun reset() {}
}