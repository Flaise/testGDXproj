package testGDX

import org.junit.Test
import kotlin.test.*
import org.junit.After
import org.junit.Before


class MockEffect(var value: Int) {}

class MockHandlerFirst(val expectedInput: Int):
        EffectHandler<MockEffect>(javaClass<MockEffect>(), 1) {
    override public fun invoke(context: Context, effect: MockEffect) {
        assertEquals(effect.value, expectedInput)
        effect.value += 1
    }
}

class MockHandlerSecond(val expectedInput: Int):
        EffectHandler<MockEffect>(javaClass<MockEffect>(), 2) {
    override public fun invoke(context: Context, effect: MockEffect) {
        assertEquals(effect.value, expectedInput)
        effect.value += 2
    }
}

class MockHandlerAddsFirst(val expectedInput: Int):
        EffectHandler<MockEffect>(javaClass<MockEffect>(), 3) {
    override public fun invoke(context: Context, effect: MockEffect) {
        assertEquals(effect.value, expectedInput)
        effect.value += 4
        addEffectHandler(context, MockHandlerFirst(-999999999))
    }
}

class MockHandlerAlsoFirst(): EffectHandler<MockEffect>(javaClass<MockEffect>(), 1) {
    override public fun invoke(context: Context, effect: MockEffect) {
        fail()
    }
}


public class EffectsTest {
    var context = Context()
    var effect = MockEffect(0)

    Before fun setUp() {
        context = Context()
        effect = MockEffect(0)
    }

    After fun tearDown() {
    }

    Test fun applyZero() {
        applyEffect(context, effect)
        assertEquals(effect.value, 0)
    }

    Test fun applyOne() {
        addEffectHandler(context, MockHandlerFirst(0))
        applyEffect(context, effect)
        assertEquals(effect.value, 1)
    }

    Test fun applyTwo() {
        addEffectHandler(context, MockHandlerFirst(0))
        addEffectHandler(context, MockHandlerSecond(1))
        applyEffect(context, effect)
        assertEquals(effect.value, 3)
    }

    Test fun removeHandler() {
        val handler = MockHandlerFirst(-999999)
        addEffectHandler(context, handler)
        removeEffectHandler(context, handler)
        applyEffect(context, effect)
        assertEquals(effect.value, 0)
    }

    Test fun addTwice() {
        val handler = MockHandlerFirst(0)
        addEffectHandler(context, handler)
        try {
            addEffectHandler(context, handler)
        }
        catch(err: IllegalStateException) {
            assertEquals(err.getMessage(), "Handler already added.")
        }
    }

    Test fun priorityCollision() {
        addEffectHandler(context, MockHandlerFirst(0))
        try {
            addEffectHandler(context, MockHandlerAlsoFirst())
        }
        catch(err: IllegalStateException) {
            assertEquals(err.getMessage(), "Handler of given class and priority already added.")
        }
    }

    Test fun concurrentModification() {
        addEffectHandler(context, MockHandlerAddsFirst(0))
        applyEffect(context, effect)
        assertEquals(effect.value, 4)
    }
}
