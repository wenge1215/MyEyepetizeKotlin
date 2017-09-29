package wenge.com.myeyepetizekotlin.mvp.contract

import wenge.com.myeyepetizekotlin.base.BasePresenter
import wenge.com.myeyepetizekotlin.base.BaseView
import wenge.com.myeyepetizekotlin.mvp.model.bean.HotBean

/**
 * Created by WENGE on 2017/9/29.
 * 备注：
 */


interface HotContract{
    interface HotView: BaseView<HotBean>{
        fun setData(hotBean: HotBean)
    }
    interface Presenter : BasePresenter{
       fun requestData(strategy:String)
    }

}