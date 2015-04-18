package testGDX

import java.util.Collections


abstract public class EffectHandler<TEffect>(val type: Class<TEffect>, val priorityDescending: Int):
        Comparable<EffectHandler<TEffect>> {

    override public fun compareTo(other: EffectHandler<TEffect>): Int {
        return other.priorityDescending - priorityDescending
    }

    /**
     * @return true to skip all further processing for the current effect, false otherwise
     */
    abstract fun handle(effect: TEffect): Boolean
}

val handlers = hashMapOf<Class<*>, MutableList<EffectHandler<*>>>()

fun add(handler: EffectHandler<*>) {
    val typeHandlers = handlers[handler.type]
    if(typeHandlers == null) {
        handlers[handler.type] = arrayListOf(handler)
    }
    else {
        typeHandlers.add(handler)
        Collections.sort(typeHandlers)
    }
}

fun remove(handler: EffectHandler<*>) {
    val typeHandlers = handlers[handler.type]
    if(typeHandlers == null)
        return
    typeHandlers.remove(handler)
}

fun apply<TEffect>(effect: TEffect) {
    val contextHandlers1 = handlers[effect.javaClass]
    if(contextHandlers1 == null)
        return
    val contextHandlers = contextHandlers1 as MutableList<EffectHandler<TEffect>>
    for(handler in contextHandlers)
        if(handler.handle(effect))
            return
}


