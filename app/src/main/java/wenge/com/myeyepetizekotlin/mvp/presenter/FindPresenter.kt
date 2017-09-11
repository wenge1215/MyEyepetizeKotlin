package wenge.com.myeyepetizekotlin.mvp.presenter

import android.content.Context
import io.reactivex.Observable
import wenge.com.myeyepetizekotlin.mvp.contract.FindContract
import wenge.com.myeyepetizekotlin.mvp.model.FindModel
import wenge.com.myeyepetizekotlin.mvp.model.bean.FindBean
import wenge.com.myeyepetizekotlin.ui.fragment.FindFragment
import wenge.com.myeyepetizekotlin.utils.applySchedulers

/**
 * Created by WENGE on 2017/9/11.
 * 备注：
 */


class FindPresenter(context: Context, view: FindFragment) : FindContract.Presenter {
    var mContext: Context? = null
    var mView: FindFragment? = null
    val mModel: FindModel by lazy {
        FindModel()
    }

    init {
        mContext = context
        mView = view

    }

    override fun start() {
        requestData()
    }

    /**
     * 请求数据，并把转换的结果传递到V层
     */
    override fun requestData() {
        val observable: Observable<MutableList<FindBean>>? = mContext?.let { mModel.loadData(mContext!!) }
        observable?.applySchedulers()?.subscribe { findBeans: MutableList<FindBean> ->
            mView?.setData(findBeans)
        }
    }

}