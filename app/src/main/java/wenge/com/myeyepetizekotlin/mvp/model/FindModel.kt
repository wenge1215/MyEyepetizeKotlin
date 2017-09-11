package wenge.com.myeyepetizekotlin.mvp.model

import android.content.Context
import io.reactivex.Observable
import wenge.com.myeyepetizekotlin.mvp.model.bean.FindBean
import wenge.com.myeyepetizekotlin.network.ApiServer
import wenge.com.myeyepetizekotlin.network.RetrofitClient

/**
 * Created by WENGE on 2017/9/11.
 * 备注：获取发现页面的数据
 */


class FindModel{
    fun loadData(context: Context): Observable<MutableList<FindBean>>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiServer.BASE_URL)
        val apiServer = retrofitClient.create(ApiServer::class.java)
        return apiServer?.getFindData()
    }
}