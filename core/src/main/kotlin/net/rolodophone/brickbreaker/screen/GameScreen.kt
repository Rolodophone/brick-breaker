package net.rolodophone.brickbreaker.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import ktx.ashley.entity
import ktx.ashley.with
import net.rolodophone.brickbreaker.BrickBreaker
import net.rolodophone.brickbreaker.ecs.component.GraphicsComponent
import net.rolodophone.brickbreaker.ecs.component.TransformComponent

class GameScreen(game: BrickBreaker): BrickBreakerScreen(game) {

	private val playerTexture = Texture(Gdx.files.internal("graphics/paddle_normal.png"))

	override fun show() {
		repeat(10) {
			engine.entity {
				with<TransformComponent> {
					position.set(MathUtils.random(0f, gameViewport.worldWidth), MathUtils.random(0f, gameViewport.worldHeight), 0f)
					size.set(playerTexture.width.toFloat(), playerTexture.height.toFloat())
				}
				with<GraphicsComponent> {
					sprite.run {
						setRegion(playerTexture)
						setSize(texture.width.toFloat(), texture.height.toFloat())
						setOriginCenter()
					}
				}
			}
		}
	}

	override fun render(delta: Float) {
		engine.update(delta)

	}

	override fun dispose() {
		playerTexture.dispose()
	}
}