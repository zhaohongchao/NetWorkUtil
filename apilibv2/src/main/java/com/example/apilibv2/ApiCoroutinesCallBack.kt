package com.example.apilibv2

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler

/**
 * @Package: com.guider.baselib.utils
 * @ClassName: ApiCoroutinesCallBack
 * @Description: api协程统一回调
 * @Author: hjr
 * @CreateDate: 2020/10/20 9:24
 * Copyright (C), 1998-2020, GuiderTechnology
 */
object ApiCoroutinesCallBack {

    val handler = CoroutineExceptionHandler { _, e ->
        if (e is RuntimeException){
            if (e.message == "customErrorMsg") {

            } else {
                Log.e("GuiderGpsApiError", "==============: ", )
            }
        }else {

        }
        Log.e("GuiderGpsApiError", e.message.toString())
    }

    suspend fun resultParse(onStart: (() -> Unit)? = null, block: suspend () -> Unit,
                            onError: ((Exception) -> Unit)? = null,
                            onRequestFinish: (() -> Unit)? = null) {
        try {
            onStart?.invoke()
            block()
        } catch (e: Exception) {
            onError?.invoke(e)
            if (e is RuntimeException){
                if (e.message == "customErrorMsg") {


                }else if (e.message=="java.lang.IllegalStateException: Expected BEGIN_ARRAY but was STRING at line 1 column 2 path \$") {
                    Log.e("TAG", "resultParse: --------------", )
                    //ToastUtil.show(BaseApplication.guiderHealthContext, " ---"+e.message.toString())
                }else{
                    Log.e("TAG", "resultParse: +++++++++++++++", )

                }
            }else {

            }
            Log.e("GuiderGpsApiError", e.message.toString())
        } finally {
            onRequestFinish?.invoke()
        }
    }

}