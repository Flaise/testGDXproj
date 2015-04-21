package testGDX

import org.junit.Test
import kotlin.test.*
import org.junit.After
import org.junit.Before
import java.util.Random


public class WaterTest {
    var context = Context()
    val tickEffect = TickEffect(Random())

    Before fun setUp() {
        context = Context()
    }

    After fun tearDown() {
    }

    Test fun obstruction() {
        makeBedrock(context, Vec2iv(10, 5))
        makeWater(context, Vec2iv(10, 6))
        assertEquals(positionsOf(context).size(), 1)

        applyEffect(context, tickEffect)
        assertEquals(positionsOf(context).size(), 0)
    }

    Test fun noObstruction() {
        makeBedrock(context, Vec2iv(10, 5))
        makeWater(context, Vec2iv(10, 7))
        assertEquals(positionsOf(context).size(), 1)

        applyEffect(context, tickEffect)
        assertEquals(positionsOf(context).size(), 1)
    }

}
