package io.github.rolodophone.brickbreaker.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IntervalIteratingSystem
import io.github.rolodophone.brickbreaker.ecs.component.PaddleComponent
import ktx.ashley.allOf

private const val DELTA_TIME = 1/4f

/**
 * Controls debugging features. This System will probably be disabled in the release.
 */
@Suppress("unused")
class DebugSystem: IntervalIteratingSystem(allOf(PaddleComponent::class).get(), DELTA_TIME) {
	override fun processEntity(entity: Entity) {

	}
}