package testGDX

import java.util.NoSuchElementException


data trait Key<TValue> {}

class Context() {
    val contents = hashMapOf<Key<*>, Any>()

    fun get<TValue>(key: Key<TValue>): TValue {
        if(key !in contents)
            throw NoSuchElementException()
        return contents[key] as TValue
    }

    fun set<TValue>(key: Key<TValue>, value: TValue) {
        contents[key] = value
    }

    fun contains<TValue>(key: Key<TValue>) = key in contents
}
