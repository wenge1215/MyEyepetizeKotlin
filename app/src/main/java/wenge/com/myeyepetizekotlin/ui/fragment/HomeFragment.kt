package wenge.com.myeyepetizekotlin.ui.fragment

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.toast
import wenge.com.myeyepetizekotlin.R
import wenge.com.myeyepetizekotlin.mvp.contract.HomeContract
import wenge.com.myeyepetizekotlin.mvp.model.bean.HomeBean
import wenge.com.myeyepetizekotlin.mvp.presenter.HomePresenter
import wenge.com.myeyepetizekotlin.ui.adapter.HomeAdapter
import java.util.regex.Pattern

/**
 * Created by WENGE on 2017/9/5.
 * 备注：
 */


class HomeFragment : BaseFragment(), HomeContract.View, SwipeRefreshLayout.OnRefreshListener {
    var mList: ArrayList<HomeBean.IssueListBean.ItemListBean> = ArrayList<HomeBean.IssueListBean.ItemListBean>()
    var mPresenter: HomePresenter? = null
    var isRefrash: Boolean = false
    lateinit var data: String
    var mAdapter: HomeAdapter? = null
    /**
     * 加载布局
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    /**
     * 初始化控件
     */
    override fun initView() {
        /**
         * 获取presenter对象
         */
        mPresenter = HomePresenter(context, this)
        mPresenter?.start()     //开启请求
        refresh.setOnRefreshListener(this)

        mAdapter = HomeAdapter(mList) {
            toast(it.data?.title.toString())
        }

        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = mAdapter



        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                var layoutManager: LinearLayoutManager = recyclerView?.layoutManager as LinearLayoutManager
                var lastPositon = layoutManager.findLastVisibleItemPosition()
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastPositon == mList.size - 1) {
                    if (data != null) {
                        mPresenter?.moreData(data)
                    }
                }
            }
        })


    }

    /**
     * 绑定数据
     */
    override fun setData(bean: HomeBean) {
        val regEx = "[^0-9]"
        val p = Pattern.compile(regEx)
        val m = p.matcher(bean?.nextPageUrl)
        Log.e("m", m.toString())
        data = m.replaceAll("").subSequence(1, m.replaceAll("").length - 1).toString()
        if (isRefrash) {
            isRefrash = false
            refresh.isRefreshing = false
            if (mList.size > 0) {
                mList.clear()
            }
        }


        Log.w("setData", bean.toString())

        /**
         * 获取ItemListBean,并添加到集合中
         */
        bean.issueList!!
                .flatMap { it.itemList!! }      //遍历bean 中的 itemList
                .filter { it.type.equals("video") }     // 过滤 itemList type  为 type
                .forEach { mList?.add(it) }         //将过滤结果添加到集合中

//        if (bean != null) {
//            if (bean.issueList != null) {
//                val issue = bean.issueList
//                if (issue != null) {
//                    for (itenBean in issue) {
//                        if ("video".equals(itenBean.type)) {
//                            mList.add(itenBean as HomeBean.IssueListBean.ItemListBean)
//                        }
//                    }
//                }
//            }
//        }

        mAdapter?.notifyDataSetChanged()
        Log.w("mList", mList.toString())
    }

    /**
     * SwipeRefreshLayout 下拉刷新
     */
    override fun onRefresh() {
        if (!isRefrash) {
            isRefrash = true
            mPresenter?.start()
        }
    }


}