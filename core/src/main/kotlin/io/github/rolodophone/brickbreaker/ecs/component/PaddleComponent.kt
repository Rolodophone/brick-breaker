package io.github.rolodophone.brickbreaker.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class PaddleComponent: Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<PaddleComponent>()
		const val Y = 20f
	}

	enum class State {
		FIRING, DEFLECTING
	}

	var state = State.FIRING
	var firingAngle = MathUtils.random(20f, 160f)

	override fun reset() {
		state = State.FIRING
		firingAngle = MathUtils.random(20f, 160f)
	}
}