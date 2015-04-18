package testGDX


trait Key<TValue> {}

class Context() {
    val contents = hashMapOf<Key<*>, Any>()

    fun get<TValue>(key: Key<TValue>): TValue {
        if(!contents.containsKey(key))
            throw IllegalStateException()
        return contents[key] as TValue
    }

    fun set<TValue>(key: Key<TValue>, value: TValue) {
        contents[key] = value
    }
}
