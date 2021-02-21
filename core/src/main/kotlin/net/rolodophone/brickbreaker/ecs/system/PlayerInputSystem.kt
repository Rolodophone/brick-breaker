package net.rolodophone.brickbreaker.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.ashley.allOf
import net.rolodophone.brickbreaker.ecs.component.PaddleComponent
import net.rolodophone.brickbreaker.ecs.component.TransformComponent
import net.rolodophone.brickbreaker.ecs.component.getNotNull
import net.rolodophone.brickbreaker.halfWidth
import net.rolodophone.brickbreaker.unprojectX

class PlayerInputSystem(
	private val gameViewport: Viewport
): IteratingSystem(
	allOf(PaddleComponent::class, TransformComponent::class).get()
) {
	override fun processEntity(entity: Entity, deltaTime: Float) {
		//don't move paddle if it's firing
		val paddle = entity.getNotNull(PaddleComponent.mapper)
		if (paddle.state == PaddleComponent.State.FIRING) return

		val transform = entity.getNotNull(TransformComponent.mapper)

		val newX = gameViewport.unprojectX(Gdx.input.x.toFloat())
		val clampedX = MathUtils.clamp(newX, transform.rect.halfWidth(), gameViewport.worldWidth - transform.rect.halfWidth())
		transform.rect.setCenter(clampedX, PaddleComponent.Y)
	}
}