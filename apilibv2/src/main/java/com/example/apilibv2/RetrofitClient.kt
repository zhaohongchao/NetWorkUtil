package com.example.apilibv2

import android.content.Context
import com.google.gson.Gson
import me.jessyan.retrofiturlmanager.BuildConfig
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @Package: com.guider.health.apilib
 * @ClassName: RetrofitClient
 * @Description: retrofit网络请求类
 * @Author: hjr
 * @CreateDate: 2020/10/10 9:21
 * Copyright (C), 1998-2020, GuiderTechnology
 */

class RetrofitClient private constructor() {


    var mRetrofit: Retrofit? = null

    companion object {
        val single_Instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            RetrofitClient()
        }
    }

    private var mContext: Context? = null
    private var mac: String? = null

    /**
     * 设置上下文和mac地址，请求头使用
     */
    fun setContextAndMac(mContext: Context?, api: String) {
        this.mContext = mContext
        this.mac = mac
        ApiConsts.API_HOST = api
    }

    private fun getHttpClient(): OkHttpClient {
        val builder = RetrofitUrlManager.getInstance().with(OkHttpClient.Builder())
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(loggingInterceptor)
        }
        val requestHead = RequestHead()
        builder.addInterceptor(requestHead)
        return builder.build()
    }

    fun getCoroutinesRetrofit(): Retrofit {
        //将每个 BaseUrl 进行初始化,运行时可以随时改变 DOMAIN_NAME 对应的值,从而达到切换 BaseUrl 的效果
        val client: OkHttpClient = getHttpClient()
        if (mRetrofit == null) {
            synchronized(RetrofitClient::class.java) {
                if (mRetrofit == null) {
                    mRetrofit = Retrofit.Builder()
                            .baseUrl(ApiConsts.API_HOST)
                            .addConverterFactory(ResultConverterFactory.create(Gson()))
                            .client(client)
                            .build()
                }
            }
        }

        return mRetrofit!!
    }

}