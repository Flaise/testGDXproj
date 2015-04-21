package testGDX

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.Viewport
import java.util.Random


class PushEffect(val origin: Vec2iv, val destination: Vec2iv) {
    var obstructed = false
}

class DrawEffect(val shapes: ShapeRenderer, val viewport: Viewport) {
    // TODO: time since last frame
}

class TickEffect(val random: Random) {}

