package com.dunn.playwithandroid.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dunn.playwithandroid.R
import com.dunn.playwithandroid.base.activity.BaseActivity
import com.dunn.playwithandroid.base.activity.BaseMvpActivity
import com.dunn.playwithandroid.base.net.exception.ResponseException
import com.dunn.playwithandroid.bean.RegisterBean
import com.dunn.playwithandroid.bean.event.AccountEvent
import com.dunn.playwithandroid.ui.widget.WrapTextWatcher
import com.dunn.playwithandroid.utils.sp.SpUtil
import kotlinx.android.synthetic.main.activity_register.*
import org.greenrobot.eventbus.EventBus

class RegisterActivity : BaseMvpActivity<RegisterPresenterImpl>(), RegisterContract.View {

    companion object {
        fun start(context: BaseActivity) {
            val intent = Intent(context, RegisterActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initPresenter(): RegisterPresenterImpl {
        return RegisterPresenterImpl(this)
    }

    override fun initLayoutResId(): Int {
        return R.layout.activity_register
    }

    override fun initData() {
    }

    override fun initView() {
        initToolbar(R.string.register)

        registerPasswordTTL.isPasswordVisibilityToggleEnabled = true
        registerRepasswordTTL.isPasswordVisibilityToggleEnabled = true
        registerUsernameET.addTextChangedListener(WrapTextWatcher(registerUsernameTTL))
        registerPasswordET.addTextChangedListener(WrapTextWatcher(registerPasswordTTL))
        registerRepasswordET.addTextChangedListener(WrapTextWatcher(registerRepasswordTTL))

        registerBtn.setOnClickListener {
            if (registerUsernameET.text.isEmpty()) {
                registerUsernameTTL.error = getString(R.string.username_empty)
                registerUsernameTTL.isErrorEnabled = true
                return@setOnClickListener
            }
            if (registerPasswordET.text.isEmpty()) {
                registerPasswordTTL.error = getString(R.string.password_empty)
                return@setOnClickListener
            }

            if (registerRepasswordET.text.isEmpty()) {
                registerRepasswordTTL.error = getString(R.string.repassword_empty)
                return@setOnClickListener
            }

            if (registerPasswordET.text.toString() != registerRepasswordET.text.toString()) {
                registerPasswordTTL.error = getString(R.string.password_unequal)
                registerRepasswordTTL.error = getString(R.string.password_unequal)
                return@setOnClickListener
            }

            presenter.register(
                registerUsernameET.text.toString(),
                registerPasswordET.text.toString(),
                registerRepasswordET.text.toString()
            )
        }
    }

    override fun initLoad() {
    }

    override fun onRegisterSuccess(data: RegisterBean) {
        SpUtil.setUsername(data.username)
        EventBus.getDefault().post(AccountEvent())
        finish()
    }

    override fun onRegisterError(e: ResponseException) {
    }

}
