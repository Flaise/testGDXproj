package testGDX

import org.junit.Test
import kotlin.test.*
import org.junit.After
import org.junit.Before
import java.util.Random


public class SettledDirtTest {
    var context = Context()

    Before fun setUp() {
        context = Context()
    }

    After fun tearDown() {
    }

    Test fun unsettleWhenHovering() {
        makeSettledDirt(context, Vec2iv(0, 0))
        applyEffect(context, TickEffect(Random()))
        assertEquals(settledDirtPositionsOf(context).size(), 0)
        assertEquals(dirtPositionsOf(context).size(), 1)
    }

    Test fun unsettleWhenHovering2() {
        makeSettledDirt(context, Vec2iv(0, 0))
        makeSettledDirt(context, Vec2iv(2, 0))
        applyEffect(context, TickEffect(Random()))
        assertEquals(settledDirtPositionsOf(context).size(), 0)
        assertEquals(dirtPositionsOf(context).size(), 2)
    }

}
