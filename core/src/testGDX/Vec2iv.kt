package testGDX

data class Vec2iv(val x: Int, val y: Int) {
    fun plus(other: Vec2iv) = Vec2iv(x + other.x, y + other.y)

    fun minus(other: Vec2iv) = Vec2iv(x - other.x, y - other.y)

    fun times(other: Vec2iv) = Vec2iv(x * other.x, y * other.y)
    fun times(other: Int) = Vec2iv(x * other, y * other)

    fun div(other: Vec2iv) = Vec2iv(x / other.x, y / other.y)
    fun div(other: Int) = Vec2iv(x * other, y * other)

    fun dist4(other: Vec2iv) = Math.abs(x - other.x) + Math.abs(y - other.y)

    fun dist8(other: Vec2iv) = Math.max(Math.abs(x - other.x), Math.abs(y - other.y))
}

// TODO: operator lifting
