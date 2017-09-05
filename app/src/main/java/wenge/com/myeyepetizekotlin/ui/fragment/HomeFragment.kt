package wenge.com.myeyepetizekotlin.ui.fragment

import android.support.v4.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_home.*
import wenge.com.myeyepetizekotlin.R
import wenge.com.myeyepetizekotlin.mvp.contract.HomeContract
import wenge.com.myeyepetizekotlin.mvp.model.bean.HomeBean

/**
 * Created by WENGE on 2017/9/5.
 * 备注：
 */


class HomeFragment: BaseFragment(),HomeContract.View, SwipeRefreshLayout.OnRefreshListener {

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
        refresh.setOnRefreshListener(this)

    }

    /**
     * 绑定数据
     */
    override fun setData(bean: HomeBean) {

    }

    /**
     * SwipeRefreshLayout 下拉刷新
     */
    override fun onRefresh() {

    }


}