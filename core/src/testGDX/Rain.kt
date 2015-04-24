package testGDX


object TickRainHandler: EffectHandler<TickEffect>(javaClass<TickEffect>(), 3) {
    override fun invoke(context: Context, effect: TickEffect) {
        val emitters = emittersOf(context)

        for(emitter in emitters) {
            for(x in 0..emitter.width - 1) {
                if(effect.random.nextFloat() > emitter.chanceOfGeneration)
                    continue
                emitter.make(context, Vec2iv(emitter.left + x, emitter.y))
            }
        }
    }
}

object KRain: Key<MutableList<Emitter>>

class Emitter(val make: (Context, Vec2iv) -> Unit, val topLeft: Vec2iv, val width: Int,
              val chanceOfGeneration: Float) {
    val left: Int get() = topLeft.x
    val right: Int get() = left + width
    val y: Int get() = topLeft.y
}

fun emittersOf(context: Context): MutableList<Emitter> {
    val result = context[KRain]
    if(result != null)
        return result
    val result2 = arrayListOf<Emitter>()
    context[KRain] = result2
    addEffectHandler(context, TickRainHandler)
    return result2
}

fun makeRain(context: Context, make: (Context, Vec2iv) -> Unit, topLeft: Vec2iv, width: Int) {
    val emitters = emittersOf(context)
    emitters.add(Emitter(make, topLeft, width, .03f))
}
