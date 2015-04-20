package testGDX

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.ExtendViewport
import java.util.Timer
import java.util.TimerTask

public class Main: ApplicationAdapter() {
    var shapes: ShapeRenderer? = null
    val camera = OrthographicCamera()
    val viewport = ExtendViewport(8f, 8f, 10f, 10f, camera)
    val context = Context()
    var drawEffect: DrawEffect? = null

    override fun create() {
        camera.viewportWidth = 8f
        camera.viewportHeight = 8f
        camera.position.set(0f, 0f, 0f)
        camera.update()
        viewport.apply()

        shapes = ShapeRenderer()
        drawEffect = DrawEffect(shapes!!, viewport)

        makeBedrock(context, Vec2iv(1, 2))
        makeBedrock(context, Vec2iv(3, 3))
        makeBedrock(context, Vec2iv(-2, -1))
        makeBedrock(context, Vec2iv(-3, -3))
        makeBedrock(context, Vec2iv(-4, -4))
        makeBedrock(context, Vec2iv(4, 4))

        makeWater(context, Vec2iv(-1, 2))

        delayTick()
    }

    fun delayTick() {
        Timer().schedule(object: TimerTask() {
            override fun run() {
                delayTick()
                Gdx.app.postRunnable {
                    applyEffect(context, TickEffect)
                }
            }
        }, 100)
    }

    override fun render() {
        val shapes = this.shapes!!
        val drawEffect = this.drawEffect!!

        val camera = viewport.getCamera()
        shapes.setProjectionMatrix(camera.projection)
        shapes.setTransformMatrix(camera.view)

        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        applyEffect(context, drawEffect)
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }
}
