package wenge.com.myeyepetizekotlin.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.gyf.barlibrary.ImmersionBar
import kotlinx.android.synthetic.main.activity_cache.*
import wenge.com.myeyepetizekotlin.R
import wenge.com.myeyepetizekotlin.mvp.model.bean.VideoBean
import wenge.com.myeyepetizekotlin.ui.adapter.DownLoadAdapter
import wenge.com.myeyepetizekotlin.utils.LogUtils
import wenge.com.myeyepetizekotlin.utils.ObjectSaveUtils
import wenge.com.myeyepetizekotlin.utils.SPUtils
import wenge.com.myeyepetizekotlin.utils.urlToKey

class CacheActivity : AppCompatActivity() {
    lateinit var mAdapter: DownLoadAdapter
    var datas = ArrayList<VideoBean>()

    var mHandler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            datas = msg?.data as ArrayList<VideoBean>
            mAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cache)
        init()
    }

    @SuppressLint("ResourceAsColor")
    private fun init() {
        initTitle()
        mAdapter = DownLoadAdapter(datas)
        DownLoadAsyn(applicationContext, mHandler).execute()
//        initData()
        initRV()
    }

    private fun initData() {
        var datas = ArrayList<VideoBean>()
        var urlSet = SPUtils.getInstance(this, "download").getStringSet("urlSet") as Set<String>
        Log.e("doInBackground", urlSet.toString())
        for (str in urlSet) {
            var data = ObjectSaveUtils.getValue(this, "download" + urlToKey(str)) as VideoBean
            datas.add(data)
        }
        LogUtils.aw(datas.toString())
    }

    private fun initRV() {
        rv_cach.layoutManager = LinearLayoutManager(this, 1, false)
        rv_cach.adapter = mAdapter
    }

    private fun initTitle() {
        tb_mine.setNavigationIcon(R.drawable.back)
        tb_mine.setTitle("我的缓存")
        tb_mine.setNavigationOnClickListener { finish() }
        var immersionBar = ImmersionBar.with(this)
        immersionBar?.statusBarColor(R.color.colorAccentF)?.barAlpha(0.2f)?.fitsSystemWindows(true)?.statusBarDarkFont(true)?.init()
        tb_mine.setBackgroundResource(R.color.colorAccentF)
    }

    class DownLoadAsyn(val context: Context, private val handler: Handler) : AsyncTask<Void, Void, ArrayList<VideoBean>>() {
        /**
         * 在后台执行
         * 获取下载的文件信息
         *    SPUtils.getInstance(this, "download").put("urlSet", urlSet)
         *    ObjectSaveUtils.saveObject(this, "download"+urlToKey(it1), this!!.bean!!)
         */
        override fun doInBackground(vararg params: Void?): ArrayList<VideoBean> {
            var datas = ArrayList<VideoBean>()
            var urlSet = SPUtils.getInstance(context, "download").getStringSet("urlSet") as Set<String>
            for (str in urlSet) {
                var data = ObjectSaveUtils.getValue(context, "download" + urlToKey(str)) as VideoBean
                datas.add(data)
            }
            return datas
        }

        override fun onPostExecute(result: ArrayList<VideoBean>?) {

            super.onPostExecute(result)
            LogUtils.aw(result.toString())
            var b = Bundle()
            b.putParcelableArrayList("beans", result)
            var msg = handler.obtainMessage()
            msg.data = b
            handler.sendMessage(msg)
        }
    }
}




