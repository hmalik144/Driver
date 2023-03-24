package h_mal.appttude.com.utils


inline fun Boolean.isTrue(block: () -> Unit){
    if (this) {
        block()
    }
}

inline fun <T, R> T.isNotNull(block: (T) -> R): R?{
    return if (this != null) {
        block(this)
    } else {
        null
    }
}