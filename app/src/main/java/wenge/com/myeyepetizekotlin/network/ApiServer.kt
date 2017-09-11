package wenge.com.myeyepetizekotlin.network

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import wenge.com.myeyepetizekotlin.mvp.model.bean.FindBean
import wenge.com.myeyepetizekotlin.mvp.model.bean.HomeBean

/**
 * Created by WENGE on 2017/9/8.
 * 备注：
 */

interface ApiServer {
    companion object{
        val BASE_URL : String
            get() = "http://baobab.kaiyanapp.com/api/"
    }

    //获取首页第一页数据
    @GET("v2/feed?num=2&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    fun getHomeData(): Observable<HomeBean>

    //获取首页第一页之后的数据  ?date=1499043600000&num=2
    @GET("v2/feed")
    fun getHomeMoreData(@Query("date") date :String, @Query("num") num :String) : Observable<HomeBean>

    //获取发现频道信息
    @GET("v2/categories?udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    fun getFindData() : Observable<MutableList<FindBean>>
}