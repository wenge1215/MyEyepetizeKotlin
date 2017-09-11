package wenge.com.myeyepetizekotlin.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_find.view.*
import wenge.com.myeyepetizekotlin.R
import wenge.com.myeyepetizekotlin.mvp.model.bean.FindBean
import wenge.com.myeyepetizekotlin.utils.ImageLoadUtils

/**
 * Created by WENGE on 2017/9/11.
 * 备注：
 */


class FindAdapter(val beans: MutableList<FindBean>, val itemClick: (FindBean) ->Any) : RecyclerView.Adapter<FindAdapter.FindViewHolder>() {
    override fun onBindViewHolder(holder: FindViewHolder?, position: Int) {
        holder?.bindData(beans.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FindViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_find, parent, false)
        return FindViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int = beans.size


    class FindViewHolder(itemView: View?, val itemClick: (FindBean) ->Any) : RecyclerView.ViewHolder(itemView) {
        fun bindData(findBean: FindBean) {
            with(findBean){
                itemView.tv_find_name.text = findBean.name
                ImageLoadUtils.display(itemView.context, findBean.bgPicture, itemView.iv_find_bg)
                itemView.setOnClickListener { itemClick(this!!) }
            }
        }
    }
}


