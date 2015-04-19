package testGDX


object GentlePushHandler: EffectHandler<GentlePushEffect>(javaClass<GentlePushEffect>(), 0) {
    override fun invoke(context: Context, effect: GentlePushEffect) {
        val elevation = bedrockElevation(context, effect.destination.x)
        if(elevation != null && elevation <= effect.destination.y)
            effect.obstructed = true
    }
}

object KBedrock: Key<MutableMap<Int, Int>> {}

fun elevationsOf(context: Context): MutableMap<Int, Int> {
    if(KBedrock in context)
        return context[KBedrock]
    val result = hashMapOf<Int, Int>()
    context[KBedrock] = result
    addEffectHandler(context, GentlePushHandler)
    return result
}

fun makeBedrock(context: Context, position: Vec2iv) {
    val elevations = elevationsOf(context)

    val prevElevation = elevations[position.x]
    if(prevElevation == null || prevElevation >= position.y)
        elevations[position.x] = position.y
}

// Adds key type-checking to Map#get()
fun bedrockElevation(context: Context, x: Int): Int? = elevationsOf(context)[x]
