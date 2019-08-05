package com.dunn.playwithandroid.base.fragment

import android.os.Bundle
import com.dunn.playwithandroid.base.BasePresenter

/**
 * Created by DunnLin on 2019/7/27.
 */
abstract class BaseMvpFragment<P : BasePresenter<*>> : BaseFragment() {

    lateinit var presenter:P

    abstract fun initPresenter(): P

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        presenter = initPresenter()
        super.onActivityCreated(savedInstanceState)
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

}