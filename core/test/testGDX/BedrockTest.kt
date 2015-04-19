package testGDX

import org.junit.Test
import kotlin.test.*
import org.junit.After
import org.junit.Before


public class BedrockTest {
    var context = Context()

    Before fun setUp() {
        context = Context()
    }

    After fun tearDown() {
    }

    Test fun handleGentlePush() {
        makeBedrock(context, Vec2iv(10, 5))

        val effect = GentlePushEffect(Vec2iv(10, 6), Vec2iv(10, 5))
        assert(!effect.obstructed)

        applyEffect(context, effect)
        assert(effect.obstructed)
    }

}
