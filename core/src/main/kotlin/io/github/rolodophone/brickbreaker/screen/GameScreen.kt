package io.github.rolodophone.brickbreaker.screen

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import io.github.rolodophone.brickbreaker.BrickBreaker
import io.github.rolodophone.brickbreaker.ecs.component.*
import io.github.rolodophone.brickbreaker.ecs.system.*
import io.github.rolodophone.brickbreaker.event.GameEventManager
import io.github.rolodophone.brickbreaker.util.getNotNull
import io.github.rolodophone.brickbreaker.util.halfWorldWidth
import ktx.ashley.entity
import ktx.ashley.with
import ktx.box2d.createWorld

private val tempVector = Vector2()

private const val MAX_DELTA_TIME = 1/10f

private const val WALL_WIDTH = 3f

class GameScreen(game: BrickBreaker): BrickBreakerScreen(game) {
	private val gameEventManager = GameEventManager()
	private lateinit var world: World

	@Suppress("UNUSED_VARIABLE")
	override fun show() {
		//set up Box2d
		Box2D.init()
		world = createWorld()

		//TODO remove
//		val myBox = world.body {
//			box(width = 40f, height = 20f)
//		}
		val bodyDef = BodyDef()
		bodyDef.type = BodyDef.BodyType.DynamicBody
		bodyDef.position.set(30f, 40f)

		val body = world.createBody(bodyDef)

		val circle = CircleShape()
		circle.radius = 6f

		val fixtureDef = FixtureDef()
		fixtureDef.shape = circle
		fixtureDef.density = 0.5f
		fixtureDef.friction = 0.4f
		fixtureDef.restitution = 0.6f

		val fixture = body.createFixture(fixtureDef)

		circle.dispose();

		// add entities

		val background = engine.entity {
			with<TransformComponent> {
				setSizeFromTexture(textures.background)
				rect.setPosition(0f, 0f)
				z = -10
			}
			with<GraphicsComponent> {
				sprite.setRegion(textures.background)
			}
		}

		val paddle = engine.entity {
			with<TransformComponent> {
				setSizeFromTexture(textures.paddle_normal)
				rect.setCenter(gameViewport.halfWorldWidth(), PaddleComponent.Y)
			}
			with<GraphicsComponent> {
				sprite.setRegion(textures.paddle_normal)
			}
			with<PaddleComponent>()
		}

		val ball = engine.entity {
			with<TransformComponent> {
				setSizeFromTexture(textures.ball)
				rect.setCenter(
					gameViewport.halfWorldWidth(), 
					PaddleComponent.Y + textures.paddle_normal.regionHeight / 2f + textures.ball.regionHeight / 2f
				)
			}
			with<GraphicsComponent> {
				sprite.setRegion(textures.ball)
			}
			with<SpinComponent>()
			with<MoveComponent>()
			with<BallComponent>()
		}

		val firingLine = engine.entity {
			with<TransformComponent> {
				setSizeFromTexture(textures.firing_line)
				rect.setPosition(ball.getNotNull(TransformComponent.mapper).rect.getCenter(tempVector))
				rect.x -= textures.firing_line.regionWidth / 2f
			}
			with<GraphicsComponent> {
				sprite.setRegion(textures.firing_line)
				sprite.setOrigin(textures.firing_line.regionWidth / 2f, 0f)
				visible = false
			}
			with<FiringLineComponent>()
		}

		val brick = engine.entity {
			with<TransformComponent> {
				setSizeFromTexture(textures.brick_red)
				rect.setCenter(gameViewport.halfWorldWidth(), gameViewport.worldHeight - 60)
			}
			with<GraphicsComponent> {
				sprite.setRegion(textures.brick_red)
			}
			with<BrickComponent>()
		}

		//add systems to engine (it is recommended to render *before* stepping the physics for some reason)
		engine.run {
			addSystem(PlayerInputSystem(gameViewport, gameEventManager, WALL_WIDTH))
			addSystem(RenderSystem(batch, gameViewport))
			addSystem(AimAndFireSystem(gameEventManager, paddle, ball, firingLine))
			addSystem(PhysicsSystem(world))
			addSystem(SpinSystem())
			addSystem(BallBounceSystem(gameViewport, paddle, WALL_WIDTH))
			addSystem(DebugSystem(gameEventManager, paddle, ball, world, gameViewport))
		}
	}

	override fun hide() {
		//remove game entities and systems
		engine.removeAllEntities()
		engine.removeAllSystems()

		world.dispose()
	}

	override fun render(delta: Float) {
		val newDeltaTime = if (delta > MAX_DELTA_TIME) MAX_DELTA_TIME else delta
		engine.update(newDeltaTime)
	}

	override fun resize(width: Int, height: Int) {
		gameViewport.update(width, height, true)
	}
}