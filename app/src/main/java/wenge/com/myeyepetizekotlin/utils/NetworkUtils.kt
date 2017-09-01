package wenge.com.myeyepetizekotlin.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * Created by WENGE on 2017/8/31.
 * 备注：判断网络状态的工具类
 * object 用来实现单例模式
 */

object NetworkUtils {

    /**
     * 网络是否可用
     */
    fun isNetConneted(context: Context): Boolean {
        val connecManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager       //as 用于类型装换
        val networkInfo: NetworkInfo? = connecManager.activeNetworkInfo
        if (networkInfo == null) {
            return false
        } else {
            return networkInfo.isAvailable && networkInfo.isConnected
        }
    }

    /**
     * 指定类型的网络是否可用
     */
    fun isNetworkkConneted(context: Context, netType: Int): Boolean {
        if (!isNetConneted(context)) {
            return false
        }
        val connectManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connectManager.getNetworkInfo(netType)
        if (networkInfo == null) {
            return false
        } else {
            return networkInfo.isAvailable && networkInfo.isConnected
        }
    }


    /**
     * 移动网络连接是否可用
     */
    fun isPhoneNetConneted(context: Context): Boolean {
        val netType: Int = ConnectivityManager.TYPE_MOBILE
        return isNetworkkConneted(context, netType)
    }

    /**
     * WiFi连接是否可用
     */
    fun isWifiNetConnneted(context: Context): Boolean {
        val netType = ConnectivityManager.TYPE_WIFI
        return isNetworkkConneted(context, netType)
    }

}
