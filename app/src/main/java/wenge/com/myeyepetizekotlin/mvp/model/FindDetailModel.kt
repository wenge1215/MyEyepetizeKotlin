package wenge.com.myeyepetizekotlin.mvp.model

import android.content.Context
import io.reactivex.Observable
import wenge.com.myeyepetizekotlin.mvp.model.bean.HotBean
import wenge.com.myeyepetizekotlin.network.ApiServer
import wenge.com.myeyepetizekotlin.network.RetrofitClient

/**
 * Created by WENGE on 2017/10/13.
 * 备注：
 */


class FindDetailModel() {
    fun loadData(context: Context, type: String): Observable<HotBean>? {
        val instance = RetrofitClient.getInstance(context, ApiServer.BASE_URL)
        val create = instance.create(ApiServer::class.java)
        return create?.getfindDetail(type)
    }

    fun loadMoreData(context: Context, type: String, index: Int): Observable<HotBean>? {
        val instance = RetrofitClient.getInstance(context, ApiServer.BASE_URL)
        val create = instance.create(ApiServer::class.java)
        return create?.getMoreFindDeatil(type, index)
    }
}