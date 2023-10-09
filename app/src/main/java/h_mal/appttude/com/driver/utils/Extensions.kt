package h_mal.appttude.com.driver.utils

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

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

/**
 * Like a #Collections.map{ } function
 * but allows for suspended actions inside
 */
suspend inline fun <T, R> Iterable<T>.mapIndexSuspend(crossinline transform: suspend (index: Int, T) -> R) =
    coroutineScope {
        mapIndexed { index: Int, t: T ->
            async {
                transform(
                    index,
                    t
                )
            }
        }.map { it.await() }
    }

suspend inline fun <T, R> Iterable<T>.mapSuspend(crossinline transform: suspend (T) -> R): List<R> =
    coroutineScope { map { t: T -> async { transform(t) } }.map { it.await() } }
