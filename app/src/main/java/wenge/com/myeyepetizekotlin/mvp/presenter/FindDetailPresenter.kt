package wenge.com.myeyepetizekotlin.mvp.presenter

import android.content.Context
import wenge.com.myeyepetizekotlin.mvp.contract.FindDetailContract
import wenge.com.myeyepetizekotlin.mvp.model.FindDetailModel
import wenge.com.myeyepetizekotlin.mvp.model.FindModel

/**
 * Created by WENGE on 2017/10/13.
 * 备注：
 */


class FindDetailPresenter(val context: Context) : FindDetailContract.Presenter {
    val findModel: FindDetailModel by lazy { FindDetailModel() }
    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun requestData(type: String) {
        findModel.loadData(context, type)
    }

}