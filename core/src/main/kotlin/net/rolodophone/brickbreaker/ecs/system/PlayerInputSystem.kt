package net.rolodophone.brickbreaker.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.ashley.allOf
import net.rolodophone.brickbreaker.ecs.component.PaddleComponent
import net.rolodophone.brickbreaker.ecs.component.TransformComponent
import net.rolodophone.brickbreaker.ecs.component.getNotNull

class PlayerInputSystem(
	private val gameViewport: Viewport
): IteratingSystem(
	allOf(PaddleComponent::class, TransformComponent::class).get()
) {
	override fun processEntity(entity: Entity, deltaTime: Float) {
		val transform = entity.getNotNull(TransformComponent.mapper)

		//TODO
	}
}