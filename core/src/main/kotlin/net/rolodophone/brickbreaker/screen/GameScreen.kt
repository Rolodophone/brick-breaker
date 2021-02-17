package net.rolodophone.brickbreaker.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import ktx.graphics.use
import ktx.log.debug
import ktx.log.logger
import net.rolodophone.brickbreaker.BrickBreaker

private val LOG = logger<GameScreen>()

class GameScreen(game: BrickBreaker): BrickBreakerScreen(game) {
	private val texture = Texture(Gdx.files.internal("graphics/paddle_normal.png"))
	private val sprite = Sprite(texture)

	override fun show() {
		LOG.debug { "First screen shown" }
		sprite.setPosition(1f, 1f)
	}

	override fun render(delta: Float) {
		batch.use {
			sprite.draw(it)
		}
	}

	override fun dispose() {
		texture.dispose()
	}
}