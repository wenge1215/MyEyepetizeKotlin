package wenge.com.myeyepetizekotlin.utils

import org.jetbrains.anko.*

/**
 * Created by WENGE on 2017/9/29.
 * 备注：
 */


object LogUtils:AnkoLogger{
    fun av(str: Any?) {
        verbose(str)
    }

    fun ad(str: Any?) {
        debug(str)
    }

    fun  ai(str: Any?) {
        info(str)
    }

    fun aw(str: Any?) {
        warn(str)
    }

    fun ae(str: Any?) {
        error(str)
    }
}