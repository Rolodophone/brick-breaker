package io.github.rolodophone.brickbreaker.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import io.github.rolodophone.brickbreaker.event.GameEvent
import io.github.rolodophone.brickbreaker.event.GameEventManager

/**
 * Controls debugging features. This System will probably be disabled in the release.
 */
@Suppress("unused")
class DebugSystem(private val gameEventManager: GameEventManager, private val paddle: Entity, private val ball: Entity): EntitySystem() {
	override fun update(deltaTime: Float) {
		if (Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_1)) {
			gameEventManager.trigger(GameEvent.CatchBall)
		}
	}
}