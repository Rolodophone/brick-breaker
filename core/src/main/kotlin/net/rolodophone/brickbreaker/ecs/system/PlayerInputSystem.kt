package net.rolodophone.brickbreaker.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.ashley.allOf
import ktx.ashley.has
import net.rolodophone.brickbreaker.ecs.component.FiringLineComponent
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
		val paddle = entity.getNotNull(PaddleComponent.mapper)

		val touchX = gameViewport.unprojectX(Gdx.input.x.toFloat())

		when (paddle.state) {
			PaddleComponent.State.FIRING -> {
				// swipe to determine firing angle

				val angle = MathUtils.lerp(60f, -60f, touchX / gameViewport.worldWidth)
				val firingAngle = MathUtils.clamp(angle, -60f, 60f)
				paddle.firingAngle = firingAngle
				engine.entities.first { it.has(FiringLineComponent.mapper) }.getNotNull(TransformComponent.mapper).rotation = firingAngle
			}

			PaddleComponent.State.DEFLECTING -> {
				// swipe to move paddle left and right

				val transform = entity.getNotNull(TransformComponent.mapper)

				val clampedX = MathUtils.clamp(touchX, transform.rect.halfWidth(), gameViewport.worldWidth - transform.rect.halfWidth())
				transform.rect.setCenter(clampedX, PaddleComponent.Y)
			}
		}
	}
}