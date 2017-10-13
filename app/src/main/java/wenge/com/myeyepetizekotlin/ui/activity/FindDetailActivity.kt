package wenge.com.myeyepetizekotlin.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_find_detial.*
import wenge.com.myeyepetizekotlin.R
import wenge.com.myeyepetizekotlin.mvp.contract.FindDetailContract
import wenge.com.myeyepetizekotlin.mvp.model.bean.HotBean

/**
 * Created by WENGE on 2017/10/13.
 * 备注：
 */


class FindDetailActivity :AppCompatActivity(),FindDetailContract.FindDetailView {
    override fun setData(data: HotBean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var type: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_detial)
        type =  intent.getStringExtra("type")
        initToolBar()
        initData()
    }

    private fun initData() {

    }

    private fun initToolBar() {
        tb_find_detail.setNavigationIcon(R.drawable.back)
        tb_find_detail.title = type
        tb_find_detail.setNavigationOnClickListener {
            finish()
        }
    }
}