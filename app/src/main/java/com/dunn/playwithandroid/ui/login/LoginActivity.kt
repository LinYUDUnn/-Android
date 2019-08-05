package com.dunn.playwithandroid.ui.login

import android.content.Intent
import android.graphics.Paint
import com.dunn.playwithandroid.R
import com.dunn.playwithandroid.base.activity.BaseActivity
import com.dunn.playwithandroid.base.activity.BaseMvpActivity
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.bean.LoginBean
import com.dunn.playwithandroid.bean.event.AccountEvent
import com.dunn.playwithandroid.ui.register.RegisterActivity
import com.dunn.playwithandroid.ui.widget.WrapTextWatcher
import com.dunn.playwithandroid.utils.sp.SpUtil
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.EventBus

class LoginActivity : BaseMvpActivity<LoginPresenterImpl>(), LoginContract.View {

    companion object {
        fun start(context: BaseActivity) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onLoginSuccess(data: LoginBean) {
        SpUtil.setUsername(data.username)
        EventBus.getDefault().post(AccountEvent())
        finish()
    }

    override fun onLoginError(e: ResponseException) {
    }

    override fun initPresenter(): LoginPresenterImpl {
        return LoginPresenterImpl(this)
    }

    override fun initLayoutResId(): Int {
        return R.layout.activity_login
    }

    override fun initData() {
    }

    override fun initView() {
        initToolbar(R.string.login)
        registerTv.paint.flags = Paint.UNDERLINE_TEXT_FLAG
        registerTv.setOnClickListener {
            RegisterActivity.start(this)
        }

        loginPasswordTTL.isPasswordVisibilityToggleEnabled = true
        loginUsernameET.addTextChangedListener(WrapTextWatcher(loginUsernameTTL))
        loginPasswordET.addTextChangedListener(WrapTextWatcher(loginPasswordTTL))

        loginBtn.setOnClickListener {
            if (loginUsernameET.text.isEmpty()) {
                loginUsernameTTL.error = getString(R.string.username_empty)
                loginUsernameTTL.isErrorEnabled = true
                return@setOnClickListener
            }

            if (loginPasswordET.text.isEmpty()) {
                loginPasswordTTL.error = getString(R.string.password_empty)
                return@setOnClickListener
            }
            presenter.login(loginUsernameET.text.toString(), loginPasswordET.text.toString())
        }
    }

    override fun initLoad() {
    }


}
