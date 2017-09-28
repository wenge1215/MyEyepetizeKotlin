package wenge.com.myeyepetizekotlin.ui

import android.Manifest
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_video_detial.*
import org.jetbrains.anko.toast
import wenge.com.myeyepetizekotlin.R
import wenge.com.myeyepetizekotlin.mvp.model.bean.VideoBean
import wenge.com.myeyepetizekotlin.utils.ImageLoadUtils
import wenge.com.myeyepetizekotlin.utils.ObjectSaveUtils
import wenge.com.myeyepetizekotlin.utils.SPUtils
import wenge.com.myeyepetizekotlin.utils.VideoListener
import zlc.season.rxdownload2.RxDownload


/**
 * Created by WENGE on 2017/9/15.
 * 备注：视频播放界面
 */

class VideoDetailActivity : AppCompatActivity() {
    var bean: VideoBean? = null
    lateinit var orientationUtils:OrientationUtils
    var isPlay: Boolean = false
    var isPause: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_detial)
        bean = intent.getParcelableExtra<VideoBean>("data")
        initView()
        prepareVideo()
    }

    private fun prepareVideo() {
        gsy_player.setUp(bean?.playUrl, true, null, null)
        var imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        ImageLoadUtils.display(this, bean?.feed!!,imageView)
        gsy_player.setThumbImageView(imageView)     //设置封面

        gsy_player.titleTextView.visibility = View.GONE
        gsy_player.backButton.visibility = View.VISIBLE
        orientationUtils = OrientationUtils(this, gsy_player)
        gsy_player.setIsTouchWiget(true);
        //关闭自动旋转
        gsy_player.isRotateViewAuto = false;
        gsy_player.isLockLand = false;
        gsy_player.isShowFullAnimation = false;
        gsy_player.isNeedLockFull = true;

        gsy_player.fullscreenButton.setOnClickListener {
            //直接横屏
            orientationUtils.resolveByClick();
            //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
            gsy_player.startWindowFullscreen(this, true, true);
        }
        gsy_player.setStandardVideoAllCallBack(object : VideoListener() {
            override fun onPrepared(url: String?, vararg objects: Any?) {
                super.onPrepared(url, *objects)
                //开始播放了才能旋转和全屏
                orientationUtils.isEnable = true
                isPlay = true;
            }

            override fun onAutoComplete(url: String?, vararg objects: Any?) {
                super.onAutoComplete(url, *objects)

            }

            override fun onClickStartError(url: String?, vararg objects: Any?) {
                super.onClickStartError(url, *objects)
            }

            override fun onQuitFullscreen(url: String?, vararg objects: Any?) {
                super.onQuitFullscreen(url, *objects)
                orientationUtils?.let { orientationUtils.backToProtVideo() }
            }
        })
        gsy_player.setLockClickListener { view, lock ->
            //配合下方的onConfigurationChanged
            orientationUtils.isEnable = !lock
        }
        gsy_player.backButton.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })

    }

    private fun initView() {
        var bgUrl = bean?.blurred
        bgUrl?.let { ImageLoadUtils.display(this, bgUrl, iv_bottom_bg) }
        tv_video_desc.text = bean?.description
        tv_video_desc.typeface = Typeface.createFromAsset(this.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        tv_video_title.text = bean?.title
        tv_video_title.typeface = Typeface.createFromAsset(this.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
        var category = bean?.category
        var duration = bean?.duration
        var minute = duration?.div(60)
        var second = duration?.minus((minute?.times(60)) as Long)
        var realMinute: String
        var realSecond: String
        if (minute!! < 10) {
            realMinute = "0" + minute
        } else {
            realMinute = minute.toString()
        }
        if (second!! < 10) {
            realSecond = "0" + second
        } else {
            realSecond = second.toString()
        }
        tv_video_time.text = "$category / $realMinute'$realSecond''"
        tv_video_favor.text = bean?.collect.toString()
        tv_video_share.text = bean?.share.toString()
        tv_video_reply.text = bean?.share.toString()
        tv_video_download.setOnClickListener {
            //点击下载
            var url = bean?.playUrl?.let { it1 -> SPUtils.getInstance(this, "downloads").getString(it1) }
            if (url.equals("")) {
                var count = SPUtils.getInstance(this, "downloads").getInt("count")
                if (count != -1) {
                    count = count.inc()
                } else {
                    count = 1
                }
                SPUtils.getInstance(this, "downloads").put("count", count)
                ObjectSaveUtils.saveObject(this, "download$count", this!!.bean!!)
                addMission(bean?.playUrl, count)
            } else {
                toast("该视频已经缓存过了")
            }
        }
    }

    /**
     * 下载
     */
    private fun addMission(playUrl: String?, count: Int) {
        Log.e("addMission",playUrl)
        var rxPermiss: RxPermissions = RxPermissions(this)

        rxPermiss.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe({ granted ->
                    if (granted) { // Always true pre-M
                        RxDownload.getInstance(this).serviceDownload(playUrl, "download$count").subscribe({
                            toast("开始下载")
                            SPUtils.getInstance(this, "downloads").put(bean?.playUrl.toString(), bean?.playUrl.toString())
                            SPUtils.getInstance(this, "download_state").put(playUrl.toString(), true)
                        }, {
                            toast("添加任务失败")
                        })
                    } else {
                        // Oups permission denied
                        toast("没有读写权限")
                    }
                })
    }


    override fun onBackPressed() {
        orientationUtils?.let {
            orientationUtils.backToProtVideo()
        }
        if (StandardGSYVideoPlayer.backFromWindowFull(this)) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        isPause = true
    }

    override fun onResume() {
        super.onResume()
        isPause = false
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoPlayer.releaseAllVideos()
        orientationUtils?.let {
            orientationUtils.releaseListener()
        }
    }
}