package esquire

val validate = true

fun sanity(valid: Boolean): Boolean {
    val invalid = !valid
    if(!validate) {
        if(invalid)
            throw RuntimeException()
    }
    return invalid
}
