package com.bguina.trader.extensions.rx2

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import org.reactivestreams.Publisher
import java.util.concurrent.TimeUnit
import io.reactivex.functions.Function

/**
 * Repeat operators
 * */
fun <T> Single<T>.every(
    interval: Long,
    timeUnit: TimeUnit
): Observable<T> = Observable.interval(0, interval, timeUnit)
    .flatMapSingle { this }

/**
 * Retry operators
 * */
fun <T> Single<T>.onErrorRetryWithDelay(
    limitEval: (e: Throwable) -> Int? = { 1 },
    delayMsEval: (e: Throwable) -> Long
): Single<T> = retryWhen(DelayedRetryCountLimit(
    retriesLimitEval = limitEval,
    delayMsEval = delayMsEval
))



/**
 * Utility Rx class to make the retryWhen() operator re-subscribe after a specified delay.
 * */
class DelayedRetryCountLimit(
    private val retriesLimitEval: (Throwable) -> Int?,
    private val delayMsEval: (Throwable) -> Long
) : Function<Flowable<out Throwable>, Publisher<out Any>> {
    /**
     * Map to each throwable class its own retry count
     * */
    private var throwableToRetryCount: HashMap<Class<out Throwable>, Int> = HashMap()

    override fun apply(
        attempts: Flowable<out Throwable>
    ): Publisher<*> = attempts.flatMapSingle { throwable ->
        val previousRetryCount: Int = throwableToRetryCount[throwable::class.java] ?: 0
        throwableToRetryCount[throwable::class.java] = previousRetryCount + 1

        if (false != retriesLimitEval(throwable)?.run { this > previousRetryCount }) {
            // When this Single calls onNext, the original
            // Single will be retried (i.e. re-subscribed).
            Single.timer(delayMsEval(throwable), TimeUnit.MILLISECONDS)
        } else
        // Max retries hit. Just pass the error along.
            Single.error(throwable)
    }
}
