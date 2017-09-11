package wenge.com.myeyepetizekotlin.mvp.contract

import wenge.com.myeyepetizekotlin.base.BasePresenter
import wenge.com.myeyepetizekotlin.base.BaseView
import wenge.com.myeyepetizekotlin.mvp.model.bean.FindBean

/**
 * Created by WENGE on 2017/9/11.
 * 备注：传递数据的通道
 */


interface FindContract {
    interface View : BaseView<Presenter> {
        fun setData(findBeans: MutableList<FindBean>)
    }

    interface Presenter : BasePresenter {
        fun requestData()
    }
}