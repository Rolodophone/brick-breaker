package io.github.rolodophone.brickbreaker.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.utils.viewport.Viewport
import io.github.rolodophone.brickbreaker.ecs.component.BallComponent
import io.github.rolodophone.brickbreaker.ecs.component.MoveComponent
import io.github.rolodophone.brickbreaker.ecs.component.PaddleComponent
import io.github.rolodophone.brickbreaker.ecs.component.TransformComponent
import io.github.rolodophone.brickbreaker.util.getNotNull
import ktx.ashley.allOf
import ktx.ashley.has
import kotlin.math.absoluteValue

/**
 * Makes the ball bounce off the walls and paddle
 */
class BallBounceSystem(private val gameViewport: Viewport):
	IteratingSystem(allOf(BallComponent::class, TransformComponent::class, MoveComponent::class).get()) {

	override fun processEntity(entity: Entity, deltaTime: Float) {
		val ballMoveComp = entity.getNotNull(MoveComponent.mapper)
		val ballTransformComp = entity.getNotNull(TransformComponent.mapper)

		val ballLeft = ballTransformComp.rect.x
		val ballBottom = ballTransformComp.rect.y
		val ballWidth = ballTransformComp.rect.width
		val ballHeight = ballTransformComp.rect.height

		val paddleTransformComp = engine.entities.first { it.has(PaddleComponent.mapper) }.getNotNull(TransformComponent.mapper)

		val paddleTop = paddleTransformComp.rect.y + paddleTransformComp.rect.height
		val paddleLeft = paddleTransformComp.rect.x
		val paddleRight = paddleTransformComp.rect.x + paddleTransformComp.rect.width

		// bounce off left wall
		if (ballLeft < 0f) {
			ballMoveComp.velocity.x = ballMoveComp.velocity.x.absoluteValue
		}

		//bounce off right wall
		else if (ballLeft + ballWidth > gameViewport.worldWidth) {
			ballMoveComp.velocity.x = -ballMoveComp.velocity.x.absoluteValue
		}

		//bounce off top wall
		if (ballBottom + ballHeight > gameViewport.worldHeight) {
			ballMoveComp.velocity.y = -ballMoveComp.velocity.y.absoluteValue
		}

		//bounce off paddle
		else if (ballBottom < paddleTop &&
			ballLeft + ballWidth > paddleLeft &&
			ballLeft < paddleRight
		) {
			ballMoveComp.velocity.y = ballMoveComp.velocity.y.absoluteValue
		}
	}
}