package testGDX

import org.junit.Test
import kotlin.test.*
import org.junit.After
import org.junit.Before


class MockEffect(val a: Int) {
}


public class EffectsTest {

    Before fun setUp() {
    }

    After fun tearDown() {
    }

    Test fun applyEmpty() {
        apply(Context(), MockEffect(2))
    }
}
