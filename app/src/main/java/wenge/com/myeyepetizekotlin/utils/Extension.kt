package wenge.com.myeyepetizekotlin.utils

import android.app.Activity
import android.content.Intent
import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.regex.Pattern

/**
 * Created by WENGE on 2017/9/8.
 * 备注：
 */

inline fun <reified T: Activity> Activity.newIntent() {
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
