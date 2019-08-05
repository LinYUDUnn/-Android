package com.dunn.playwithandroid.base.net.observer

import android.content.Context
import android.text.TextUtils
import com.dunn.playwithandroid.App
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.utils.ToastUtil
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference

/**
 * Created by DunnLin on 2019/7/25.
 */
public abstract class BaseObserver<E>(private val showErrorTip: Boolean = true) : Observer<E> {

    //定义context的弱引用
    private val wContext: WeakReference<Context> = WeakReference(App.getApp())

    private lateinit var disposable: Disposable

    override fun onComplete() {

    }

    override fun onSubscribe(d: Disposable) {
        disposable = d
    }

    override fun onNext(t: E) {
        onSuccess(t)
    }

    override fun onError(e: Throwable) {
        val responseException: ResponseException = e as ResponseException
        val errorMessage = responseException.getErrorMessage()
        if (showErrorTip && !TextUtils.isEmpty(errorMessage)) {
            ToastUtil.show(wContext.get(), errorMessage)
        }
        onError(responseException)
    }

    fun getDisposable(): Disposable {
        return disposable
    }

    abstract fun onSuccess(data: E)

    abstract fun onError(e: ResponseException)
}