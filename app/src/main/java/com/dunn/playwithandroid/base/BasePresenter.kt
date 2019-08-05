package com.dunn.playwithandroid.base

import android.content.Context
import com.dunn.playwithandroid.base.fragment.BaseFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by DunnLin on 2019/7/24.
 */
abstract class BasePresenter<out V : BaseView>(val view: V) {
    protected val context: Context = if (view is BaseFragment) {
        view.mContext
    } else {
        view as Context
    }

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun addDisposable(disposable: Disposable) {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.add(disposable)
        }
    }

    fun removeDisposable(disposable: Disposable) {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.remove(disposable)
        }
    }

    fun detach() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
    }

}