package wenge.com.myeyepetizekotlin.base

import android.app.Application
import zlc.season.rxdownload3.core.DownloadConfig

/**
 * Created by WENGE on 2017/10/18.
 * 备注：
 */


class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val builder = DownloadConfig.Builder.create(this)
                .enableDb(true)
                .enableNotification(true)
        DownloadConfig.init(builder)
    }
}