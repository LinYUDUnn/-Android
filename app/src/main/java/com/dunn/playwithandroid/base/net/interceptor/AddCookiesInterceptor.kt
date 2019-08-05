package com.dunn.playwithandroid.base.net.interceptor

import com.dunn.playwithandroid.utils.sp.SpUtil
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by DunnLin on 2019/7/26.
 */
class AddCookiesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val cookies = SpUtil.getCookies().split("#")
        for (cookie in cookies) {
            builder.addHeader("Cookie", cookie)
        }
        return chain.proceed(builder.build())
    }
}