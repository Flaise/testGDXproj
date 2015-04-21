package testGDX

import java.util.Collections
import java.util.concurrent.CopyOnWriteArrayList


data abstract public
class EffectHandler<TEffect: Any>(val type: Class<TEffect>, val order: Int):
        Comparable<EffectHandler<TEffect>> {

    override public fun compareTo(other: EffectHandler<TEffect>): Int {
        return order - other.order
    }

    abstract fun invoke(context: Context, effect: TEffect)
}


object KEffects: Key<MutableMap<Class<*>, MutableList<EffectHandler<*>>>> {}


fun handlersOf(context: Context): MutableMap<Class<*>, MutableList<EffectHandler<*>>> {
    if(KEffects in context)
        return context[KEffects]
    val result = hashMapOf<Class<*>, MutableList<EffectHandler<*>>>()
    context[KEffects] = result
    return result
}

fun addEffectHandler(context: Context, handler: EffectHandler<*>) {
    val handlers = handlersOf(context)
    val typeHandlers = handlers[handler.type]
    if(typeHandlers == null) {
        val newHandlers = CopyOnWriteArrayList<EffectHandler<*>>()
        newHandlers.add(handler)
        handlers[handler.type] = newHandlers
    }
    else if(typeHandlers.any { a -> a.order == handler.order }) {
        throw IllegalStateException("Handler of given priority already exists.")
    }
    else {
        typeHandlers.add(handler)
        Collections.sort(typeHandlers)
    }
}

fun removeEffectHandler(context: Context, handler: EffectHandler<*>) {
    val handlers = handlersOf(context)
    val typeHandlers = handlers[handler.type]
    if(typeHandlers == null)
        return
    typeHandlers.remove(handler)
}

fun applyEffect<TEffect: Any>(context: Context, effect: TEffect) {
    val handlers = handlersOf(context)
    val key = effect.javaClass
    if(key !in handlers)
        return
    val contextHandlers = handlers[key] as MutableList<EffectHandler<TEffect>>
    for(handler in contextHandlers)
        handler(context, effect)
}


