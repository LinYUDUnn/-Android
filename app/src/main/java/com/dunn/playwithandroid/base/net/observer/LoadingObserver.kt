package com.dunn.playwithandroid.base.net.observer

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.ui.widget.LoadingDialog
import com.shehuan.nicedialog.BaseNiceDialog
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference

/**
 * Created by DunnLin on 2019/7/25.
 */
abstract class LoadingObserver<E>(context: Context, showLoading: Boolean = true, showErrorTip: Boolean = true) :
    BaseObserver<E>(showErrorTip) {

    private val wrContext: WeakReference<Context> = WeakReference(context)

    private var loadingDialog: BaseNiceDialog? = null


    private fun initLoading(): BaseNiceDialog {
        return LoadingDialog.newInstance()
    }

    init {
        if (showLoading) {
            loadingDialog = initLoading()
        }
    }

    override fun onSubscribe(d: Disposable) {
        showLoading()
        super.onSubscribe(d)
    }

    override fun onError(e: Throwable) {
        dismissLoading()
        super.onError(e)
    }

    override fun onNext(t: E) {
        dismissLoading()
        super.onNext(t)
    }

    /**
     *显示loading
     */
    private fun showLoading() {
        loadingDialog?.show((wrContext.get() as AppCompatActivity).supportFragmentManager)
    }

    private fun dismissLoading() {
        loadingDialog?.dismiss()
        wrContext.clear()
    }
}