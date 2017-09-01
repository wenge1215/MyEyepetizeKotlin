package wenge.com.myeyepetizekotlin.network

import android.content.Context
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by WENGE on 2017/8/31.
 * 备注：retrofit 基本配置
 * private constructor  标注主代码块可见性，如果没有注解或可见性修饰，可以省略constructor
 */

class RetrofitClient private constructor(context: Context, baseUrl: String) {
    val context: Context = context
    val DEFORE_TIME: Long = 10
    var httpCacheDirectory: File? = null
    var cache: Cache? = null    //okhttp缓存对象
    var okHttpClient: OkHttpClient? = null
    var retrofit: Retrofit? = null


    /**
     * 初始化块
     */
    init {
        if (httpCacheDirectory == null) {
            httpCacheDirectory = File(context.cacheDir, "app_cache")
        }
        if (cache == null) {
            cache = Cache(httpCacheDirectory, 10 * 1024 * 1024)
        }

        /**
         * 日志拦截器
         */
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        /**
         * okhttp 对象
         */
        okHttpClient = OkHttpClient.Builder()
                .addNetworkInterceptor(loggingInterceptor)
                .addInterceptor(CacheInterceptor(context))
                .addNetworkInterceptor(CacheInterceptor(context))
                .cache(cache)
                .connectTimeout(DEFORE_TIME, TimeUnit.SECONDS)
                .writeTimeout(DEFORE_TIME, TimeUnit.SECONDS)
                .build()


        /**
         * retrofit 对象
         */
        retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())      //Rxjava 适配器对象，rxjava关联网络请求的结果
                .build()

    }

    /**
     * 静态初始化块
     * 用来初始化静态变量和静态方法
     */
    companion object {
        var instance: RetrofitClient? = null

        fun getInstance(context: Context, baseUrl: String): RetrofitClient {
            if (instance == null) {
                synchronized(RetrofitClient::class) {
                    instance = RetrofitClient(context, baseUrl)
                }
            }
            return instance!!;
        }
    }

    /**
     * 创建请求服务
     */
    fun <T> create(service: Class<T>?): T? {
        if (service == null) {
            throw RuntimeException("Api service is null!")
        }
        return retrofit?.create(service)
    }
}
