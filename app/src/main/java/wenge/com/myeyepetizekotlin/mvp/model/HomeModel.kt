package wenge.com.myeyepetizekotlin.mvp.model

import android.content.Context
import io.reactivex.Observable
import wenge.com.myeyepetizekotlin.mvp.model.bean.HomeBean
import wenge.com.myeyepetizekotlin.network.ApiServer
import wenge.com.myeyepetizekotlin.network.RetrofitClient

/**
 * Created by WENGE on 2017/9/5.
 * 备注：请求网络获取数据
 */


class HomeModel {
    fun loadData(context: Context, isFirst: Boolean, data: String, num: String): Observable<HomeBean>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiServer.BASE_URL)
        var api = retrofitClient.create(ApiServer::class.java)
        when (isFirst) {
            true -> return api?.getHomeData()

            false -> return api?.getHomeMoreData(data, num)
        }
    }
}