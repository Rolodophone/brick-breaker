package io.github.rolodophone.brickbreaker.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
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

		while (accumulator >= DELTA_TIME) {
			accumulator -= DELTA_TIME
			super.update(DELTA_TIME)
		}
	}

	override fun processEntity(entity: Entity, deltaTime: Float) {
		val transformComp = entity.getNotNull(TransformComponent.mapper)
		val moveComp = entity.getNotNull((MoveComponent.mapper))

		val currentPosition = transformComp.rect.getPosition(tempVector)
		transformComp.rect.setPosition(currentPosition + moveComp.velocity)
	}
}