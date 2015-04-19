package testGDX

import java.util.Collections


data abstract public
class EffectHandler<TEffect>(val type: Class<TEffect>, val order: Int):
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
        handlers[handler.type] = arrayListOf(handler)
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

// TODO: no generic constraint for non-nullability?
fun applyEffect<TEffect>(context: Context, effect: TEffect) {
    val handlers = handlersOf(context)
    val contextHandlers1 = handlers[effect.javaClass]
    if(contextHandlers1 == null)
        return
    val contextHandlers = contextHandlers1 as MutableList<EffectHandler<TEffect>>
    for(handler in contextHandlers)
        handler(context, effect)
}


