package h_mal.appttude.com.driver.utils

/**
 * Extension function that will execute high order if value is true or do nothing
 *
 * @sample #boolean.isTrue{ #Do something when its true }
 */
inline fun Boolean.isTrue(block: () -> Unit) {
    if (this) {
        block()
    }
}

/**
 * Extension function that will execute high order if value is not null or do nothing
 *
 * @sample #nullable.isNotNull{i -> i.doSomethingSinceItsNotNull() }
 */
inline fun <T, R> T?.isNotNull(block: (T) -> R): R? {
    return if (this != null) {
        block(this)
    } else {
        null
    }
}