package net.rolodophone.brickbreaker.screen

import ktx.ashley.entity
import ktx.ashley.with
import net.rolodophone.brickbreaker.BrickBreaker
import net.rolodophone.brickbreaker.ecs.component.BallComponent
import net.rolodophone.brickbreaker.ecs.component.GraphicsComponent
import net.rolodophone.brickbreaker.ecs.component.PaddleComponent
import net.rolodophone.brickbreaker.ecs.component.TransformComponent
import net.rolodophone.brickbreaker.halfWorldWidth

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
		engine.entity {
			with<TransformComponent> {
				setSizeFromTexture(textures.ball)
				rect.setCenter(
					gameViewport.halfWorldWidth(), 
					PaddleComponent.Y + textures.paddle_normal.regionHeight / 2 + textures.ball.regionHeight / 2
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

			}
		}
	}

	override fun render(delta: Float) {
		engine.update(delta)
	}
}