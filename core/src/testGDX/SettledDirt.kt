package testGDX

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import java.util.NoSuchElementException
import java.util.concurrent.CopyOnWriteArraySet


object TickSettledDirtHandler: EffectHandler<TickEffect>(javaClass<TickEffect>(), 2) {
    override fun invoke(context: Context, effect: TickEffect) {
        val positions = settledDirtPositionsOf(context)

        for(position in positions) {
            val below = position + DOWNV
            if(isBedrockAt(context, below))
                continue
            if(below in positions)
                continue
            if(position + LEFTV in positions || position + RIGHTV in positions)
                continue
            positions.remove(position)
            makeDirt(context, position)
        }
    }
}

object PushSettledDirtHandler: EffectHandler<PushEffect>(javaClass<PushEffect>(), 1) {
    override fun invoke(context: Context, effect: PushEffect) {
        val positions = settledDirtPositionsOf(context)
        if(effect.destination !in positions)
            return
        effect.obstructed = true
        if(effect.destination + DOWNV in positions)
            return
        unsettle(context, effect.destination)
    }
}

object DirtMovedSettledDirtHandler: EffectHandler<DirtMovedEffect>(javaClass<DirtMovedEffect>(), 0) {
    override fun invoke(context: Context, effect: DirtMovedEffect) {
        val positions = settledDirtPositionsOf(context)
        if(effect.position + LEFTV in positions || effect.position + RIGHTV in positions
                || effect.position + DOWNV in positions)
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

object KSettledDirt: Key<MutableSet<Vec2iv>>

fun settledDirtPositionsOf(context: Context): MutableSet<Vec2iv> {
    val result = context[KSettledDirt]
    if(result != null)
        return result
    val result2 = CopyOnWriteArraySet<Vec2iv>()
    context[KSettledDirt] = result2
    addEffectHandler(context, DrawSettledDirtHandler)
    addEffectHandler(context, PushSettledDirtHandler)
    addEffectHandler(context, DirtMovedSettledDirtHandler)
    addEffectHandler(context, TickSettledDirtHandler)
    return result2
}

fun makeSettledDirt(context: Context, position: Vec2iv) {
    val positions = settledDirtPositionsOf(context)
    positions.add(position)
}

fun unsettle(context: Context, position: Vec2iv) {
    val positions = settledDirtPositionsOf(context)
    if(position !in positions)
        throw NoSuchElementException() // TODO: sanity testing
    positions.remove(position)
    makeDirt(context, position)
}
