package testGDX

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.Viewport


class GentlePushEffect(val origin: Vec2iv, val destination: Vec2iv, var obstructed: Boolean = false) {

}

class DrawEffect(val shapes: ShapeRenderer, val viewport: Viewport) {
    // TODO: time since last frame
}
