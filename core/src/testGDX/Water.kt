package testGDX

import com.badlogic.gdx.graphics.glutils.ShapeRenderer


object DrawWaterHandler: EffectHandler<DrawEffect>(javaClass<DrawEffect>(), 1) {
    override fun invoke(context: Context, effect: DrawEffect) {
        val shapes = effect.shapes

        shapes.begin(ShapeRenderer.ShapeType.Filled)
        shapes.setColor(0f, .3f, .8f, 1f)
        for((x, y) in positionsOf(context)) {
            shapes.rect(x.toFloat(), y.toFloat(), 1f, 1f)
        }
        shapes.end()
    }
}

object TickWaterHandler: EffectHandler<TickEffect>(javaClass<TickEffect>(), 0) {
    override fun invoke(context: Context, effect: TickEffect) {
        val positions = positionsOf(context)

        var i = 0
        while(i < positions.size()) {
            val position = positions[i]

            val push = GentlePushEffect(position, position + DOWNV)
            applyEffect(context, push)
            if(push.obstructed) {
                positions.remove(i)
                continue
            }

            positions[i] = position + DOWNV // TODO: arithmetic in place
            i += 1
        }
    }
}

object KWater: Key<MutableList<Vec2iv>> {}

fun positionsOf(context: Context): MutableList<Vec2iv> {
    if(KWater in context)
        return context[KWater]
    val result = arrayListOf<Vec2iv>()
    context[KWater] = result
    addEffectHandler(context, DrawWaterHandler)
    addEffectHandler(context, TickWaterHandler)
    return result
}

fun makeWater(context: Context, position: Vec2iv) {
    val positions = positionsOf(context)

    positions.add(position)
}
