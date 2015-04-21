package testGDX

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.Viewport
import java.util.Random


class PushEffect(val destination: Vec2iv) {
    var obstructed = false
}

class DrawEffect(val shapes: ShapeRenderer, val viewport: Viewport) {}

class TickEffect(val random: Random) {}

class DirtMovedEffect(val position: Vec2iv) {
    var stuck = false
}
