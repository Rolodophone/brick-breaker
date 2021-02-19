package net.rolodophone.brickbreaker.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import net.rolodophone.brickbreaker.BrickBreaker
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration

/** Launches the desktop (LWJGL3) application.  */
fun main() {
	Lwjgl3Application(BrickBreaker(), Lwjgl3ApplicationConfiguration().apply {
		setTitle("BrickBreaker")
		setWindowedMode(9 * 64, 16 * 64)
		setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png")
	})
}
