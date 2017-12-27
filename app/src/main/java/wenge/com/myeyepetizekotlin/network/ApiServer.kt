package wenge.com.myeyepetizekotlin.network

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import wenge.com.myeyepetizekotlin.mvp.model.bean.FindBean
import wenge.com.myeyepetizekotlin.mvp.model.bean.HomeBean
import wenge.com.myeyepetizekotlin.mvp.model.bean.HotBean

/**
 * Created by WENGE on 2017/9/8.
 * 备注：
 */

interface ApiServer {
    companion object {
        val BASE_URL: String
            get() = "http://baobab.kaiyanapp.com/api/"
    }

    //获取首页第一页数据
    @GET("v2/feed?num=2&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    fun getHomeData(): Observable<HomeBean>

    //获取首页第一页之后的数据  ?date=1499043600000&num=2
    @GET("v2/feed")
    fun getHomeMoreData(@Query("date") date: String, @Query("num") num: String): Observable<HomeBean>

    //获取发现频道信息
    @GET("v2/categories?udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    fun getFindData(): Observable<MutableList<FindBean>>

    //发现详情首页
    //v3/videos?categoryName=%E6%97%B6%E5%B0%9A&strategy=date&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83
    @GET("v3/videos?strategy=date&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    fun getfindDetail(@Query("categoryName") cn: String):Observable<HotBean>

    //发现详情页————加载更多
    //v3/videos?start=10&num=10&categoryName=%E8%BF%90%E5%8A%A8&strategy=date
    @GET("v3/videos?num=10&strategy=date")
    fun getMoreFindDeatil(@Query("categoryName") cn: String, @Query("start") index: String):Observable<HotBean>


    //获取热门排行信息
    @GET("v3/ranklist")
    fun getHotData(@Query("num") num: Int, @Query("strategy") strategy: String,
                   @Query("udid") udid: String = "26868b32e808498db32fd51fb422d00175e179df", @Query("vc") vc: Int = 83): Observable<HotBean>

    //获取关键词搜索相关信息
    @GET("v1/search")
    fun getSearchData(@Query("num") num :Int,@Query("query") query :String,
                      @Query("start") start :Int) : Observable<HotBean>

}
