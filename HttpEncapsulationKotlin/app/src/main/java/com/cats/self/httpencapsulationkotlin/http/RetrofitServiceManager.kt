package com.cats.self.httpencapsulationkotlin.http

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by cp on 2019/4/15.
 */
class RetrofitServiceManager {
    companion object {
        const val DEFAULT_TIME_OUT: Long = 5 //超时时间 5s
        const val DEFAULT_READ_TIME_OUT: Long = 10 //读取超时时间 10s
    }

    private var mRetrofit : Retrofit

    init {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS) // 设置连接超时
                .readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS) // 设置读取数据超时
                .writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS) // 设置写数据超时

        //添加公共参数拦截器
        val commonInterceptor : HttpCommonInterceptor = HttpCommonInterceptor.Builder()
                .addHeaderParams("paltform","android")
                .addHeaderParams("userToken","1234343434dfdfd3434")
                .addHeaderParams("userId","123445").build()
        builder.addInterceptor(commonInterceptor)
        //创建Retrofit
        mRetrofit = Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("")
                .build()
    }
}