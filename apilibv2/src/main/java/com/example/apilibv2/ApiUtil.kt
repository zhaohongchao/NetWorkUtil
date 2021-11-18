package com.example.apilibv2

import android.content.Context
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

/**
 * @Package: com.guider.health.apilib
 * @ClassName: GuiderApiUtil
 * @Description: 接口调用工具类
 * @Author: hjr
 * @CreateDate: 2020/10/10 9:58
 * Copyright (C), 1998-2020, GuiderTechnology
 */
class ApiUtil<T>(t: T) {

    companion object{
        fun setContextAndAPI(mContext: Context?, api: String) {
            RetrofitClient.single_Instance.setContextAndMac(mContext, api)
        }

        inline fun <reified T> getDefaultApi() :T {
            return RetrofitClient.single_Instance.getCoroutinesRetrofit().create(
                T::class.java
            )
        }

        fun uploadFile(filePath: String): MultipartBody.Part? {
            val file = File(filePath)
            val requestFile = file.asRequestBody("application/otcet-stream".toMediaTypeOrNull())
            return MultipartBody.Part.createFormData(
                "file", file.name, requestFile
            )
        }
    }

}