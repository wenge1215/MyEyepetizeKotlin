package wenge.com.myeyepetizekotlin.ui.activity

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.gyf.barlibrary.ImmersionBar
import kotlinx.android.synthetic.main.activity_find_detial.*
import wenge.com.myeyepetizekotlin.R
import wenge.com.myeyepetizekotlin.mvp.contract.FindDetailContract
import wenge.com.myeyepetizekotlin.mvp.model.bean.HotBean
import wenge.com.myeyepetizekotlin.mvp.presenter.FindDetailPresenter
import wenge.com.myeyepetizekotlin.ui.adapter.RnakRecycleAdapte
import wenge.com.myeyepetizekotlin.utils.goToVideo

/**
 * Created by WENGE on 2017/10/13.
 * 备注：
 */


class FindDetailActivity : AppCompatActivity(), FindDetailContract.View, SwipeRefreshLayout.OnRefreshListener {
    val Tag: String = "FindDetailActivity"
    var type: String = ""
    lateinit var nextPage: String
    var mList: ArrayList<HotBean.ItemListBean.DataBean> = ArrayList()
    var mAdapter: RnakRecycleAdapte? = null
    var isRefresh: Boolean = true
    val presenter: FindDetailPresenter by lazy { FindDetailPresenter(this, this) }
    override fun onRefresh() {
        isRefresh = true
        presenter.requestData(type)
    }

    override fun setData(data: HotBean) {
        srl.isRefreshing = false

        val charSequence = data.nextPageUrl as CharSequence
        nextPage = charSequence.substring(charSequence.indexOf("=") + 1, charSequence.indexOf("&"))

        Log.w(Tag, data.nextPageUrl.toString())
        Log.w(Tag, "####################################")
        Log.w(Tag, nextPage)


        if (mList == null) {
            throw Exception("集合为空")
        }
        if (isRefresh) {
            mList.clear()
        }
        isRefresh = false

        data.itemList?.forEach {
            it.data.let { it1 ->
                mList.add(it1!!)
            }
        }
        mAdapter?.notifyDataSetChanged()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_detial)
        type = intent.getStringExtra("type")
        initToolBar()
        initAdapter()
        initRefresh()
        initData()
    }

    private fun initAdapter() {
        mAdapter = mList?.let {
            RnakRecycleAdapte(it) {
                goToVideo(this, it)
            }
        }
        recy_find_detail.layoutManager = LinearLayoutManager(this)
        recy_find_detail.adapter = mAdapter
        recy_find_detail.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val layoutManager = recy_find_detail.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition == mList.size - 1) {
                    if (nextPage != null) {
                        presenter.requestMoreData(type, nextPage)
                    }
                }
            }
        })
    }

    private fun initRefresh() {
        srl.isRefreshing = true
        srl.setOnRefreshListener(this)
    }

    private fun initData() {
        presenter.requestData(type)
    }

    private fun initToolBar() {
        val immersionBar = ImmersionBar.with(this)
        immersionBar?.statusBarColor(R.color.colorAccentT)?.barAlpha(0.2f)?.fitsSystemWindows(true)?.statusBarDarkFont(true)?.init()
        tb_find_detail.setNavigationIcon(R.drawable.back_dark)
        tb_find_detail.title = type
        tb_find_detail.setNavigationOnClickListener {
            finish()
        }
    }
}