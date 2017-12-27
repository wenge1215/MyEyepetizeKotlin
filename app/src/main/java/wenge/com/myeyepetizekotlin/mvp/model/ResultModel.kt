package wenge.com.myeyepetizekotlin.mvp.model

import android.content.Context
import io.reactivex.Observable
import wenge.com.myeyepetizekotlin.mvp.model.bean.HotBean
import wenge.com.myeyepetizekotlin.network.ApiServer
import wenge.com.myeyepetizekotlin.network.RetrofitClient.Companion.getInstance


/**
 * Created by WENGE on 2017/12/27.
 * 备注：搜索结果的model
 * 请求网络，获取搜索结果
 */


class ResultModel() {
    fun getData(context: Context,query:String,start:Int): Observable<HotBean>? {
        //1,获取Retrofit对象
       var instance  = getInstance(context,ApiServer.BASE_URL)
        return instance.create(ApiServer::class.java)?.getSearchData(10,query,start)
    }
}