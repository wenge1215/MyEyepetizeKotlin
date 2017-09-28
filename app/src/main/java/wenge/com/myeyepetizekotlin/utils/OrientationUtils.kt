package wenge.com.myeyepetizekotlin.utils

import android.app.Activity
import android.content.pm.ActivityInfo
import android.provider.Settings
import android.view.OrientationEventListener
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer

/**
 * Created by WENGE on 2017/9/18.
 * 备注：处理屏幕旋转问题
 */


class OrientationUtils
/**
 * @param activity
 * @param gsyVideoPlayer
 */
(private val activity: Activity, private val gsyVideoPlayer: GSYBaseVideoPlayer?) {
    private var orientationEventListener: OrientationEventListener? = null

    var screenType = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    var isLand: Int = 0

    private var mClick: Boolean = false
    var isClickLand: Boolean = false
    var isClickPort: Boolean = false
    var isEnable = true
        set(enable) {
            field = enable
            if (isEnable) {
                orientationEventListener!!.enable()
            } else {
                orientationEventListener!!.disable()
            }
        }
    /**
     * 是否更新系统旋转，false的话，系统禁止旋转也会跟着旋转
     * @param rotateWithSystem 默认true
     */
    var isRotateWithSystem = true //是否跟随系统

    init {
        init()
    }

    private fun init() {
        orientationEventListener = object : OrientationEventListener(activity) {
            override fun onOrientationChanged(rotation: Int) {
                val autoRotateOn = android.provider.Settings.System.getInt(activity.contentResolver, Settings.System.ACCELEROMETER_ROTATION, 0) == 1
                if (!autoRotateOn && isRotateWithSystem) {
                    //if (mIsLand == 0) {
                    return
                    //}
                }
                // 设置竖屏
                if (rotation >= 0 && rotation <= 30 || rotation >= 330) {
                    if (mClick) {
                        if (isLand > 0 && !isClickLand) {
                            return
                        } else {
                            isClickPort = true
                            mClick = false
                            isLand = 0
                        }
                    } else {
                        if (isLand > 0) {
                            screenType = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                            if (gsyVideoPlayer!!.isIfCurrentIsFullscreen()) {
                                gsyVideoPlayer!!.getFullscreenButton().setImageResource(gsyVideoPlayer!!.getShrinkImageRes())
                            } else {
                                gsyVideoPlayer!!.getFullscreenButton().setImageResource(gsyVideoPlayer!!.getEnlargeImageRes())
                            }
                            isLand = 0
                            mClick = false
                        }
                    }
                } else if (rotation >= 230 && rotation <= 310) {
                    if (mClick) {
                        if (isLand != 1 && !isClickPort) {
                            return
                        } else {
                            isClickLand = true
                            mClick = false
                            isLand = 1
                        }
                    } else {
                        if (isLand != 1) {
                            screenType = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                            gsyVideoPlayer!!.getFullscreenButton().setImageResource(gsyVideoPlayer!!.getShrinkImageRes())
                            isLand = 1
                            mClick = false
                        }
                    }
                } else if (rotation > 30 && rotation < 95) {
                    if (mClick) {
                        if (isLand != 2 && !isClickPort) {
                            return
                        } else {
                            isClickLand = true
                            mClick = false
                            isLand = 2
                        }
                    } else if (isLand != 2) {
                        screenType = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
                        gsyVideoPlayer!!.getFullscreenButton().setImageResource(gsyVideoPlayer!!.getShrinkImageRes())
                        isLand = 2
                        mClick = false
                    }
                }// 设置反向横屏
                // 设置横屏
            }
        }
        orientationEventListener!!.enable()
    }

    /**
     * 点击切换的逻辑，比如竖屏的时候点击了就是切换到横屏不会受屏幕的影响
     */
    fun resolveByClick() {
        mClick = true
        if (isLand == 0) {
            screenType = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            gsyVideoPlayer!!.getFullscreenButton().setImageResource(gsyVideoPlayer!!.getShrinkImageRes())
            isLand = 1
            isClickLand = false
        } else {
            screenType = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            if (gsyVideoPlayer!!.isIfCurrentIsFullscreen()) {
                gsyVideoPlayer!!.getFullscreenButton().setImageResource(gsyVideoPlayer!!.getShrinkImageRes())
            } else {
                gsyVideoPlayer!!.getFullscreenButton().setImageResource(gsyVideoPlayer!!.getEnlargeImageRes())
            }
            isLand = 0
            isClickPort = false
        }

    }

    /**
     * 列表返回的样式判断。因为立即旋转会导致界面跳动的问题
     */
    fun backToProtVideo(): Int {
        if (isLand > 0) {
            mClick = true
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            if (gsyVideoPlayer != null)
                gsyVideoPlayer!!.getFullscreenButton().setImageResource(gsyVideoPlayer!!.getEnlargeImageRes())
            isLand = 0
            isClickPort = false
            return 500
        }
        return 0
    }

    fun releaseListener() {
        if (orientationEventListener != null) {
            orientationEventListener!!.disable()
        }
    }

    var isClick: Boolean
        get() = mClick
        set(Click) {
            this.mClick = mClick
        }
}
