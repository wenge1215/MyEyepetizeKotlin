package wenge.com.myeyepetizekotlin.network

import android.content.Context
import android.util.Log
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import wenge.com.myeyepetizekotlin.utils.NetworkUtils

/**
 * Created by WENGE on 2017/8/31.
 * 备注：拦截缓存状态
 */

class CacheInterceptor(context: Context) : Interceptor {
    val context = context

    /**
     * 有网络---> 获取网络缓存
     * 无网络---> 获取本地缓存
     */
    override fun intercept(chain: Interceptor.Chain?): Response? {
        var request = chain?.request()
        if (NetworkUtils.isNetConneted(context)) {
            val response: Response?
            val maxAge: Int
            val cacheControl: String

            try {
                response = chain?.proceed(request)
                // read from cache for 60 s (在线缓存过期时间)
                maxAge = 60
                cacheControl = request?.cacheControl().toString()
                Log.e("CacheInterceptor", "6s load cahe" + cacheControl)
                return response?.newBuilder()
                        ?.removeHeader("Pragma")
                        ?.removeHeader("Cache-Control")
                        ?.header("Cache-Control", "public, max-age=" + maxAge)
                        ?.build()
            } catch (e: Exception) {
                return null
            }


        } else {
            Log.e("CacheInterceptor", " no network load cahe")
            request = request?.newBuilder()?.cacheControl(CacheControl.FORCE_CACHE)?.build()
            val response = chain?.proceed(request)
            //set cahe times is 3 days (离线缓存过期时间)
            val maxStale = 60 * 60 * 24 * 3
            return response?.newBuilder()
                    ?.removeHeader("Pragma")
                    ?.removeHeader("Cache-Control")
                    ?.header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    ?.build()
        }
        return null;
    }
}