package wenge.com.myeyepetizekotlin.mvp.contract

import wenge.com.myeyepetizekotlin.base.BasePresenter
import wenge.com.myeyepetizekotlin.base.BaseView
import wenge.com.myeyepetizekotlin.mvp.model.bean.HotBean

/**
 * Created by WENGE on 2017/10/13.
 * 备注：
 */


interface FindDetailContract {
    interface FindDetailView : BaseView<HotBean> {
        fun setData(data: HotBean)
    }

    interface Presenter : BasePresenter {
        fun requestData(type: String)
    }
}