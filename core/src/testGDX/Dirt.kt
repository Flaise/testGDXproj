package testGDX

import com.badlogic.gdx.graphics.glutils.ShapeRenderer


object DrawDirtHandler: EffectHandler<DrawEffect>(javaClass<DrawEffect>(), 2) {
    override fun invoke(context: Context, effect: DrawEffect) {
        val shapes = effect.shapes

        shapes.begin(ShapeRenderer.ShapeType.Filled)
        shapes.setColor(.7f, .5f, .1f, 1f)
        for((x, y) in dirtPositionsOf(context)) {
            shapes.rect(x.toFloat(), y.toFloat(), 1f, 1f)
        }
        shapes.end()
    }
}

object TickDirtHandler: EffectHandler<TickEffect>(javaClass<TickEffect>(), 1) {
    override fun invoke(context: Context, effect: TickEffect) {
        val positions = dirtPositionsOf(context)

        var i = 0
        while(i < positions.size()) {
            val position = positions[i]
            val dest = position + DOWNV

            val push = PushEffect(dest)
            applyEffect(context, push)
            if(push.obstructed) {
                positions.remove(i)
                makeSettledDirt(context, position)
                continue
            }

            val moved = DirtMovedEffect(dest)
            applyEffect(context, moved)
            if(moved.stuck) {
                positions.remove(i)
                makeSettledDirt(context, dest)
                continue
            }

            positions[i] = dest // TODO: arithmetic in place
            i += 1
        }
    }
}

object KDirt: Key<MutableList<Vec2iv>> {}

fun dirtPositionsOf(context: Context): MutableList<Vec2iv> {
    if(KDirt in context)
        return context[KDirt]
    val result = arrayListOf<Vec2iv>()
    context[KDirt] = result
    addEffectHandler(context, DrawDirtHandler)
    addEffectHandler(context, TickDirtHandler)
    return result
}

fun makeDirt(context: Context, position: Vec2iv) {
    val positions = dirtPositionsOf(context)
    positions.add(position)
}
