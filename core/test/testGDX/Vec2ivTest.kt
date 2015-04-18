package testGDX

import org.junit.Test
import kotlin.test.*
import org.junit.After
import org.junit.Before


public class Vec2ivTest {

    Before fun setUp() {
    }

    After fun tearDown() {
    }

    Test fun equality() {
        assert(Vec2iv(4, 5) == Vec2iv(4, 5))
        assert(Vec2iv(4, 5) != Vec2iv(1, 2))
        assertEquals(Vec2iv(1, 2), Vec2iv(1, 2))
    }

    Test fun sum() {
        assertEquals(Vec2iv(1, 2), Vec2iv(0, 1) + Vec2iv(1, 1))
        assertEquals(Vec2iv(1, 2), Vec2iv(0, 3) + Vec2iv(1, -1))
        assertEquals(Vec2iv(1, 2), Vec2iv(2, 2) + Vec2iv(-1, 0))
    }

    Test fun dif() {
        assertEquals(Vec2iv(-1, 0), Vec2iv(0, 1) - Vec2iv(1, 1))
        assertEquals(Vec2iv(-1, 4), Vec2iv(0, 3) - Vec2iv(1, -1))
        assertEquals(Vec2iv(3, 2), Vec2iv(2, 2) - Vec2iv(-1, 0))
    }

    Test fun dist4() {
        assertEquals(2, Vec2iv(0, 0) dist4 Vec2iv(1, 1))
        assertEquals(4, Vec2iv(0, 0) dist4 Vec2iv(-2, -2))
        assertEquals(4, Vec2iv(0, 0) dist4 Vec2iv(-4, 0))
    }

    Test fun testHashCode() {
        assertNotEquals(Vec2iv(1, 0).hashCode(), Vec2iv(0, 1).hashCode())
        assertNotEquals(Vec2iv(2, 0).hashCode(), Vec2iv(1, 1).hashCode())
    }
}
