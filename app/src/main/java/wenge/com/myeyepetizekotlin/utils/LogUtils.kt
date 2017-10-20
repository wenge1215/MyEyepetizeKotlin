package wenge.com.myeyepetizekotlin.utils

import org.jetbrains.anko.*

/**
 * Created by WENGE on 2017/9/29.
 * 备注：
 */


object LogUtils : AnkoLogger {
    val b: Boolean = true

    fun av(str: Any?) {
        if (b) {
            verbose(str)
        }
    }

    fun ad(str: Any?) {
        if (b) {
            debug(str)
        }
    }

    fun ai(str: Any?) {
        if (b) {
            info(str)
        }
    }

    fun aw(str: Any?) {
        if (b) {
            warn(str)
        }
    }

    fun ae(str: Any?) {
        if (b) {
            error(str)
        }
    }
}