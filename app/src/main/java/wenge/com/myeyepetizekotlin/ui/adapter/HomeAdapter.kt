package wenge.com.myeyepetizekotlin.ui.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_home.view.*
import wenge.com.myeyepetizekotlin.R
import wenge.com.myeyepetizekotlin.mvp.model.bean.HomeBean
import wenge.com.myeyepetizekotlin.utils.ImageLoadUtils

/**
 * Created by WENGE on 2017/9/8.
 * 备注：首页适配器
 */

class HomeAdapter(val list: MutableList<HomeBean.IssueListBean.ItemListBean>?, val itemClick: (HomeBean.IssueListBean.ItemListBean) -> Any) :
        RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    override fun onBindViewHolder(holder: HomeViewHolder?, position: Int) {
        Log.i("onBindViewHolder",list.toString())
        holder?.bindData(list!!.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_home, parent, false)
        return HomeViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int = list?.size ?: 0


    class HomeViewHolder(itemView: View, val itemClick: (HomeBean.IssueListBean.ItemListBean) -> Any) : RecyclerView.ViewHolder(itemView) {
        fun bindData(data: HomeBean.IssueListBean.ItemListBean) {
            var title = data?.data?.title
            var category = data?.data?.category
            var minute = data?.data?.duration?.div(60)
            var second = data?.data?.duration?.minus((minute?.times(60)) as Long)
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
            var playUrl = data?.data?.playUrl
            var photo = data?.data?.cover?.feed
            var author = data?.data?.author
            with(data) {
                ImageLoadUtils.display(itemView.context, photo as String, itemView.iv_photo)
                itemView.tv_title.text = title
                itemView.tv_detail.text = "发布于 $category / $realMinute:$realSecond"
                if (author != null) {
                    ImageLoadUtils.display(itemView.context, author.icon as String, itemView.iv_user)
                } else {
                    itemView?.iv_user?.visibility = View.GONE
                }

                itemView.setOnClickListener { itemClick(this!!) }
            }
        }
    }

}