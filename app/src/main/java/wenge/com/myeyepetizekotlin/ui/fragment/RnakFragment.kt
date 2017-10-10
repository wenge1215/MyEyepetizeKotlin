package wenge.com.myeyepetizekotlin.ui.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_rnak.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import wenge.com.myeyepetizekotlin.R
import wenge.com.myeyepetizekotlin.mvp.contract.HotContract
import wenge.com.myeyepetizekotlin.mvp.model.bean.HotBean
import wenge.com.myeyepetizekotlin.mvp.presenter.HotPresenter
import wenge.com.myeyepetizekotlin.ui.adapter.RnakRecycleAdapte

class RnakFragment : Fragment(), HotContract.HotView {
    var mList: ArrayList<HotBean.ItemListBean.DataBean> = ArrayList()
    var mAdapter: RnakRecycleAdapte? = null
    override fun setData(hotBean: HotBean) {
        if (mList.size > 0) {
            mList.clear()
        }
        hotBean.itemList?.forEach {
            it.data?.let { it1 -> mList.add(it1) }
        }
        mAdapter?.notifyDataSetChanged()


    }

    val TAG: String = "RnakFragment"

    private var mParam1: String? = null
    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            AnkoLogger(TAG).error("onCreate")

        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_rnak, container, false)
    }

    /**
     * 实现数据绑定
     */
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter = RnakRecycleAdapte(context, mList)
        rnak_recycler.layoutManager = LinearLayoutManager(context)
        rnak_recycler.adapter = mAdapter
        mParam1?.let { HotPresenter(context, this).requestData(it) }         //请求数据
    }

    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
//            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        private val ARG_PARAM1 = "strategy"
//        GET /api/v3/ranklist?num=10&strategy=historical&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83 HTTP/1.1

        fun newInstance(param1: String): RnakFragment {
            val fragment = RnakFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            fragment.arguments = args
            return fragment
        }
    }
}
