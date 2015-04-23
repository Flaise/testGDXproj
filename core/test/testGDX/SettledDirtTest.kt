package testGDX

import org.junit.Test
import kotlin.test.*
import org.junit.After
import org.junit.Before
import java.util.Random


public class SettledDirtTest {
    var context = Context()
    val tickEffect = TickEffect(Random())

    Before fun setUp() {
        context = Context()
    }

    After fun tearDown() {
    }

    Test fun unsettleWhenHovering() {
        makeSettledDirt(context, Vec2iv(0, 0))
        applyEffect(context, tickEffect)
        assertEquals(settledDirtPositionsOf(context).size(), 0)
        assertEquals(dirtPositionsOf(context).size(), 1)
    }

    Test fun unsettleWhenHovering2() {
        makeSettledDirt(context, Vec2iv(0, 0))
        makeSettledDirt(context, Vec2iv(2, 0))
        applyEffect(context, tickEffect)
        assertEquals(settledDirtPositionsOf(context).size(), 0)
        assertEquals(dirtPositionsOf(context).size(), 2)
    }

    Test fun staySettledOnBedrock() {
        makeSettledDirt(context, Vec2iv(0, 1))
        makeBedrock(context, Vec2iv(0, 0))
        applyEffect(context, tickEffect)
        assertEquals(1, settledDirtPositionsOf(context).size())
    }

}
