package com.cats.self.httpencapsulationkotlin.http

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Created by cp on 2019/4/15.
 */
class HttpCommonInterceptor : Interceptor {
    private val mHeaderParamsMap : HashMap<String,String> = HashMap()
    override fun intercept(chain: Interceptor.Chain?): Response {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val oldRequest = chain!!.request()
        //新的请求
        val requestBuilder = oldRequest.newBuilder()
        requestBuilder.method(oldRequest.method(),oldRequest.body())

        //添加公共参数，添加到header中
        if (mHeaderParamsMap.size > 0){
            for (param:Map.Entry<String,String> in mHeaderParamsMap.entries){
                requestBuilder.addHeader(param.key,param.value)
            }
        }
        val newRequest : Request = requestBuilder.build()
        return chain.proceed(newRequest)
    }
    class Builder{
        private val mHttpCommonInterceptor : HttpCommonInterceptor
        init {
            mHttpCommonInterceptor = HttpCommonInterceptor()
        }
        fun addHeaderParams(key:String,value:String) : Builder{
            mHttpCommonInterceptor.mHeaderParamsMap.put(key,value)
            return this
        }
        fun addHeaderParams(key:String,value:Int) : Builder{
            return addHeaderParams(key,value.toString())
        }
        fun addHeaderParams(key:String,value:Float) : Builder{
            return addHeaderParams(key,value.toString())
        }
        fun addHeaderParams(key:String,value:Long) : Builder{
            return addHeaderParams(key,value.toString())
        }
        fun addHeaderParams(key:String,value:Double) : Builder{
            return addHeaderParams(key,value.toString())
        }

        fun build():HttpCommonInterceptor{
            return mHttpCommonInterceptor
        }
    }
}