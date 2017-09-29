package wenge.com.myeyepetizekotlin.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter


/**
 * Created by WENGE on 2017/9/28.
 * 备注：
 */


class HotAdapter(fm: FragmentManager?,val arrayList: ArrayList<Fragment>,val titleList: MutableList<String> =mutableListOf("周排行", "月排行")): FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment=arrayList[position]

    override fun getCount(): Int = arrayList.size

    override fun getPageTitle(position: Int): CharSequence= titleList[position]
}