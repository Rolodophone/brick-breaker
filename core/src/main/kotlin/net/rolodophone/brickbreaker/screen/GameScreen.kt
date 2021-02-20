package net.rolodophone.brickbreaker.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import ktx.ashley.entity
import ktx.ashley.with
import net.rolodophone.brickbreaker.BrickBreaker
import net.rolodophone.brickbreaker.ecs.component.GraphicsComponent
import net.rolodophone.brickbreaker.ecs.component.PaddleComponent
import net.rolodophone.brickbreaker.ecs.component.TransformComponent

class GameScreen(game: BrickBreaker): BrickBreakerScreen(game) {
	private val playerTexture = Texture(Gdx.files.internal("graphics/paddle_normal.png"))

	override fun show() {
		engine.entity {
			with<TransformComponent> {
				setSizeFromTexture(playerTexture)
				rect.setCenter(gameViewport.worldWidth / 2f, 20f)
			}
			with<GraphicsComponent> {
				sprite.run {
					setRegion(playerTexture)
				}
			}
			with<PaddleComponent>()
		}
	}

	override fun render(delta: Float) {
		engine.update(delta)
	}

	override fun dispose() {
		playerTexture.dispose()
	}
}