package com.dunn.playwithandroid.ui.splash

import com.dunn.playwithandroid.R
import com.dunn.playwithandroid.base.activity.BaseActivity
import com.dunn.playwithandroid.ui.main.MainActivity

class SplashActivity : BaseActivity() {

    override fun initLayoutResId(): Int {
        return R.layout.activity_splash
    }

    override fun initData() {
    }

    override fun initView() {
        MainActivity.start(this)
        finish()
    }

    override fun initLoad() {
    }

}
