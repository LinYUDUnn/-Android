package com.dunn.playwithandroid.base.activity

import android.os.Bundle
import com.dunn.playwithandroid.base.BasePresenter

/**
 * Created by DunnLin on 2019/7/24.
 */
abstract class BaseMvpActivity<P : BasePresenter<*>> : BaseActivity() {
    lateinit var presenter: P

    abstract fun initPresenter(): P

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = initPresenter()
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

}