package com.dunn.playwithandroid.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import com.dunn.playwithandroid.R
import com.shehuan.statusview.StatusView
import com.shehuan.statusview.StatusViewBuilder
import kotlinx.android.synthetic.main.toolbar_layout.*

abstract class BaseActivity : AppCompatActivity() {

    protected val TAG: String = this.javaClass.simpleName

    lateinit var context: BaseActivity

    /**
     * 页面多状态布局切换控件
     */
    protected lateinit var statusView: StatusView

    /**
     * 表明该参数、变量或者函数返回值应该是一个 layout 布局文件类型的资源
     */
    @LayoutRes
    abstract fun initLayoutResId(): Int

    abstract fun initData()

    abstract fun initView()

    abstract fun initLoad()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(initLayoutResId())
        context = this

        initData()
        initView()
        initLoad()
    }

    /**
     *页面多状态布局切换控件
     */
    protected fun initStatusView(id: Int, errorRetry: (View) -> Unit) {
        statusView = StatusView.init(this, id).apply {
            setLoadingView(R.layout.dialog_loading_layout)
            config(
                StatusViewBuilder.Builder().showEmptyRetry(false).setOnErrorRetryClickListener(errorRetry).build()
            )
        }
    }

    //@StringRes 表明该参数、变量或者函数返回值应该是一个字符串类型的资源
    protected fun initToolbar(@StringRes titleId: Int) {
        initToolbar(getString(titleId))
    }

    protected fun initToolbar(titleStr: String) {
        //run函数是let,with两个函数结合体，准确来说它弥补了let函数在函数体内必须使用it参数替代对象
        toolbar.run {
            title = titleStr
            setSupportActionBar(this)
            setNavigationOnClickListener {
                finish()
            }
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

    }
}