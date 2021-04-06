package io.github.rolodophone.brickbreaker.event

import com.badlogic.gdx.utils.ObjectMap

class GameEventManager {
	private val listeners = ObjectMap<GameEvent, MutableSet<GameEventListener>>()

	fun listen(event: GameEvent, listener: GameEventListener) {
		val listenerSet = listeners[event]

		if (listenerSet == null) {
			listeners.put(event, mutableSetOf(listener))
		}
		else {
			listenerSet.add(listener)
		}
	}

	fun trigger(event: GameEvent) {
		val listenerSet = listeners[event]

		if (listenerSet != null) {
			for (listener in listeners[event]) {
				listener.invoke(event)
			}
		}
	}
}