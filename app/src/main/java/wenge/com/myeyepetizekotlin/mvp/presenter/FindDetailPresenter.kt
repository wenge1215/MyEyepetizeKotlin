package wenge.com.myeyepetizekotlin.mvp.presenter

import android.content.Context
import wenge.com.myeyepetizekotlin.mvp.contract.FindDetailContract
import wenge.com.myeyepetizekotlin.mvp.model.FindDetailModel
import wenge.com.myeyepetizekotlin.mvp.model.bean.HotBean
import wenge.com.myeyepetizekotlin.utils.applySchedulers

/**
 * Created by WENGE on 2017/10/13.
 * 备注：
 */


class FindDetailPresenter(context: Context, view: FindDetailContract.View) : FindDetailContract.Presenter {
    val findModel: FindDetailModel by lazy { FindDetailModel() }
    var mFdView: FindDetailContract.View? = null
    var mContext: Context? = null

    init {
        mFdView = view
        mContext = context
    }

    override fun start() {
    }

    override fun requestData(type: String) {
        val observable = mContext?.let { findModel.loadData(it, type) }
        observable?.applySchedulers()?.subscribe { bean: HotBean ->
            mFdView?.setData(bean)
        }
    }

    fun requestMoreData(type: String, start: String) {
        val observable = mContext?.let { findModel.loadMoreData(it, type, start) }
        val subscribe = observable?.applySchedulers()?.subscribe { bean: HotBean -> mFdView?.setData(bean) }
    }

}