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
    else if(typeHandlers.any { it.order == handler.order }) {
        if(handler in typeHandlers)
            throw IllegalStateException("Handler already added.")
        val existing = typeHandlers.first { it.order == handler.order }
        throw IllegalStateException("Handler of given class and priority already added: "
                                    + existing.javaClass.getSimpleName())
    }
    else {
        typeHandlers.addInOrder(handler)
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

fun MutableList<T>.addInOrder<T: Comparable<T>>(element: T) {
    val index = Collections.binarySearch(this, element)
    val insertAt =
        if(index < 0) -(index + 1)
        else index + 1
    this.add(insertAt, element)
}
