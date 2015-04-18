package testGDX

import org.junit.Test
import kotlin.test.*
import org.junit.After
import org.junit.Before


public class BedrockTest {

    Before fun setUp() {
    }

    After fun tearDown() {
    }

    Test fun asdf() {

        val context = Any()

//        Bedrock.make(Vec2iv(10, 5))

//        assert(Bedrock.)
        val effect = GentlePushEffect(Vec2iv(10, 6), Vec2iv(10, 5))
        assert(!effect.obstructed)

//        Effects.apply(context, effect)
//        Effects.apply(effect)

        assert(effect.obstructed)

    }

}
