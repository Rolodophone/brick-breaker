package net.rolodophone.brickbreaker.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.ashley.entity
import ktx.ashley.get
import ktx.ashley.with
import ktx.graphics.use
import ktx.log.debug
import ktx.log.logger
import net.rolodophone.brickbreaker.BrickBreaker
import net.rolodophone.brickbreaker.ecs.component.GraphicsComponent
import net.rolodophone.brickbreaker.ecs.component.TransformComponent

class GameScreen(game: BrickBreaker): BrickBreakerScreen(game) {
	private val viewport = FitViewport(9 * 32f, 16 * 32f) //288x512#

	private val playerTexture = Texture(Gdx.files.internal("graphics/paddle_normal.png"))

	private val player = engine.entity {
		with<TransformComponent> {
			position.set(1f, 1f, 0f)
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

	override fun resize(width: Int, height: Int) {
		viewport.update(width, height, true)
	}

	override fun render(delta: Float) {
		engine.update(delta)

		viewport.apply()

		batch.use(viewport.camera.combined) { batch ->
			player[GraphicsComponent.mapper]?.let { graphics ->
				player[TransformComponent.mapper]?.let { transform ->
					graphics.sprite.run {
						rotation = transform.rotationDeg
						setBounds(transform.position.x, transform.position.y, transform.size.x, transform.size.y)
						draw(batch)
					}
				}
			}
		}
	}

	override fun dispose() {
		playerTexture.dispose()
	}
}