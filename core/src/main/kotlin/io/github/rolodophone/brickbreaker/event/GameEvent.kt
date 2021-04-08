package io.github.rolodophone.brickbreaker.event

sealed class GameEvent {
	object StartAiming: GameEvent()
	object ShootBall: GameEvent()
	object AdjustAimAngle: GameEvent() {
		var newAngle = 0f
	}
}