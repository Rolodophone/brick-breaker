package io.github.rolodophone.brickbreaker.event

import com.badlogic.gdx.utils.ObjectMap

class GameEventManager {
	private val listeners = ObjectMap<GameEvent, MutableSet<GameEventListener>>()

	fun listen(event: GameEvent, listener: GameEventListener) {
		listeners[event].add(listener)
	}

	fun trigger(event: GameEvent) {
		for (listener in listeners[event]) {
			listener(event)
		}
	}
}