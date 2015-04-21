package testGDX

import com.badlogic.gdx.graphics.glutils.ShapeRenderer


object PushSettledDirtHandler: EffectHandler<PushEffect>(javaClass<PushEffect>(), 1) {
    override fun invoke(context: Context, effect: PushEffect) {
        val positions = settledDirtPositionsOf(context)
        if(effect.destination in positions)
            effect.obstructed = true
    }
}

object DirtMovedSettledDirtHandler: EffectHandler<DirtMovedEffect>(javaClass<DirtMovedEffect>(), 0) {
    override fun invoke(context: Context, effect: DirtMovedEffect) {
        val positions = settledDirtPositionsOf(context)
        if(effect.position + LEFTV in positions || effect.position + RIGHTV in positions)
            effect.stuck = true
    }
}

object DrawSettledDirtHandler: EffectHandler<DrawEffect>(javaClass<DrawEffect>(), 3) {
    override fun invoke(context: Context, effect: DrawEffect) {
        val shapes = effect.shapes

        shapes.begin(ShapeRenderer.ShapeType.Filled)
        shapes.setColor(.7f, .45f, 0f, 1f)
        for((x, y) in settledDirtPositionsOf(context)) {
            shapes.rect(x.toFloat(), y.toFloat(), 1f, 1f)
        }
        shapes.end()
    }
}

object KSettledDirt: Key<MutableSet<Vec2iv>> {}

fun settledDirtPositionsOf(context: Context): MutableSet<Vec2iv> {
    if(KSettledDirt in context)
        return context[KSettledDirt]
    val result = hashSetOf<Vec2iv>()
    context[KSettledDirt] = result
    addEffectHandler(context, DrawSettledDirtHandler)
    addEffectHandler(context, PushSettledDirtHandler)
    addEffectHandler(context, DirtMovedSettledDirtHandler)
    return result
}

fun makeSettledDirt(context: Context, position: Vec2iv) {
    val positions = settledDirtPositionsOf(context)
    positions.add(position)
}
