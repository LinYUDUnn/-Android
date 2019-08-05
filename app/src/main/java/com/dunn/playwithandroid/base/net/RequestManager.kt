package com.dunn.playwithandroid.base.net

import com.dunn.playwithandroid.base.BasePresenter
import com.dunn.playwithandroid.base.BaseResponse
import com.dunn.playwithandroid.base.net.convert.ExceptionConvert
import com.dunn.playwithandroid.base.net.convert.ResponseConvert
import com.dunn.playwithandroid.base.net.observer.BaseObserver
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by DunnLin on 2019/7/25.
 */
object RequestManager {

    /**
     * 通用网络请求方法
     */
    fun <E> execute(
        presenter: BasePresenter<*>,
        observable: Observable<BaseResponse<E>>,
        observer: BaseObserver<E>
    ): Disposable {
        observable
            .map(ResponseConvert())
            .onErrorResumeNext(ExceptionConvert<E>())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
        presenter.addDisposable(observer.getDisposable())
        return observer.getDisposable()
    }

    /**
     * 通用耗时任务
     */
    fun <E> execute(presenter: BasePresenter<*>, listener: ExecuteListener<E>, observer: BaseObserver<E>): Disposable {
        Observable.create(ObservableOnSubscribe<E> {
            it.onNext(listener.onExecute())
            it.onComplete()
        }).onErrorResumeNext(ExceptionConvert<E>())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
        presenter.addDisposable(observer.getDisposable())
        return observer.getDisposable()
    }

    interface ExecuteListener<E> {
        fun onExecute(): E
    }
}