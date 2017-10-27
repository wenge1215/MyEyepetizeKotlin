package wenge.com.myeyepetizekotlin.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.widget.Toast
import com.gyf.barlibrary.ImmersionBar
import kotlinx.android.synthetic.main.activity_cache.*
import wenge.com.myeyepetizekotlin.R
import wenge.com.myeyepetizekotlin.mvp.model.bean.VideoBean
import wenge.com.myeyepetizekotlin.ui.adapter.DownLoadAdapter
import wenge.com.myeyepetizekotlin.utils.ObjectSaveUtils
import wenge.com.myeyepetizekotlin.utils.SPUtils
import wenge.com.myeyepetizekotlin.utils.urlToKey
import zlc.season.rxdownload3.RxDownload
import zlc.season.rxdownload3.core.DownloadConfig.context
import java.util.*
import kotlin.collections.ArrayList


class CacheActivity : AppCompatActivity() {

    var mAdapter: DownLoadAdapter? = null
    var datas = ArrayList<VideoBean>()

    var mHandler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            datas.addAll(msg?.data?.getParcelableArrayList<VideoBean>("beans")!!)
            mAdapter?.notifyDataSetChanged()
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
        initRV()
        DownLoadAsyn(applicationContext, mHandler).execute()
        helper.attachToRecyclerView(rv_cach)

    }

    private fun initRV() {
        mAdapter = DownLoadAdapter(datas)
        rv_cach.layoutManager = LinearLayoutManager(this)
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
            var url = SPUtils.getInstance(context, "download").getString("urlSet")
//            Log.e("url", url)
            if (url.contains(",")) {
                var urls = url.split(",")
                if (urls.size > 1) {
                    for (s in urls) {
//                        Log.e("urls", s)
                        var data: VideoBean? = ObjectSaveUtils.getValue(context, "download" + urlToKey(s)) as VideoBean
                        datas.add(data!!)
                    }
                }
            } else {
                if (url.length == 0) {
                    Toast.makeText(context, "没有下载", Toast.LENGTH_SHORT).show()
                } else {
                    var data = ObjectSaveUtils.getValue(context, "download" + urlToKey(url)) as VideoBean
                    datas.add(data)
                }
            }
            return datas
        }

        override fun onPostExecute(result: ArrayList<VideoBean>) {

            super.onPostExecute(result)
//            Log.w("urlSet", result.toString())
            var b = Bundle()
            b.putParcelableArrayList("beans", result)
            var msg = handler.obtainMessage()
            msg.data = b
            handler.sendMessage(msg)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    /**
     * 为RecycleView绑定触摸事件
     */
    var helper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            //设置拖拽方向为上下左右
            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or
                    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            //设置侧滑方向为从左到右和从右到左都可以
            val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
            //将方向参数设置进去
            return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags)
        }

        /**
         * @param recyclerView
         * @param viewHolder 拖动的ViewHolder
         * @param target 目标位置的ViewHolder
         * @return
         */
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            val fromPosition = viewHolder.adapterPosition//得到拖动ViewHolder的position
            val toPosition = target.adapterPosition//得到目标ViewHolder的position
            if (fromPosition < toPosition) {
                //分别把中间所有的item的位置重新交换
                for (i in fromPosition until toPosition) {
                    Collections.swap(datas, i, i + 1)
                }
            } else {
                for (i in fromPosition downTo toPosition + 1) {
                    Collections.swap(datas, i, i - 1)
                }
            }
            mAdapter?.notifyItemMoved(fromPosition, toPosition)
            //返回true表示执行拖动
            return true
        }

        /**
         * 删除数据
         */
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            Log.e("position", position.toString() + "")
            deletCacher(viewHolder, direction)
            datas?.removeAt(position)
//            RxDownload.delete(datas[position].playUrl!!).subscribe()
            mAdapter?.notifyItemRemoved(position)
            mAdapter?.notifyDataSetChanged()
        }

        override fun onChildDraw(c: Canvas?, recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            if (actionState === ItemTouchHelper.ACTION_STATE_SWIPE) {
                if (dX > dY) {
                    //滑动时改变Item的透明度
                    val alpha = 1 - Math.abs(dX) / viewHolder?.itemView?.width as Int
                    viewHolder?.itemView?.alpha = alpha
                    viewHolder?.itemView?.translationX = dX
                }
            }
        }
    })


    /**
     * 删除下载记录
     */
    fun deletCacher(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val playUrl = datas[viewHolder.adapterPosition]?.playUrl!!
        RxDownload.delete(playUrl).subscribe()       //删除下载记录

        ObjectSaveUtils.deleteFile(urlToKey(playUrl), this)  //删除下载对象

        /**
         * 删除下载地址
         */
        var urls = SPUtils.getInstance(context, "download").getString("urlSet")
        var sb = StringBuffer()
        if (urls.contains(",")) {       //  >= 两个
            var url = urls.split(",") as ArrayList
            Log.e("deletCacher---url", url.toString())

            for (i in url.indices) {
                if (i < url.size) {
                    Log.e("url.indices", "" + i)
                    if (url[i].equals(playUrl)) {
                        url.removeAt(i)
                    }
                }
            }
            for (i in url.indices) {
                if (i < url.size) {
                    if (i == url.size - 1) {
                        sb.append(url[i])
                    } else {
                        sb.append(url[i]).append(",")
                    }
                }
            }
            urls = sb.toString()
        } else {
            if (urls.length == 0) {     //没有
                Toast.makeText(context, "没有下载", Toast.LENGTH_SHORT).show()
            } else {                    //一个
                urls = ""
            }
        }
        Log.e("urls.toString",urls.toString())
        SPUtils.getInstance(context, "download").put("urlSet", urls)
    }
}




