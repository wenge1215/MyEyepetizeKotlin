package wenge.com.myeyepetizekotlin.mvp.contract

import wenge.com.myeyepetizekotlin.base.BasePresenter
import wenge.com.myeyepetizekotlin.base.BaseView
import wenge.com.myeyepetizekotlin.mvp.model.bean.HotBean

/**
 * Created by WENGE on 2017/12/27.
 * 备注：搜索结果界面的Contract
 */


interface ResultContract {
    interface ResultView : BaseView<HotBean> {
        fun setData(bean: HotBean)
    }

    interface Persenter : BasePresenter {
        fun requestData(query: String, start: Int)
    }
}