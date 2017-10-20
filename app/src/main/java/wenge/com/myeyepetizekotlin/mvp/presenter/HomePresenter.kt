package wenge.com.myeyepetizekotlin.mvp.presenter

import android.content.Context
import io.reactivex.Observable
import wenge.com.myeyepetizekotlin.mvp.contract.HomeContract
import wenge.com.myeyepetizekotlin.mvp.model.HomeModel
import wenge.com.myeyepetizekotlin.mvp.model.bean.HomeBean
import wenge.com.myeyepetizekotlin.utils.LogUtils
import wenge.com.myeyepetizekotlin.utils.applySchedulers

/**
 * Created by WENGE on 2017/9/5.
 * 备注：
 */

class HomePresenter(context: Context, view: HomeContract.View) : HomeContract.Presenter {
    var mContext: Context? = null
    var mView: HomeContract.View? = null
    val mModel: HomeModel by lazy {
        //延迟加载
        HomeModel()
    }

    init {
        mView = view
        mContext = context
    }

    override fun start() {
        requestData()
    }

    /**
     * 请求首页数据
     */
    override fun requestData() {
        LogUtils.aw("requestData")
        val observable: Observable<HomeBean>? = mContext?.let { mModel.loadData(it, true, "0") }
        LogUtils.aw("observable")
        observable?.applySchedulers()?.subscribe { homeBean : HomeBean ->
            LogUtils.aw("subscribe")
            mView?.setData(homeBean)
            LogUtils.aw("setData")
        }
    }

    /**
     * 加载更多数据
     *
     */
    fun moreData(data: String?) {
        val observable: Observable<HomeBean>? = mContext?.let { mModel.loadData(it, false, data!!) }
        observable?.applySchedulers()?.subscribe { homeBean: HomeBean ->
            mView?.setData(homeBean)
        }
    }


}
