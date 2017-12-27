package wenge.com.myeyepetizekotlin.ui.fragment

import android.content.Intent
import android.os.Parcelable
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import kotlinx.android.synthetic.main.fragment_home.*
import wenge.com.myeyepetizekotlin.R
import wenge.com.myeyepetizekotlin.mvp.contract.HomeContract
import wenge.com.myeyepetizekotlin.mvp.model.bean.HomeBean
import wenge.com.myeyepetizekotlin.mvp.model.bean.VideoBean
import wenge.com.myeyepetizekotlin.mvp.presenter.HomePresenter
import wenge.com.myeyepetizekotlin.ui.activity.VideoDetailActivity
import wenge.com.myeyepetizekotlin.ui.adapter.HomeAdapter
import wenge.com.myeyepetizekotlin.utils.getNextPageUrl

/**
 * Created by WENGE on 2017/9/5.
 * 备注：
 */


class HomeFragment : BaseFragment(), HomeContract.View, SwipeRefreshLayout.OnRefreshListener {
    var mList: ArrayList<HomeBean.IssueListBean.ItemListBean> = ArrayList<HomeBean.IssueListBean.ItemListBean>()
    var mPresenter: HomePresenter? = null
    var isRefrash: Boolean = false
    var data: String = ""
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

        /**
         * 创建适配器并实现RecyclerView条目点击事件
         */
        mAdapter = HomeAdapter(mList) {
            //跳转到Video详情界面
            goToVideoDetail(it.data)
        }

        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = mAdapter


        /**
         * 监听RecyclerView滚动，加载更多
         */
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
     * 跳转到Video详情界面
     */
    private fun goToVideoDetail(data: HomeBean.IssueListBean.ItemListBean.DataBean?) {
        var intent: Intent = Intent(context, VideoDetailActivity::class.java)
        //跳转视频详情页
//        var title = data?.title
//        var photo = data?.cover?.feed
//        var desc = data?.description
//        var duration = data?.duration
        var playUrl = data?.playUrl
//        var category = data?.category
//        var blurred = data?.cover?.blurred
//        var collect = data?.consumption?.collectionCount
//        var share = data?.consumption?.shareCount
//        var reply = data?.consumption?.replyCount
//        var time = System.currentTimeMillis()
        var videoBean = VideoBean(data?.cover?.feed, data?.title,
                data?.description, data?.duration, playUrl,
                data?.category, data?.cover?.blurred,
                data?.consumption?.collectionCount, data?.consumption?.shareCount,
                data?.consumption?.replyCount, System.currentTimeMillis())

//        var url = SPUtils.getInstance(context!!, "beans").getString(playUrl!!)
//        if (url.equals("")) {
//            var count = SPUtils.getInstance(context!!, "beans").getInt("count")
//            if (count != -1) {
//                count = count.inc()
//            } else {
//                count = 1
//            }
//            SPUtils.getInstance(context!!, "beans").put("count", count)
//            SPUtils.getInstance(context!!, "beans").put(playUrl!!, playUrl)
//            ObjectSaveUtils.saveObject(context!!, "bean$count", videoBean)
//        }
        intent.putExtra("data", videoBean as Parcelable)
        context?.let { context -> context.startActivity(intent) }
    }

    /**
     * 绑定数据
     */
    override fun setData(bean: HomeBean) {

        data = getNextPageUrl(bean.nextPageUrl as CharSequence?)
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