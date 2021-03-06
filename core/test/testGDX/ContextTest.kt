package testGDX

import org.junit.Test
import kotlin.test.*
import org.junit.After
import org.junit.Before
import java.util.NoSuchElementException


object Left: Key<Int> {}
object Right: Key<Float> {}

public class ContextTest {
    var context = Context()

    Before fun setUp() {
        context = Context()
    }

    After fun tearDown() {
    }

    Test fun storeInt() {
        context[Left] = 1
        assertEquals(context[Left], 1)
    }

    Test fun storeFloat() {
        context[Right] = 2f
        assertEquals(context[Right], 2f)
    }

    Test fun storeIntFloat() {
        context[Left] = -1
        context[Right] = 3f
        assertEquals(context[Left], -1)
        assertEquals(context[Right], 3f)
    }

    Test fun reassignInt() {
        context[Left] = 2
        context[Left] = 4
        assertEquals(context[Left], 4)
    }

    Test fun missingInt() {
        assertEquals(null, context[Left])
    }

    Test fun existenceOfInt() {
        assert(Left !in context)
        context[Left] = 1
        assert(Left in context)
    }
}
