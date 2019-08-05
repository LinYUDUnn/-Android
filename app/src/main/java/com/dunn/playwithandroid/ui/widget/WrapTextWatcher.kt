package com.dunn.playwithandroid.ui.widget

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputLayout

/**
 * Created by DunnLin on 2019/7/26.
 */
class WrapTextWatcher(private val textInputLayout: TextInputLayout) : TextWatcher {
    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (s!!.isEmpty()) {
            textInputLayout.error = null
            textInputLayout.isErrorEnabled = false
        }
    }
}