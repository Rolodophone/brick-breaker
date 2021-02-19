package net.rolodophone.brickbreaker.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.graphics.use
import ktx.log.debug
import ktx.log.logger
import net.rolodophone.brickbreaker.BrickBreaker

class GameScreen(game: BrickBreaker): BrickBreakerScreen(game) {
	companion object {
		val log = logger<GameScreen>()
	}

	private val viewport = FitViewport(9 * 32f, 16 * 32f) //288x512
	private val texture = Texture(Gdx.files.internal("graphics/paddle_normal.png"))
	private val sprite = Sprite(texture)

	override fun show() {
		sprite.setPosition(1f, 1f)
	}

	override fun resize(width: Int, height: Int) {
		viewport.update(width, height, true)
	}

	override fun render(delta: Float) {
		viewport.apply()
		batch.use(viewport.camera.combined) {
			sprite.draw(it)
		}
	}

	override fun dispose() {
		texture.dispose()
	}
}