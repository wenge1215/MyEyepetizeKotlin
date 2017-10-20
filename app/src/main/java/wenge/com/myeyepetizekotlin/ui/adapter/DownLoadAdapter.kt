package wenge.com.myeyepetizekotlin.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import wenge.com.myeyepetizekotlin.R
import wenge.com.myeyepetizekotlin.mvp.model.bean.VideoBean

/**
 * Created by WENGE on 2017/10/20.
 * 备注：
 */


class DownLoadAdapter(val datas: ArrayList<VideoBean>) : RecyclerView.Adapter<DownLoadAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.setData(datas,position)

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_download, parent))
    }

    override fun getItemCount(): Int = datas.size

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun setData(datas: ArrayList<VideoBean>, position: Int) {

        }
    }
}