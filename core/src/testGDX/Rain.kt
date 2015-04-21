package testGDX


object TickRainHandler: EffectHandler<TickEffect>(javaClass<TickEffect>(), 1) {
    override fun invoke(context: Context, effect: TickEffect) {
        val emitters = emittersOf(context)

        for(emitter in emitters) {
            for(x in 0..emitter.width - 1) {
                if(effect.random.nextFloat() > emitter.chanceOfGeneration)
                    continue
                makeWater(context, Vec2iv(emitter.left + x, emitter.y))
            }
        }
    }
}

object KRain: Key<MutableList<Emitter>> {}

class Emitter(val topLeft: Vec2iv, val width: Int, val chanceOfGeneration: Float) {
    val left: Int get() = topLeft.x
    val right: Int get() = left + width
    val y: Int get() = topLeft.y
}

fun emittersOf(context: Context): MutableList<Emitter> {
    if(KRain in context)
        return context[KRain]
    val result = arrayListOf<Emitter>()
    context[KRain] = result
    addEffectHandler(context, TickRainHandler)
    return result
}

fun makeRain(context: Context, topLeft: Vec2iv, width: Int) {
    val emitters = emittersOf(context)
    emitters.add(Emitter(topLeft, width, .05f))
}
