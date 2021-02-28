package net.rolodophone.brickbreaker.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import ktx.ashley.entity
import ktx.ashley.with
import net.rolodophone.brickbreaker.BrickBreaker
import net.rolodophone.brickbreaker.ecs.component.BallComponent
import net.rolodophone.brickbreaker.ecs.component.GraphicsComponent
import net.rolodophone.brickbreaker.ecs.component.PaddleComponent
import net.rolodophone.brickbreaker.ecs.component.TransformComponent
import net.rolodophone.brickbreaker.halfWorldWidth

class GameScreen(game: BrickBreaker): BrickBreakerScreen(game) {
	private val paddleTexture = Texture(Gdx.files.internal("graphics/paddle_normal.png"))
	private val ballTexture = Texture(Gdx.files.internal("graphics/ball.png"))

	override fun show() {
		//paddle
		engine.entity {
			with<TransformComponent> {
				setSizeFromTexture(paddleTexture)
				rect.setCenter(gameViewport.halfWorldWidth(), PaddleComponent.Y)
			}
			with<GraphicsComponent> {
				sprite.setRegion(paddleTexture)
			}
			with<PaddleComponent>()
		}
		//ball
		engine.entity {
			with<TransformComponent> {
				setSizeFromTexture(ballTexture)
				rect.setCenter(gameViewport.halfWorldWidth(), PaddleComponent.Y + paddleTexture.height / 2 + ballTexture.height / 2)
			}
			with<GraphicsComponent> {
				sprite.setRegion(ballTexture)
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

	override fun dispose() {
		paddleTexture.dispose()
	}
}