package testGDX

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.ExtendViewport

public class Main: ApplicationAdapter() {
    var shapes: ShapeRenderer? = null
    var camera = OrthographicCamera()
    var viewport = ExtendViewport(8f, 8f, 10f, 10f, camera)

    override fun create() {
        camera.viewportWidth = 8f
        camera.viewportHeight = 8f
        camera.position.set(0f, 0f, 0f)
        camera.update()
        viewport.apply()

        shapes = ShapeRenderer()
    }

    override fun render() {
        val shapes = this.shapes!!

        val camera = viewport.getCamera()
        shapes.setProjectionMatrix(camera.projection)
        shapes.setTransformMatrix(camera.view)

        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        shapes.begin(ShapeRenderer.ShapeType.Filled)
        for(i in 0f..9f) {
            shapes.setColor(.3f, .3f, .3f, 1f)
            shapes.rect(i, i, 1f, 1f)
        }
        shapes.end()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }
}
