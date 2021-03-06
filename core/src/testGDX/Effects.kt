package testGDX

import java.util.Collections
import java.util.concurrent.CopyOnWriteArrayList


abstract public class EffectHandler<TEffect: Any>(val type: Class<TEffect>, val order: Int):
        Comparable<EffectHandler<TEffect>> {

    override public fun toString() =
        javaClass.getSimpleName() + "(type=" + type.getSimpleName() + " order=" + order + ")"

    override public fun compareTo(other: EffectHandler<TEffect>) = order - other.order

    abstract fun invoke(context: Context, effect: TEffect)
}


object KEffects: Key<MutableMap<Class<*>, MutableList<EffectHandler<*>>>>


fun handlersOf(context: Context): MutableMap<Class<*>, MutableList<EffectHandler<*>>> {
    val result = context[KEffects]
    if(result != null)
        return result
    val result2 = hashMapOf<Class<*>, MutableList<EffectHandler<*>>>()
    context[KEffects] = result2
    return result2
}

fun addEffectHandler(context: Context, handler: EffectHandler<*>) {
    val handlers = handlersOf(context)
    val typeHandlers = handlers[handler.type]
    if(typeHandlers == null) {
        val newHandlers = CopyOnWriteArrayList<EffectHandler<*>>()
        newHandlers.add(handler)
        handlers[handler.type] = newHandlers
        return
    }

    val index = Collections.binarySearch(typeHandlers, handler)
    if(index >= 0) {
        val existing = typeHandlers[index]
        if(existing == handler)
            throw IllegalStateException("Handler already added.")
        throw IllegalStateException(
            "Handler of given class and priority already added: "
            + existing.javaClass.getSimpleName()
        )
    }
    else {
        typeHandlers.add(-(index + 1), handler)
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
