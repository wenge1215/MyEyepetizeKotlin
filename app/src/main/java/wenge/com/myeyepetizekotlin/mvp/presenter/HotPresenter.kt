package wenge.com.myeyepetizekotlin.mvp.presenter

import android.content.Context
import io.reactivex.Observable
import wenge.com.myeyepetizekotlin.mvp.contract.HotContract
import wenge.com.myeyepetizekotlin.mvp.model.HotModel
import wenge.com.myeyepetizekotlin.mvp.model.bean.HotBean
import wenge.com.myeyepetizekotlin.utils.applySchedulers

/**
 * Created by WENGE on 2017/9/29.
 * 备注：
 */

class HotPresenter(context: Context, hotView: HotContract.HotView) : HotContract.Presenter {

    private val mHotModel: HotModel by lazy { HotModel() }
    private var mHotView: HotContract.HotView? = null
    var mContext: Context? = null

    init {
        mHotView = hotView
        mContext = context
    }

    override fun start() {

    }

    override fun requestData(strategy: String) {
        var result: Observable<HotBean>? = mContext.let { mHotModel.getData(it!!, strategy) }
        result?.applySchedulers()!!.subscribe { hotBean: HotBean ->
            mHotView?.setData(hotBean)
        }
    }
}
