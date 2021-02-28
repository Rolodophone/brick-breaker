package net.rolodophone.brickbreaker.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.graphics.use
import ktx.log.error
import ktx.log.logger
import net.rolodophone.brickbreaker.ecs.component.GraphicsComponent
import net.rolodophone.brickbreaker.ecs.component.TransformComponent
import net.rolodophone.brickbreaker.ecs.component.getNotNull

private val log = logger<RenderSystem>()

class RenderSystem(
	private val batch: Batch,
	private val gameViewport: Viewport
): SortedIteratingSystem(
	allOf(TransformComponent::class, GraphicsComponent::class).get(),
	compareBy { entity -> entity[TransformComponent.mapper] }
) {
	override fun update(deltaTime: Float) {
		gameViewport.apply()
		batch.use(gameViewport.camera.combined) {
			super.update(deltaTime)
		}
	}

	override fun processEntity(entity: Entity, deltaTime: Float) {
		val transform = entity.getNotNull(TransformComponent.mapper)
		val graphics = entity.getNotNull(GraphicsComponent.mapper)

		if (graphics.sprite.texture == null) {
			log.error { "Entity $entity has no texture for rendering" }
			return
		}

		graphics.sprite.run {
			setBounds(transform.rect.x, transform.rect.y, transform.rect.width, transform.rect.height)
			rotation = transform.rotation
			draw(batch)
		}
	}
}