package wenge.com.myeyepetizekotlin.ui.adapter


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_search.view.*
import wenge.com.myeyepetizekotlin.R

/**
 * Created by WENGE on 2017/9/7.
 * 备注：
 */


class SearchAdapter(val dataLlist: ArrayList<String>,val itemClick : (String) -> Any) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    override fun getItemCount(): Int = dataLlist.size

    override fun onBindViewHolder(holder: SearchViewHolder?, position: Int) {
        holder?.bindData(dataLlist[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SearchViewHolder{
        return SearchViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_search,parent,false),itemClick)
    }

    class SearchViewHolder(itemView: View,val itemClick: (String) -> Any) : RecyclerView.ViewHolder(itemView) {
        fun bindData(s: String) {
            with(s) {
                itemView.tv_title.text = s
                itemView.setOnClickListener{itemClick(this)}
            }
        }

    }
}


