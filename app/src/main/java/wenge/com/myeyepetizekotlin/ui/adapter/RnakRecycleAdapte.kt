package wenge.com.myeyepetizekotlin.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.adapter_rnak_item.view.*
import wenge.com.myeyepetizekotlin.R
import wenge.com.myeyepetizekotlin.mvp.model.bean.HotBean
import wenge.com.myeyepetizekotlin.utils.ImageLoadUtils
import wenge.com.myeyepetizekotlin.utils.TimeUtils

/**
 * Created by WENGE on 2017/9/29.
 * 备注：
 */


class RnakRecycleAdapte(val context: Context, var hotBean: HotBean) : RecyclerView.Adapter<RnakRecycleAdapte.ViewHolder>() {
    override fun getItemCount(): Int = hotBean.itemList?.size!!

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_rnak_item, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindData(hotBean, position)
    }


    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bindData(hotBean: HotBean, position: Int) {
            val dataBean: HotBean.ItemListBean.DataBean = hotBean.itemList?.get(position)?.data!!
            ImageLoadUtils.display(itemView.context, dataBean.cover?.feed!!, itemView.iv_photo)
            itemView.tv_title.text = dataBean.title
            itemView.tv_time.text = "${dataBean.type}/${TimeUtils.LongToTime(dataBean.duration)}"
            itemView.tv_description.text = dataBean.description
        }
    }
}