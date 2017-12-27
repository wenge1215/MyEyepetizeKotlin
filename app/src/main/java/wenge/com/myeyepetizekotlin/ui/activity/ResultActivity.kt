package wenge.com.myeyepetizekotlin.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.gyf.barlibrary.ImmersionBar
import kotlinx.android.synthetic.main.activity_result.*
import wenge.com.myeyepetizekotlin.R
import wenge.com.myeyepetizekotlin.mvp.contract.ResultContract
import wenge.com.myeyepetizekotlin.mvp.model.bean.HotBean
import wenge.com.myeyepetizekotlin.mvp.model.bean.VideoBean
import wenge.com.myeyepetizekotlin.mvp.presenter.ResultPresenter
import wenge.com.myeyepetizekotlin.ui.adapter.ResultAdapter
import zlc.season.rxdownload3.core.DownloadConfig.context

class ResultActivity : AppCompatActivity(), ResultContract.ResultView, SwipeRefreshLayout.OnRefreshListener {


    lateinit var key: String
    var start: Int = 10
    var mList: ArrayList<HotBean.ItemListBean.DataBean> = ArrayList<HotBean.ItemListBean.DataBean>()
    var adapter: ResultAdapter? = null
    var mIsRefresh = false
    var mPresenter: ResultPresenter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        key = intent.getStringExtra("key")
        srl_result.setOnRefreshListener(this)
        initToolBar()
        initAdapter()
        initData()
    }

    private fun initAdapter() {
        adapter = ResultAdapter(mList) {
            //跳转到Video详情界面
            goToVideoDetail(it)
        }

        rv_result.layoutManager = LinearLayoutManager(this)
        rv_result.adapter = adapter
        /**
         * Recycler 滚动监听
         */
        rv_result.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                var layoutManager: LinearLayoutManager = recyclerView?.layoutManager as LinearLayoutManager
                var lastPositon = layoutManager.findLastVisibleItemPosition()
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastPositon == mList.size - 1) {
                    start = start.plus(10)
                    mPresenter?.requestData(key, start)
                }
            }
        })

    }


    private fun initData() {
        mPresenter = ResultPresenter(this, this)
        mPresenter?.requestData(key, start)
    }

    @SuppressLint("ResourceAsColor")
    private fun initToolBar() {
        //透明导航栏
        var immersionBar = ImmersionBar.with(this)
        immersionBar?.statusBarColor(R.color.colorAccent)?.barAlpha(0.2f)?.fitsSystemWindows(true)?.statusBarDarkFont(true)?.init()
        tb_result.title = StringBuilder().append("'").append(key).append("'").append(" 相关").toString()
        tb_result.setNavigationIcon(R.drawable.back_dark)
        tb_result.setBackgroundColor(R.color.colorAccent)
        tb_result.setNavigationOnClickListener { finish() }

    }

    override fun setData(bean: HotBean) {
        if (mIsRefresh) {
            mIsRefresh = false
            srl_result.isRefreshing = false
            if (mList.size > 0) {
                mList.clear()
            }
        }
        bean.itemList?.forEach {
            it.data?.let { it1 -> mList.add(it1) }
        }
        adapter?.notifyDataSetChanged()
    }


    private fun goToVideoDetail(data: HotBean.ItemListBean.DataBean) {
        var intent: Intent = Intent(context, VideoDetailActivity::class.java)
        //跳转视频详情页

        var playUrl = data?.playUrl

        var videoBean = VideoBean(data?.cover?.feed, data?.title,
                data?.description, data?.duration, playUrl,
                data?.category, data?.cover?.blurred,
                data?.consumption?.collectionCount, data?.consumption?.shareCount,
                data?.consumption?.replyCount, System.currentTimeMillis())


        intent.putExtra("data", videoBean as Parcelable)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context?.let { context -> context.startActivity(intent) }

    }

    override fun onRefresh() {
        if (!mIsRefresh) {
            mIsRefresh = true
            start = 10
            mPresenter?.requestData(key, start)
        }
    }
}
