package io.github.rolodophone.brickbreaker.ecs.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.math.MathUtils
import io.github.rolodophone.brickbreaker.ecs.component.*
import io.github.rolodophone.brickbreaker.event.GameEvent
import io.github.rolodophone.brickbreaker.event.GameEventManager
import io.github.rolodophone.brickbreaker.util.getNotNull
import ktx.ashley.has

private const val BALL_SPEED = 10f

/**
 * Responsible for controlling the logic of aiming and firing the ball
 */
class AimAndFireSystem(gameEventManager: GameEventManager): EntitySystem() {
	private val paddle by lazy { engine.entities.first { it.has(PaddleComponent.mapper) } }
	private val ball by lazy { engine.entities.first { it.has(BallComponent.mapper) } }
	private val firingLine by lazy { engine.entities.first { it.has(FiringLineComponent.mapper) } }

	private val paddleComp by lazy { paddle.getNotNull(PaddleComponent.mapper) }
	private val ballMoveComp by lazy { ball.getNotNull(MoveComponent.mapper) }
	private val firingLineTransformComp by lazy { firingLine.getNotNull(TransformComponent.mapper) }
	private val firingLineGraphicsComp by lazy { firingLine.getNotNull(GraphicsComponent.mapper) }

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