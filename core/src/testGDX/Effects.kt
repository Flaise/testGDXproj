package testGDX

import java.util.Collections


abstract class EffectHandler<GEffect>(val type: Class<GEffect>, val priorityDescending: Int):
        Comparable<EffectHandler<GEffect>> {

    override public fun compareTo(other: EffectHandler<GEffect>): Int {
        return other.priorityDescending - priorityDescending
    }

    /**
     * @return true to skip all further processing for the current effect, false otherwise
     */
    abstract fun handle(effect: GEffect): Boolean
}

val handlers = hashMapOf<Class<*>, MutableList<EffectHandler<*>>>()

fun addHandler(handler: EffectHandler<*>) {
    val typeHandlers = handlers[handler.type]
    if(typeHandlers == null) {
        handlers[handler.type] = arrayListOf(handler)
    }
    else {
        typeHandlers.add(handler)
        Collections.sort(typeHandlers)
    }
}

fun removeHandler(handler: EffectHandler<*>) {
    val typeHandlers = handlers[handler.type]
    if(typeHandlers == null)
        return
    typeHandlers.remove(handler)
}

fun apply<GEffect>(effect: GEffect) {
    val contextHandlers1 = handlers[effect.javaClass]
    if(contextHandlers1 == null)
        return
    val contextHandlers = contextHandlers1 as MutableList<EffectHandler<GEffect>>
    for(handler in contextHandlers)
        if(handler.handle(effect))
            return
}

