package io.github.rolodophone.brickbreaker.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.viewport.Viewport
import io.github.rolodophone.brickbreaker.ecs.component.PaddleComponent
import io.github.rolodophone.brickbreaker.ecs.component.TransformComponent
import io.github.rolodophone.brickbreaker.event.GameEvent
import io.github.rolodophone.brickbreaker.event.GameEventManager
import io.github.rolodophone.brickbreaker.util.getNotNull
import io.github.rolodophone.brickbreaker.util.halfWidth
import io.github.rolodophone.brickbreaker.util.unprojectX
import ktx.ashley.allOf

class PlayerInputSystem(
	private val gameViewport: Viewport,
	private val gameEventManager: GameEventManager
): IteratingSystem(
	allOf(PaddleComponent::class, TransformComponent::class).get()
) {
	override fun processEntity(entity: Entity, deltaTime: Float) {
		val paddleComp = entity.getNotNull(PaddleComponent.mapper)

		when (paddleComp.state) {
			PaddleComponent.State.WAITING_TO_FIRE -> {
				// wait for the user to hold the mouse down to aim

				if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
					gameEventManager.trigger(GameEvent.StartAiming)
				}
			}

			PaddleComponent.State.AIMING -> {
				// swipe left and right to determine firing angle

				val touchX = gameViewport.unprojectX(Gdx.input.x.toFloat())
				val angle = MathUtils.lerp(60f, -60f, touchX / gameViewport.worldWidth)
				val firingAngle = MathUtils.clamp(angle, -60f, 60f)

				GameEvent.AdjustAimAngle.newAngle = firingAngle
				gameEventManager.trigger(GameEvent.AdjustAimAngle)

				if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
					// button released, so fire ball
					gameEventManager.trigger(GameEvent.ShootBall)
				}
			}

			PaddleComponent.State.DEFLECTING -> {
				// swipe to move paddle left and right

				val transform = entity.getNotNull(TransformComponent.mapper)

				val touchX = gameViewport.unprojectX(Gdx.input.x.toFloat())
				val clampedX = MathUtils.clamp(touchX, transform.rect.halfWidth(), gameViewport.worldWidth - transform.rect.halfWidth())
				transform.rect.setCenter(clampedX, PaddleComponent.Y)
			}
		}
	}
}