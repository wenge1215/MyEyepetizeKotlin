package wenge.com.myeyepetizekotlin.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_feed_result.view.*
import wenge.com.myeyepetizekotlin.R
import wenge.com.myeyepetizekotlin.mvp.model.bean.HotBean
import wenge.com.myeyepetizekotlin.utils.ImageLoadUtils
import wenge.com.myeyepetizekotlin.utils.TimeUtils

/**
 * Created by WENGE on 2017/12/27.
 * 备注：
 */


class ResultAdapter(val list: MutableList<HotBean.ItemListBean.DataBean>,val itemClick: (HotBean.ItemListBean.DataBean) -> Any) : RecyclerView.Adapter<ResultAdapter.ResultHolder>() {

    override fun onBindViewHolder(holder: ResultHolder?, position: Int) {
        holder?.setData(list.get(position))
    }

    override fun getItemCount(): Int = list.size
//    {
//        return if (list != null && list.size > 0) list.size else 0
//    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ResultHolder? {
        var itemView: View = LayoutInflater.from(parent?.context).inflate(R.layout.item_feed_result, null)
        return ResultHolder(itemView, itemClick)
    }

    /**
     * ViewHolder
     */
    class ResultHolder(itemView: View?, val itemClick: (HotBean.ItemListBean.DataBean) -> Any) : RecyclerView.ViewHolder(itemView) {
        fun setData(data: HotBean.ItemListBean.DataBean) {

            /**
             * 加载封面图片
             */
            data.cover?.feed.let { ImageLoadUtils.display(itemView.context, it!!, itemView.iv_photo) }

            /**
             * 加载标题
             */
            data?.title.let { it -> itemView.tv_title.text = it }

            /**
             * 设置时间
             */
            TimeUtils.LongToTime(data.duration).let { it -> itemView.tv_detail.text = it }

            with(data) {
                itemView.setOnClickListener { itemClick(this!!) }
            }
        }
    }
}




