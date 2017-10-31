package wenge.com.myeyepetizekotlin.ui.activity

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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_video_detial.*
import org.jetbrains.anko.toast
import wenge.com.myeyepetizekotlin.R
import wenge.com.myeyepetizekotlin.mvp.model.bean.VideoBean
import wenge.com.myeyepetizekotlin.utils.*
import zlc.season.rxdownload3.RxDownload


/**
 * Created by WENGE on 2017/9/15.
 * 备注：视频播放界面
 */

class VideoDetailActivity : AppCompatActivity() {
    var bean: VideoBean? = null
    lateinit var orientationUtils: OrientationUtils
    var isPlay: Boolean = false
    var isPause: Boolean = false
    var disposable: Disposable? = null

    var download: Disposable? = null
    lateinit var mPlayUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_detial)
        bean = intent.getParcelableExtra<VideoBean>("data")
        initDownload(bean?.playUrl)
        initView()
        isSave()
        prepareVideo()
    }


    private fun isSave() {
        var play = SPUtils.getInstance(this, "download").getString("urlSet")
        if (play.contains(bean?.playUrl.toString())) {
            var file = RxDownload.file(bean?.playUrl.toString()).blockingGet()
            mPlayUrl = file.toURI().toString()
        } else {
            mPlayUrl = bean?.playUrl.toString()
        }
    }

    private fun prepareVideo() {
        gsy_player.setUp(mPlayUrl, true, null, null)
        var imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        ImageLoadUtils.display(this, bean?.feed!!, imageView)
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
                isPlay = true
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

    /**
     * 保存Set
     */
    private fun saveToSetInBack() {
        Thread(Runnable {
            Log.e("thread", Thread.currentThread().name)
            var playSet = SPUtils.getInstance(this, "beans").getStringSet("playSet", HashSet()) as HashSet
            Log.e("playSet", playSet.toString())
            if (!playSet.contains(bean!!.playUrl)) {
                bean!!.playUrl?.let { it1 ->
                    //                        playSet.add(it1)
                    var urls = HashSet<String>(playSet)
                    urls.add(it1)
                    SPUtils.getInstance(this, "beans").put("playSet", urls)
                    ObjectSaveUtils.saveObject(this, "beans" + urlToKey(it1), bean!!)
                }
            } else {
                Log.e("VideoDetial", "播放记录以存在")
            }

        }).start()
    }

    private fun initView() {
//        getCView()
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
        /**
         * 下载监听
         */
        tv_video_download.setOnClickListener {
            saveDLToStrInBack()
        }
    }

    /**
     * 保村下载路径  String
     */
    private fun saveDLToStrInBack() {
        addMission()
    }

    private fun download() {
        var play = SPUtils.getInstance(this, "download").getString("urlSet")
        if (play.contains(",")) {
            if (!play.contains(bean!!.playUrl!!)) {
                var sb = StringBuffer()
                sb.append(play).append(",").append(bean!!.playUrl)
                //保存下载记录
                SPUtils.getInstance(this, "download").put("urlSet", sb.toString())
                ObjectSaveUtils.saveObject(this, "download" + urlToKey(bean!!.playUrl!!), this!!.bean!!)
                //下载
                startLoad()
            } else {
                toast("该视频已经缓存过了")
            }
        } else {
            if (play.length == 0) {
                bean!!.playUrl?.let { SPUtils.getInstance(this, "download").put("urlSet", it) }
                ObjectSaveUtils.saveObject(this, "download" + urlToKey(bean!!.playUrl!!), this!!.bean!!)
                startLoad()
            } else {
                if (!play.equals(bean!!.playUrl)) {
                    var sb = StringBuffer()
                    sb.append(play).append(",").append(bean!!.playUrl)
                    SPUtils.getInstance(this, "download").put("urlSet", sb.toString())
                    ObjectSaveUtils.saveObject(this, "download" + urlToKey(bean!!.playUrl!!), this!!.bean!!)
                    startLoad()
                } else {
                    toast("该视频已经缓存过了")
                }
            }
        }
    }

    private fun startLoad() {
        RxDownload.start(bean!!.playUrl!!).subscribe()
    }

    private fun initDownload(playUrl: String?) {
        Log.e("initDownload", playUrl)

        download = RxDownload.create(playUrl!!)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    /**
     * 下载
     */
    private fun addMission() {
        var rxPermiss: RxPermissions = RxPermissions(this)

        rxPermiss.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe({ granted ->
                    Log.e("RxDownload", granted.toString())
                    if (granted) { // Always true pre-M
                        toast("开始下载")
                        download()
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
        download?.dispose()
    }
}
