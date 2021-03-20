package io.github.rolodophone.brickbreaker.screen

import com.badlogic.gdx.math.Vector2
import io.github.rolodophone.brickbreaker.BrickBreaker
import io.github.rolodophone.brickbreaker.ecs.component.*
import io.github.rolodophone.brickbreaker.util.halfWorldWidth
import ktx.ashley.entity
import ktx.ashley.with

private val tempVector = Vector2()

class GameScreen(game: BrickBreaker): BrickBreakerScreen(game) {
	override fun show() {
		//paddle
		engine.entity {
			with<TransformComponent> {
				setSizeFromTexture(textures.paddle_normal)
				rect.setCenter(gameViewport.halfWorldWidth(), PaddleComponent.Y)
			}
			with<GraphicsComponent> {
				sprite.setRegion(textures.paddle_normal)
			}
			with<PaddleComponent>()
		}
		//ball
		val ball = engine.entity {
			with<TransformComponent> {
				setSizeFromTexture(textures.ball)
				rect.setCenter(
					gameViewport.halfWorldWidth(), 
					PaddleComponent.Y + textures.paddle_normal.regionHeight / 2f + textures.ball.regionHeight / 2f
				)
			}
			with<GraphicsComponent> {
				sprite.setRegion(textures.ball)
			}
			with<BallComponent>()
		}
		//firing line
		engine.entity {
			with<TransformComponent> {
				setSizeFromTexture(textures.firing_line)
				rect.setPosition(ball.getNotNull(TransformComponent.mapper).rect.getCenter(tempVector))
				rect.x -= textures.firing_line.regionWidth / 2f
			}
			with<GraphicsComponent> {
				sprite.setRegion(textures.firing_line)
				sprite.setOrigin(textures.firing_line.regionWidth / 2f, 0f)
			}
			with<FiringLineComponent>()
		}
	}

	override fun render(delta: Float) {
		engine.update(delta)
	}
}