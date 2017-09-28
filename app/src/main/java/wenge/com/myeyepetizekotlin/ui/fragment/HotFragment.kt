package wenge.com.myeyepetizekotlin.ui.fragment

import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_hot.*
import wenge.com.myeyepetizekotlin.R
import wenge.com.myeyepetizekotlin.ui.adapter.HotAdapter

/**
 * Created by WENGE on 2017/9/5.
 * 备注：
 */


class HotFragment : BaseFragment() {
    val title: MutableList<String> = mutableListOf("周排行", "月排行")

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initView() {
        val childAt = tl_navi.getChildAt(0) as LinearLayout
        childAt.dividerPadding = dp2px(10)
        childAt.dividerDrawable = context.getDrawable(R.drawable.div)
        childAt.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE


        var fragmets: MutableList<Fragment> = mutableListOf(WeekFragment(), MonthFragment())

        vp_content.adapter = HotAdapter(fragmentManager, fragmets, title)

        tl_navi.setupWithViewPager(vp_content)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_hot
    }

}