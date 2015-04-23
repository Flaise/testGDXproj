package testGDX

import org.junit.Test
import kotlin.test.*
import org.junit.After
import org.junit.Before
import java.util.Random


public class DirtTest {
    var context = Context()
    val tickEffect = TickEffect(Random())

    Before fun setUp() {
        context = Context()
    }

    After fun tearDown() {
    }

    Test fun fallOnBedrock() {
        makeDirt(context, Vec2iv(0, 2))
        makeBedrock(context, Vec2iv(0, 0))
        applyEffect(context, tickEffect)
        applyEffect(context, tickEffect) // doesn't matter whether implementation is 1 or 2 steps
        assertEquals(1, settledDirtPositionsOf(context).size())
        assertEquals(0, dirtPositionsOf(context).size())
    }

    Test fun fallOnSettledDirt() {
        makeDirt(context, Vec2iv(0, 3))
        makeSettledDirt(context, Vec2iv(0, 1))
        makeBedrock(context, Vec2iv(0, 0))
        applyEffect(context, tickEffect)
        assertEquals(2, settledDirtPositionsOf(context).size())
        assertEquals(0, dirtPositionsOf(context).size())
    }

    Test fun stickToSettledDirt() {
        makeDirt(context, Vec2iv(1, 2))
        makeSettledDirt(context, Vec2iv(0, 1))
        makeBedrock(context, Vec2iv(0, 0))
        applyEffect(context, tickEffect)
        assertEquals(2, settledDirtPositionsOf(context).size())
        assertEquals(0, dirtPositionsOf(context).size())
    }
}
