package wenge.com.myeyepetizekotlin.mvp.model

import android.content.Context
import io.reactivex.Observable
import wenge.com.myeyepetizekotlin.mvp.model.bean.HotBean
import wenge.com.myeyepetizekotlin.network.ApiServer
import wenge.com.myeyepetizekotlin.network.RetrofitClient

/**
 * Created by WENGE on 2017/9/29.
 * 备注：
 */


class HotModel() {
    fun getData(context: Context,strategy: String): Observable<HotBean>? {
        val instance = RetrofitClient.getInstance(context, ApiServer.BASE_URL)
        val create = instance.create(ApiServer::class.java)
        return  create?.getHotData(10, strategy,"26868b32e808498db32fd51fb422d00175e179df",83)
    }

}