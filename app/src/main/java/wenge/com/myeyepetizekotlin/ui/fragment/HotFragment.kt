package wenge.com.myeyepetizekotlin.ui.fragment

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

    override fun initView() {
        initTablayout()
        initFragments()
    }

    private fun initFragments() {
        var weekFragment: Fragment = RnakFragment.newInstance("week")
        var monthFragmetn: Fragment = RnakFragment.newInstance("month")
        var fragmets: ArrayList<Fragment>? = ArrayList()
        fragmets?.add(weekFragment)
        fragmets?.add(monthFragmetn)
        vp_content.adapter = HotAdapter(fragmentManager, fragmets!!, title)

        tl_navi.setupWithViewPager(vp_content)
    }


    private fun initTablayout() {
        val childAt = tl_navi.getChildAt(0) as LinearLayout
        childAt.dividerPadding = dp2px(10)
        childAt.dividerDrawable = context.resources.getDrawable(R.drawable.div)
        childAt.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_hot
    }

}