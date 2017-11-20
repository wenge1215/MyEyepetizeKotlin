package wenge.com.myeyepetizekotlin.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.gyf.barlibrary.ImmersionBar
import com.roughike.bottombar.OnTabSelectListener
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import wenge.com.myeyepetizekotlin.R
import wenge.com.myeyepetizekotlin.ui.fragment.*
import java.util.*

const val SEARCH_TAG = "SearchFragment"

class MainActivity : AppCompatActivity(), View.OnClickListener, OnTabSelectListener {
    var homeFragment: HomeFragment? = null
    var findFragment: FindFragment? = null
    var hotFragment: HotFragment? = null
    var mineFragment: MineFragment? = null
    var immersionBar: ImmersionBar? = null

    lateinit var searchFragment: SerachFragment   //lateinit 延时加载
    override fun onClick(p0: View?) {
        searchFragment = SerachFragment()
        searchFragment.show(fragmentManager, SEARCH_TAG)
    }


    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //透明导航栏
        immersionBar = ImmersionBar.with(this)
        immersionBar?.statusBarColor(R.color.colorAccent)?.barAlpha(0.2f)?.fitsSystemWindows(true)?.statusBarDarkFont(true)?.init()
        //设置导航栏按键可见
        val window = window
        val params = window.attributes
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

        initToolbar()
        initFragmetn(savedInstanceState)
        initView()
        initPremiss()
    }

    private fun initPremiss() {
        var rxPermissions: RxPermissions = RxPermissions(this)
        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe({ granted ->
                    if (granted) { // Always true pre-M
                        // I can control the camera now
                    } else {
                        toast("申请权限失败")
                    }
                })
    }

    private fun initView() {
        bottomBar.setOnTabSelectListener(this)
        bottomBar.setDefaultTab(R.id.tab_home)
    }

    private fun initFragmetn(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            val fragments: List<Fragment> = supportFragmentManager.fragments        //获取fragment
            for (item in fragments) {
                for (item in fragments) {
                    if (item is HomeFragment) {
                        homeFragment = item
                    }
                    if (item is FindFragment) {
                        findFragment = item
                    }
                    if (item is HotFragment) {
                        hotFragment = item
                    }
                    if (item is MineFragment) {
                        mineFragment = item
                    }
                }
            }
        } else {
            homeFragment = HomeFragment()
            findFragment = FindFragment()
            hotFragment = HotFragment()
            mineFragment = MineFragment()
            supportFragmentManager.beginTransaction()
                    .add(R.id.contentContainer, homeFragment)
                    .add(R.id.contentContainer, findFragment)
                    .add(R.id.contentContainer, hotFragment)
                    .add(R.id.contentContainer, mineFragment)
                    .commit()
        }

    }

    private fun initToolbar() {
        val today = getToday()
        tv_toolbar_title.text = today
        //设置字体
        tv_toolbar_title.typeface = Typeface.createFromAsset(this.assets, "fonts/Lobster-1.4.otf")
        iv_toolbar_search.setOnClickListener(this)
    }

    private fun getToday(): String {
        val todayList = arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
        var data: Date = Date()
        var calendar: Calendar = Calendar.getInstance()
        calendar.time = data
        var index: Int = calendar.get(Calendar.DAY_OF_WEEK) - 1
        if (index < 0) {
            index == 0
        }
        return todayList[index]
    }


    override fun onTabSelected(tabId: Int) {
        val beginTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        when (tabId) {
            R.id.tab_home -> {
                setBackground(R.color.colorAccent)
                iv_toolbar_search.setImageResource(R.drawable.icon_search)
                beginTransaction.show(homeFragment)
                        .hide(findFragment)
                        .hide(hotFragment)
                        .hide(mineFragment)
                        .commit()
            }

            R.id.tab_find -> {
                setBackground(R.color.colorAccentT)
                iv_toolbar_search.setImageResource(R.drawable.icon_search)
                beginTransaction.show(findFragment)
                        .hide(homeFragment)
                        .hide(hotFragment)
                        .hide(mineFragment)
                        .commit()
            }

            R.id.tab_hot -> {
                setBackground(R.color.colorAccentS)
                iv_toolbar_search.setImageResource(R.drawable.icon_search)
                beginTransaction.show(hotFragment)
                        .hide(homeFragment)
                        .hide(findFragment)
                        .hide(mineFragment)
                        .commit()
            }
            R.id.tab_me -> {
                setBackground(R.color.colorAccentF)
                iv_toolbar_search.setImageResource(R.drawable.icon_setting)
                beginTransaction.show(mineFragment)
                        .hide(homeFragment)
                        .hide(findFragment)
                        .hide(hotFragment)
                        .commit()
            }
        }
    }

    public fun setBackground(@ColorRes statusBarColor: Int) {
        immersionBar?.statusBarColor(statusBarColor)?.barAlpha(0.2f)?.fitsSystemWindows(true)?.statusBarDarkFont(true)?.init()
        toolbar_search.setBackgroundResource(statusBarColor)

    }
}
