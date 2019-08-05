package com.dunn.playwithandroid.base.net.convert

import com.dunn.playwithandroid.base.net.exception.ExceptionHandler
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function

/**
 * Created by DunnLin on 2019/7/25.
 */
class ExceptionConvert<E> : Function<Throwable, ObservableSource<out E>> {
    override fun apply(t: Throwable): ObservableSource<out E> {
        return Observable.error(ExceptionHandler.handle(t))
    }
}