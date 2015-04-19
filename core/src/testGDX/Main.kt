package testGDX

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.ExtendViewport

public class Main: ApplicationAdapter() {
    var batch: SpriteBatch? = null
    var img: Texture? = null
    var camera = OrthographicCamera()
    var viewport = ExtendViewport(8f, 8f, 10f, 10f, camera)

    override fun create() {
        camera.viewportWidth = 8f
        camera.viewportHeight = 8f
        camera.position.set(0f, 0f, 0f)
        camera.update()
        viewport.apply()

        batch = SpriteBatch()
        img = Texture("badlogic.jpg")
    }

    override fun render() {
        val b = batch
        if(b == null)
            return

        val camera = viewport.getCamera()
        b.setProjectionMatrix(camera.projection)
        b.setTransformMatrix(camera.view)

        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        b.begin()
        for(i in 0f..9f)
            b.draw(img, i, i, 1f, 1f)
        b.end()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }
}
