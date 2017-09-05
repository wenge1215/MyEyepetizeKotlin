package wenge.com.myeyepetizekotlin.mvp.contract

import wenge.com.myeyepetizekotlin.base.BasePresenter
import wenge.com.myeyepetizekotlin.base.BaseView
import wenge.com.myeyepetizekotlin.mvp.model.bean.HomeBean

/**
 * Created by WENGE on 2017/9/5.
 * 备注：
 */
interface HomeContract {
    interface View : BaseView<Presenter>{
        fun setData(bean : HomeBean)
    }
    interface Presenter:BasePresenter{
        fun requestData()
    }

}






