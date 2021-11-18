package com.example.apilibv2

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.*


class RequestHead : Interceptor {
    val TAG = this.javaClass.simpleName
    val headers: Map<String, String>
        get() {
            val headers: MutableMap<String, String> = HashMap()
            return headers
        }

    override fun intercept(chain: Interceptor.Chain): Response {
        TODO("Not yet implemented")
    }

}