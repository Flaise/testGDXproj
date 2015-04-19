package testGDX


object GentlePushHandler: EffectHandler<GentlePushEffect>(javaClass<GentlePushEffect>(), 0) {
    override fun invoke(effect: GentlePushEffect) = false
}


fun make(position: Vec2iv) {

}

//EffectHandler<GEffect>(val type: Class<GEffect>, val priorityDescending: Int):
//
//[<JavaScript>]
//module Bedrock =
//let Key = 5
//
//let GetSurfaces (state:State) =
//GetMap Key state
//
//let Present position state =
//match GetSurfaces(state).TryFind position.X with
//| None -> false
//| Some y -> y <= position.Y
//
//let private handleGentlePush (effect:GentlePushEffect) (state:State) =
//if Present effect.Destination state then
//{effect with Obstructed=true}, state
//else
//effect, state
//
//let SetSurfaces (positions:Map<int, int>) (state:State) =
//SetState
//Key
//positions
//(Effects.Register GentlePushEffect.TypeID handleGentlePush 1)
//(Effects.Unregister<GentlePushEffect> GentlePushEffect.TypeID 1)
//state
//
//let Make (position:Vector2i) (state:State) =
//SetSurfaces (GetSurfaces(state).Add(position.X, position.Y)) state
//
//let GetElevation x state =
//GetSurfaces(state).TryFind x
