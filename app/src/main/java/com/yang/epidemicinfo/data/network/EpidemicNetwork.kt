package com.yang.epidemicinfo.data.network

import android.util.Log
import com.yang.epidemicinfo.data.network.api.GetProvinceDataService
import com.yang.epidemicinfo.data.network.api.GetProvinceInfoService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.*

class EpidemicNetwork {

    private val getProvinceDataService = ServiceCreator.createAboutKnowledge(GetProvinceDataService::class.java)
    private val getProvinceInfoService = ServiceCreator.createAboutKnowledge(GetProvinceInfoService::class.java)

    suspend fun getProvinceData() = withContext(Dispatchers.IO){ getProvinceDataService.getProvinceData().await() }
    suspend fun getProvinceInfo(where:String) = withContext(Dispatchers.IO){ getProvinceInfoService.getProvinceData(where).await() }

    private suspend fun <T> Call<T>.await(): T {
        //suspendCoroutine 这个方法并不是帮我们启动协程的，它运行在协程当中
        // 并且帮我们获取到当前协程的 Continuation 实例，
        // 也就是拿到回调，方便后面我们调用它的
        // resume 或者 resumeWithException 来返回结果或者抛出异常
        return suspendCoroutine { continuation ->
            Log.e("await",Thread.currentThread().name)
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    t.printStackTrace()
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    Log.e("onResponse",Thread.currentThread().name)
                    val body = response.body()
                    if(response.isSuccessful){
                        Log.e("BaseOnResponse","isSuccessful")
                        Log.e("BaseOnResponse",response.message())
                    }
                    if (body != null){
                        Log.e("BaseOnResponse",response.body().toString())
                        continuation.resume(body)
                    }
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }
            })
        }
    }

    companion object {
        private var network: EpidemicNetwork? = null
        fun getInstance(): EpidemicNetwork {
            if (network == null) {
                synchronized(EpidemicNetwork::class.java) {
                    if (network == null) {
                        network = EpidemicNetwork()
                    }
                }
            }
            return network!!
        }

    }





}