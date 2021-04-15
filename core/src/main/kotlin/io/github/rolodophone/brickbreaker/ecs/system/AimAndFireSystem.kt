package io.github.rolodophone.brickbreaker.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.math.MathUtils
import io.github.rolodophone.brickbreaker.ecs.component.GraphicsComponent
import io.github.rolodophone.brickbreaker.ecs.component.MoveComponent
import io.github.rolodophone.brickbreaker.ecs.component.PaddleComponent
import io.github.rolodophone.brickbreaker.ecs.component.TransformComponent
import io.github.rolodophone.brickbreaker.event.GameEvent
import io.github.rolodophone.brickbreaker.event.GameEventManager
import io.github.rolodophone.brickbreaker.util.getNotNull

private const val BALL_SPEED = 200f

/**
 * Responsible for controlling the logic of aiming and firing the ball
 */
class AimAndFireSystem(gameEventManager: GameEventManager, paddle: Entity, ball: Entity, firingLine: Entity): EntitySystem() {
	private val paddleComp = paddle.getNotNull(PaddleComponent.mapper)
	private val ballMoveComp = ball.getNotNull(MoveComponent.mapper)
	private val firingLineTransformComp = firingLine.getNotNull(TransformComponent.mapper)
	private val firingLineGraphicsComp = firingLine.getNotNull(GraphicsComponent.mapper)

	init {
		gameEventManager.listen(GameEvent.StartAiming) {
			paddleComp.state = PaddleComponent.State.AIMING
			firingLineGraphicsComp.visible = true
		}

		gameEventManager.listen(GameEvent.AdjustAimAngle) { event ->
			paddleComp.firingAngle = event.newAngle
			firingLineTransformComp.rotation = event.newAngle
		}

		gameEventManager.listen(GameEvent.ShootBall) {
			paddleComp.state = PaddleComponent.State.DEFLECTING
			firingLineGraphicsComp.visible = false

			// subtracting from 90 converts the angle into anticlockwise from horizontal so we can use sin and cos
			val firingAngle = 90 + firingLineTransformComp.rotation

			ballMoveComp.velocity.set(
				MathUtils.cosDeg(firingAngle) * BALL_SPEED,
				MathUtils.sinDeg(firingAngle) * BALL_SPEED
			)
		}
	}
}