package net.byteabyte.mobiletv

import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import junit.framework.AssertionFailedError
import kotlin.reflect.KClass

fun waitForActivityIntent(activityName: String, timeout: Long = 10000, sleepMilliseconds: Long = 500) {
    retryUntilTimeout(AssertionFailedError::class, timeoutMillis = timeout, sleepMillis = sleepMilliseconds) {
        Intents.intended(IntentMatchers.hasComponent(activityName))
    }
}

/**
 * Retries action if it throws the given exception until it succeeds or until the timeout happens.
 */
@Suppress("Detekt.TooGenericExceptionCaught")
fun <E: Throwable> retryUntilTimeout(exceptionType: KClass<out E>, timeoutMillis: Long = 10000, sleepMillis: Long = 100, action: () -> Unit) {
    try {
        action()
    } catch (e: Throwable) {
        if (timeoutMillis > 0 && exceptionType.isInstance(e)) {
            Thread.sleep(sleepMillis)
            val newTimeout = timeoutMillis - sleepMillis
            retryUntilTimeout(exceptionType, newTimeout, sleepMillis, action)
        } else {
            throw e
        }
    }
}