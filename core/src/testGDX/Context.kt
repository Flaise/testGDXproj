package testGDX

import java.util.NoSuchElementException


data trait NullableKey<TValue>

class NullableContext() {
    val contents = hashMapOf<NullableKey<*>, Any>()

    fun get<TValue>(key: NullableKey<TValue>): TValue {
        if(key !in contents)
            throw NoSuchElementException()
        return contents[key] as TValue
    }

    fun set<TValue>(key: NullableKey<TValue>, value: TValue) {
        contents[key] = value
    }

    fun contains<TValue>(key: NullableKey<TValue>) = key in contents
}


data trait Key<TValue: Any>

class Context() {
    val contents = hashMapOf<Key<*>, Any>()

    fun get<TValue: Any>(key: Key<TValue>): TValue? {
        return contents[key] as TValue?
    }

    fun set<TValue: Any>(key: Key<TValue>, value: TValue) {
        contents[key] = value
    }

    fun contains<TValue: Any>(key: Key<TValue>) = key in contents
}
