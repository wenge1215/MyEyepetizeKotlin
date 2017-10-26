package wenge.com.myeyepetizekotlin.ui.fragment

import android.content.Intent
import android.graphics.Typeface
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.fragment_mine.*
import wenge.com.myeyepetizekotlin.R
import wenge.com.myeyepetizekotlin.ui.activity.CacheActivity
import wenge.com.myeyepetizekotlin.ui.activity.TestActivity

/**
 * Created by WENGE on 2017/9/5.
 * 备注：
 */


class MineFragment : BaseFragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_save ->
                startActivity(Intent(context, CacheActivity::class.java))
            R.id.tv_watch -> Log.e("Tag", "tv_watch")
            R.id.tv_advise ->
                startActivity(Intent(context, TestActivity::class.java))

        }
    }

    override fun initView() {
        tv_save.setOnClickListener(this)
        tv_advise.setOnClickListener(this)
        tv_watch.setOnClickListener(this)
        tv_advise.typeface = Typeface.createFromAsset(context?.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        tv_watch.typeface = Typeface.createFromAsset(context?.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        tv_save.typeface = Typeface.createFromAsset(context?.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

}