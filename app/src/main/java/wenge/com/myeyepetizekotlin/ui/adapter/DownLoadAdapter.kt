package wenge.com.myeyepetizekotlin.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.item_download.view.*
import wenge.com.myeyepetizekotlin.R
import wenge.com.myeyepetizekotlin.mvp.model.bean.VideoBean
import wenge.com.myeyepetizekotlin.utils.ImageLoadUtils
import wenge.com.myeyepetizekotlin.utils.LogUtils
import zlc.season.rxdownload3.RxDownload
import zlc.season.rxdownload3.core.*

/**
 * Created by WENGE on 2017/10/20.
 * 备注：
 */


class DownLoadAdapter(val datas: ArrayList<VideoBean>) : RecyclerView.Adapter<DownLoadAdapter.ViewHolder>() {
    var vd: ViewHolder? = null

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        vd = holder
        holder?.setData(datas)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_download, parent, false))
    }

    override fun getItemCount(): Int = datas.size

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView){
        var isDownLoad: Boolean = true
        var currentStatus = Status()
        var datas: ArrayList<VideoBean>? = null


        fun setData(datas: ArrayList<VideoBean>) {
            LogUtils.aw("datas  " + datas.toString())
            this.datas = datas
            datas[this.adapterPosition].feed?.let { ImageLoadUtils.display(itemView.context, it, itemView.dl_iv_photo) }
            itemView.dl_tv_title.text = datas[this.adapterPosition].title
            itemView.dl_tv_detail.text = datas[this.adapterPosition].description
            itemView.iv_download_state.setImageResource(R.drawable.icon_download_stop)
            RxDownload.create(datas[this.adapterPosition].playUrl!!).observeOn(AndroidSchedulers.mainThread())
                    .subscribe { status ->
                        currentStatus = status
                        setProgress(status, itemView)
                        setActionText(status, itemView)
                    }
            setStateAction(itemView.iv_download_state)
            setItemAction(itemView)
        }

        /**
         * 条目事件
         */
        private fun setItemAction(itemView: View?) {
            /**
             * 点击事件
             */
            itemView?.setOnClickListener {
            }
        }



        private fun setStateAction(iv_download_state: ImageView) {
            iv_download_state.setOnClickListener {
                when (currentStatus) {
                    is Normal -> start()

                    is Suspend -> start()

                    is Failed -> start()

                    is Waiting -> stop()

                    is Downloading -> stop()

                }
            }
        }

        private fun setProgress(status: Status?, itemView: View) {
            if (status != null) {
                var totalS = status.totalSize.toInt()
                var loadS = status.downloadSize.toInt()
                var percent = status.percent()
                var str = status.formatString()
                var sb = StringBuffer()
                sb.append(str).append("   ").append(percent)
                itemView.pb_load.max = totalS
                itemView.pb_load.progress = loadS

                itemView.tv_load_progress.text = sb.toString()
                if (loadS == totalS && totalS > 0) {
                    itemView.pb_load.visibility = View.GONE
                }



            }
        }

        private fun setActionText(status: Status, itemView: View?) {
            var text = ""
            when (status) {
                is Normal -> {
                    text = "开始"
                    itemView?.iv_download_state?.setImageResource(R.drawable.icon_download_stop)
                }
                is Suspend -> {
                    text = "已暂停"
                    itemView?.iv_download_state?.setImageResource(R.drawable.icon_download_stop)
                }
                is Waiting -> {
                    text = "等待中"
                    itemView?.iv_download_state?.setImageResource(R.drawable.icon_download_stop)
                }
                is Downloading -> {
                    text = "暂停"
                    itemView?.iv_download_state?.setImageResource(R.drawable.icon_download_start)
                }
                is Failed -> {
                    text = "失败"
                    itemView?.iv_download_state?.setImageResource(R.drawable.icon_download_stop)
                }
                is Succeed -> {
                    text = "完成"
                    itemView?.iv_download_state?.setImageResource(R.drawable.icon_download_stop)
                }
                else -> ""
            }
            itemView?.tv_download_state?.text = text
        }

        private fun start() {
            datas?.get(this.adapterPosition)?.playUrl?.let { RxDownload.start(it).subscribe() }
        }

        private fun stop() {
            datas?.get(this.adapterPosition)?.playUrl?.let { RxDownload.stop(it).subscribe() }
        }
    }

}

