package wenge.com.myeyepetizekotlin.ui.fragment

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.toast
import wenge.com.myeyepetizekotlin.R
import wenge.com.myeyepetizekotlin.mvp.contract.HomeContract
import wenge.com.myeyepetizekotlin.mvp.model.bean.HomeBean
import wenge.com.myeyepetizekotlin.mvp.presenter.HomePresenter
import wenge.com.myeyepetizekotlin.ui.adapter.HomeAdapter

/**
 * Created by WENGE on 2017/9/5.
 * 备注：
 */


class HomeFragment : BaseFragment(), HomeContract.View, SwipeRefreshLayout.OnRefreshListener {
    var mAdapter: HomeAdapter? = null
    var mList: ArrayList<HomeBean.IssueListBean.ItemListBean>? = null
    var mPresenter: HomePresenter? = null
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
        mPresenter = HomePresenter(context, this)
        mPresenter?.start()
        refresh.setOnRefreshListener(this)


    }

    /**
     * 绑定数据
     */
    override fun setData(bean: HomeBean) {
        Log.i("setData",bean.toString())
        bean.issueList!!
                .flatMap { it.itemList!! }
                .filter { it.type.equals("video") }
                .forEach { mList?.add(it) }
        Log.d("mList", mList?.get(0).toString())
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = HomeAdapter(mList) {
            toast(it.data!!.playUrl)
        }
    }

    /**
     * SwipeRefreshLayout 下拉刷新
     */
    override fun onRefresh() {

    }


}