package io.github.rolodophone.brickbreaker.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import io.github.rolodophone.brickbreaker.ecs.component.MoveComponent
import io.github.rolodophone.brickbreaker.ecs.component.TransformComponent
import io.github.rolodophone.brickbreaker.util.getNotNull
import io.github.rolodophone.brickbreaker.util.plus
import ktx.ashley.allOf

private const val DELTA_TIME = 1/25f

private val tempVector = Vector2()

class MoveSystem: IteratingSystem(allOf(TransformComponent::class, MoveComponent::class).get()) {
	private var accumulator = 0f

	override fun update(deltaTime: Float) {
		accumulator += deltaTime

		//move each entity enough times so that it overshoots
		while (accumulator >= 0f) {
			accumulator -= DELTA_TIME
			super.update(DELTA_TIME)
		}

		// interpolate between the frames
		val alpha = (accumulator + DELTA_TIME) / DELTA_TIME

		for (entity in entities) {
			val transformComp = entity.getNotNull(TransformComponent.mapper)
			val moveComp = entity.getNotNull(MoveComponent.mapper)

			transformComp.rect.set(
				MathUtils.lerp(moveComp.beforeRect.x, moveComp.afterRect.x, alpha),
				MathUtils.lerp(moveComp.beforeRect.y, moveComp.afterRect.y, alpha),
				transformComp.rect.width,
				transformComp.rect.height
			)
		}
	}

	override fun processEntity(entity: Entity, deltaTime: Float) {
		val moveComp = entity.getNotNull((MoveComponent.mapper))

		//save previous position
		moveComp.beforeRect.set(moveComp.afterRect)

		val currentPosition = moveComp.afterRect.getPosition(tempVector)
		moveComp.afterRect.setPosition(currentPosition + moveComp.velocity)
	}
}