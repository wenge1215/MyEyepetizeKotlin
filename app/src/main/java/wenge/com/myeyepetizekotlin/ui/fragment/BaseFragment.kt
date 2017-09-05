package wenge.com.myeyepetizekotlin.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by WENGE on 2017/9/5.
 * 备注：
 */


abstract class BaseFragment : Fragment() {
    var rootView: View? = null
    var isFirst: Boolean = false
    var isFragmentUserVisible: Boolean = false


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = inflater?.inflate(getLayoutId(), container, false)
        }
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            isFragmentUserVisible = true
        }

        if (rootView == null) {
            return
        }
        //可见————初次加载
        if (!isFirst && isFragmentUserVisible) {
            onFragmentUserVisble(true)
            return
        }

        //可见-----不可见  已加载过
        if (!isFragmentUserVisible) {
            onFragmentUserVisble(false)
            return
        }
    }

    open protected fun onFragmentUserVisble(b: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    abstract fun initView()

    abstract fun getLayoutId(): Int


}