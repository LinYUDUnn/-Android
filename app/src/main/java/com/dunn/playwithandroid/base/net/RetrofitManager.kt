package com.dunn.playwithandroid.base.net

import com.dunn.playwithandroid.base.Const
import com.dunn.playwithandroid.base.net.interceptor.AddCookiesInterceptor
import com.dunn.playwithandroid.base.net.interceptor.SaveCookiesInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by DunnLin on 2019/7/26.
 */
object RetrofitManager {

    private val okHttpClient: OkHttpClient by lazy {
        getOkHttpClient(true)
    }

    fun <S> create(service: Class<S>): S {
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(Const.WAN_ANDROID_URL)
            .build()
        return retrofit.create(service)
    }

    fun getOkHttpClient(flag: Boolean): OkHttpClient {
        val builder = OkHttpClient.Builder().apply {
            connectTimeout(10, TimeUnit.SECONDS)
            writeTimeout(10, TimeUnit.SECONDS)
            readTimeout(10, TimeUnit.SECONDS)

            if (flag) {
                //配置log打印拦截器
                val logginInterruptor = HttpLoggingInterceptor()
                logginInterruptor.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(logginInterruptor)
            }
            addInterceptor(SaveCookiesInterceptor())
            addInterceptor(AddCookiesInterceptor())
        }
        return builder.build()
    }

}