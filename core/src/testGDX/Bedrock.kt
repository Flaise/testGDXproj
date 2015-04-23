package testGDX

import com.badlogic.gdx.graphics.glutils.ShapeRenderer


object PushBedrockHandler: EffectHandler<PushEffect>(javaClass<PushEffect>(), 0) {
    override fun invoke(context: Context, effect: PushEffect) {
        if(isBedrockAt(context, effect.destination))
            effect.obstructed = true
    }
}

object DrawBedrockHandler: EffectHandler<DrawEffect>(javaClass<DrawEffect>(), 0) {
    override fun invoke(context: Context, effect: DrawEffect) {
        val shapes = effect.shapes

        val camera = effect.viewport.getCamera()
        val worldBottom = -camera.viewportHeight / 2

        shapes.begin(ShapeRenderer.ShapeType.Filled)
        shapes.setColor(.3f, .3f, .3f, 1f)
        for((x, y) in elevationsOf(context)) {
            shapes.rect(x.toFloat(), y.toFloat() + 1f, 1f, worldBottom - y.toFloat())
        }
        shapes.end()
    }
}

object KBedrock: Key<MutableMap<Int, Int>>

fun elevationsOf(context: Context): MutableMap<Int, Int> {
    if(KBedrock in context)
        return context[KBedrock]
    val result = hashMapOf<Int, Int>()
    context[KBedrock] = result
    addEffectHandler(context, PushBedrockHandler)
    addEffectHandler(context, DrawBedrockHandler)
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

fun isBedrockAt(context: Context, position: Vec2iv): Boolean {
    val elevation = bedrockElevation(context, position.x)
    return elevation != null && elevation >= position.y
}
