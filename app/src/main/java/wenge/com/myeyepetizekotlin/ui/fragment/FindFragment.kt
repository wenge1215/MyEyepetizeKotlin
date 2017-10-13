package wenge.com.myeyepetizekotlin.ui.fragment

import android.content.Intent
import android.graphics.Canvas
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import kotlinx.android.synthetic.main.fragment_find.*
import wenge.com.myeyepetizekotlin.R
import wenge.com.myeyepetizekotlin.mvp.contract.FindContract
import wenge.com.myeyepetizekotlin.mvp.model.bean.FindBean
import wenge.com.myeyepetizekotlin.mvp.presenter.FindPresenter
import wenge.com.myeyepetizekotlin.ui.activity.FindDetailActivity
import wenge.com.myeyepetizekotlin.ui.adapter.FindAdapter
import java.util.*

/**
 * Created by WENGE on 2017/9/5.
 * 备注：
 */


class FindFragment : BaseFragment(), FindContract.View {
    var presenter: FindPresenter? = null
    var datas: MutableList<FindBean>? = null
    var mAdapter: FindAdapter? = null


    override fun getLayoutId(): Int {
        return R.layout.fragment_find
    }

    /**
     * 为RecycleView绑定触摸事件
     */
   val helper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
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
                for (i in fromPosition..toPosition - 1) {
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

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            Log.e("position", position.toString() + "")
            Log.e("mList", datas?.size.toString())
            datas?.removeAt(position)
            mAdapter?.notifyItemRemoved(position)

            mAdapter?.notifyDataSetChanged()
        }

        override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                //滑动时改变Item的透明度
                val alpha = 1 - Math.abs(dX) / viewHolder.itemView.width.toFloat()
                viewHolder.itemView.alpha = alpha
                viewHolder.itemView.translationX = dX
            }
        }
    })

    override fun initView() {
        presenter = FindPresenter(context, this)
        presenter?.start()
        recycler_find.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recycler_find.itemAnimator = DefaultItemAnimator()
        helper.attachToRecyclerView(recycler_find);

    }

    override fun setData(findBeans: MutableList<FindBean>) {
        datas = findBeans
        mAdapter = FindAdapter(datas!!) {
            activity.startActivity(Intent(context, FindDetailActivity::class.java).putExtra("type", it.name))
        }
        recycler_find.adapter = mAdapter

    }
}