package wenge.com.myeyepetizekotlin.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log

/**
 * Created by WENGE on 2017/8/31.
 * 备注：判断网络状态的工具类
 * object 用来实现单例模式
 */

object NetworkUtils {

    fun isNetConneted(context: Context):Boolean{
        val connectManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo : NetworkInfo?= connectManager.activeNetworkInfo
        System.out.println(networkInfo?.isAvailable)
        System.out.println(networkInfo?.isConnected)
        if(networkInfo==null){
            return  false
        }else{
            return networkInfo.isAvailable&& networkInfo.isConnected
        }

    }

    fun isNetworkConnected(context: Context,typeMoblie : Int): Boolean{
        if(!isNetConneted(context)){
            return false
        }
        val connectManager  = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo : NetworkInfo = connectManager.getNetworkInfo(typeMoblie)
        if(networkInfo==null){
            return false;
        }else{
            return  networkInfo.isConnected && networkInfo.isAvailable
        }
    }

//    /**
//     * 网络是否可用
//     */
//    fun isNetConneted(context: Context): Boolean {
//        val connecManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager       //as 用于类型装换
//        val networkInfo: NetworkInfo? = connecManager.activeNetworkInfo
//        if (networkInfo == null) {
//            return false
//        } else {
//            return networkInfo.isConnected && isAvailableByPing(null)
//        }
//    }
//
//    /**
//     * 指定类型的网络是否可用
//     */
//    fun isNetworkConnected(context: Context, netType: Int): Boolean {
//        if (!isNetConneted(context)) {
//            return false
//        }
//        val connectManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val networkInfo: NetworkInfo? = connectManager.getNetworkInfo(netType)
//        if (networkInfo == null) {
//            return false
//        } else {
//            return networkInfo.isConnected && isAvailableByPing(null)
//        }
//    }
//
    /**
     * 判断网络是否可用
     *
     * 需添加权限 `<uses-permission android:name="android.permission.INTERNET"/>`
     *
     * 需要异步ping，如果ping不通就说明网络不可用
     *
     * @param ip ip地址（自己服务器ip），如果为空，ip为阿里巴巴公共ip
     * @return `true`: 可用<br></br>`false`: 不可用
     *
     *
     * 调用该方法判断网络，会造成504错误
     */
    fun isAvailableByPing(ip: String?): Boolean {
        var ip = ip
        if (ip == null || ip.length <= 0) {
            ip = "202.108.22.5"// 百度公共ip
        }
        val result = ShellUtils.execCmd(String.format("ping -c 1 %s", ip), false)
        val ret = result.result == 0
        if (result.errorMsg != null) {
            Log.d("NetworkUtils", "isAvailableByPing() called" + result.errorMsg)
        }
        if (result.successMsg != null) {
            Log.d("NetworkUtils", "isAvailableByPing() called" + result.successMsg)
        }
        return ret
    }

    /**
     * 移动网络连接是否可用
     */
    fun isPhoneNetConneted(context: Context): Boolean {
        val netType: Int = ConnectivityManager.TYPE_MOBILE
        return isNetworkConnected(context, netType)
    }

    /**
     * WiFi连接是否可用
     */
    fun isWifiNetConnneted(context: Context): Boolean {
        val netType = ConnectivityManager.TYPE_WIFI
        return isNetworkConnected(context, netType)
    }

}
