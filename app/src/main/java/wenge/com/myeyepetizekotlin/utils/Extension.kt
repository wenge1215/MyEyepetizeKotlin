package wenge.com.myeyepetizekotlin.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import wenge.com.myeyepetizekotlin.mvp.model.bean.HotBean
import wenge.com.myeyepetizekotlin.mvp.model.bean.VideoBean
import wenge.com.myeyepetizekotlin.ui.activity.VideoDetailActivity
import java.util.regex.Pattern

/**
 * Created by WENGE on 2017/9/8.
 * 备注：
 */

inline fun <reified T : Activity> Activity.newIntent() {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
}

/**
 * Rxjava 线程调度
 */
fun <T> Observable<T>.applySchedulers(): Observable<T> {
    return subscribeOn(Schedulers.io()).
            unsubscribeOn(Schedulers.io()).
            observeOn(AndroidSchedulers.mainThread())
}

fun getNextPageUrl(pageUrl: CharSequence?): String {
    val regEx = "[^0-9]"
    val p = Pattern.compile(regEx)
    val m = p.matcher(pageUrl)
    Log.e("m", m.toString())
    return m.replaceAll("").subSequence(1, m.replaceAll("").length - 1).toString()
}

fun goToVideo(context: Context, it: HotBean.ItemListBean.DataBean) {
    var intent: Intent = Intent(context, VideoDetailActivity::class.java)
    //跳转视频详情页

    var playUrl = it?.playUrl
    var videoBean = VideoBean(it?.cover?.feed, it?.title,
            it?.description, it?.duration, playUrl,
            it?.category, it?.cover?.blurred,
            it?.consumption?.collectionCount, it?.consumption?.shareCount,
            it?.consumption?.replyCount, System.currentTimeMillis())

//    var url = SPUtils.getInstance(context!!, "beans").getString(playUrl!!)
//    if (url.equals("")) {
//        var count = SPUtils.getInstance(context!!, "beans").getInt("count")
//        if (count != -1) {
//            count = count.inc()
//        } else {
//            count = 1
//        }
//        SPUtils.getInstance(context!!, "beans").put("count", count)
//        SPUtils.getInstance(context!!, "beans").put(playUrl!!, playUrl)
//        ObjectSaveUtils.saveObject(context!!, "bean$count", videoBean)
//    }
    intent.putExtra("data", videoBean as Parcelable)
    context?.let { context -> context.startActivity(intent) }
}

fun urlToKey(url:String): String {
    return url.substring(url.indexOf("?"),url.indexOf("&"))
}
