package testGDX

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.RandomXS128
import com.badlogic.gdx.utils.viewport.ExtendViewport
import java.util.Timer
import java.util.TimerTask

public class Main: ApplicationAdapter() {
    val camera = OrthographicCamera()
    val viewport = ExtendViewport(40f, 40f, 60f, 0f, camera)
    val context = Context()
    val random = RandomXS128()
    var shapes: ShapeRenderer? = null
    var drawEffect: DrawEffect? = null

    override fun create() {
        Gdx.graphics.setContinuousRendering(false)

        camera.position.set(20f, 20f, 0f)
        camera.update()
        viewport.apply()

        shapes = ShapeRenderer()
        drawEffect = DrawEffect(shapes!!, viewport)

        for(i in -10..39 + 10) {
            makeBedrock(context, Vec2iv(i, random.nextInt(4) + 1))
        }

        makeWater(context, Vec2iv(0, 40))

        delayTick()
    }

    fun delayTick() {
        Timer().schedule(object: TimerTask() {
            override fun run() {
                delayTick()
                Gdx.app.postRunnable {
                    applyEffect(context, TickEffect)
                    Gdx.graphics.requestRendering()
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
