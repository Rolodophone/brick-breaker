package net.rolodophone.brickbreaker

import com.badlogic.gdx.Game
import net.rolodophone.brickbreaker.FirstScreen

/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms.  */
class BrickBreaker : Game() {
	override fun create() {
		setScreen(FirstScreen())
	}
}