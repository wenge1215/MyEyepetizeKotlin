package wenge.com.myeyepetizekotlin.mvp.presenter

import android.content.Context
import io.reactivex.Observable
import wenge.com.myeyepetizekotlin.mvp.contract.ResultContract
import wenge.com.myeyepetizekotlin.mvp.model.ResultModel
import wenge.com.myeyepetizekotlin.mvp.model.bean.HotBean
import wenge.com.myeyepetizekotlin.utils.applySchedulers
import zlc.season.rxdownload3.core.DownloadConfig.context

/**
 * Created by WENGE on 2017/12/27.
 * 备注：
 */
class ResultPresenter(context: Context, view: ResultContract.ResultView) : ResultContract.Persenter {
    var mContext: Context? = null
    var mView: ResultContract.ResultView? = null
    val mModel: ResultModel by lazy {
        ResultModel()
    }

    init {
        mContext = context
        mView = view
    }

    override fun start() {

    }

    override fun requestData(query: String, start: Int) {
        /**
         * 通过model对象，获取数据
         */
        var obs: Observable<HotBean>? = mContext.let { mModel.getData(context, query, start) }
        obs?.applySchedulers()?.subscribe { bena: HotBean ->
            mView?.setData(bena)
        }
    }
}