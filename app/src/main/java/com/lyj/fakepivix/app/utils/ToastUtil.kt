package com.lyj.fakepivix.app.utils

import android.content.Context
import android.os.Handler
import android.support.annotation.IdRes
import android.widget.Toast
import com.lyj.fakepivix.app.App

/**
 * @author greensun
 *
 * @date 2019/4/12
 *
 * @desc
 */
object ToastUtil {
    private var mToast: Toast? = null
    private val mHandler = Handler()

    fun showToast(@IdRes content: String) {
        mHandler.post {
            if (mToast != null) {
                mToast?.setText(content)
            } else {
                mToast = Toast.makeText(App.context, content, Toast.LENGTH_SHORT)
            }
            mToast?.show()
        }
    }
}