package testGDX

import org.junit.Test
import kotlin.test.*
import org.junit.After
import org.junit.Before
import java.util.NoSuchElementException


object NLeft: NullableKey<Int> {}
object NRight: NullableKey<Float> {}
object NullableThing: NullableKey<Any?> {}

public class NullableContextTest {
    var context = NullableContext()

    Before fun setUp() {
        context = NullableContext()
    }

    After fun tearDown() {
    }

    Test fun storeInt() {
        context[NLeft] = 1
        assertEquals(context[NLeft], 1)
    }

    Test fun storeFloat() {
        context[NRight] = 2f
        assertEquals(context[NRight], 2f)
    }

    Test fun storeIntFloat() {
        context[NLeft] = -1
        context[NRight] = 3f
        assertEquals(context[NLeft], -1)
        assertEquals(context[NRight], 3f)
    }

    Test fun reassignInt() {
        context[NLeft] = 2
        context[NLeft] = 4
        assertEquals(context[NLeft], 4)
    }

    Test fun storeNull() {
        context[NullableThing] = null
        assertEquals(context[NullableThing], null)
    }

    Test fun overwriteNull() {
        context[NullableThing] = null
        val one = Any()
        context[NullableThing] = one
        assertEquals(context[NullableThing], one)
    }

    Test(expected = javaClass<NoSuchElementException>()) fun missingInt() {
        context[NLeft]
    }

    Test fun existenceOfInt() {
        assert(NLeft !in context)
        context[NLeft] = 1
        assert(NLeft in context)
    }

    Test fun existenceOfNullable() {
        assert(NullableThing !in context)
        context[NullableThing] = null
        assert(NullableThing in context)
    }
}
