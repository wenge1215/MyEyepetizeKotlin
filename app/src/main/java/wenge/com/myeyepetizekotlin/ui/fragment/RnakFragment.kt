package wenge.com.myeyepetizekotlin.ui.fragment

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
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
import wenge.com.myeyepetizekotlin.mvp.model.bean.VideoBean
import wenge.com.myeyepetizekotlin.mvp.presenter.HotPresenter
import wenge.com.myeyepetizekotlin.ui.activity.VideoDetailActivity
import wenge.com.myeyepetizekotlin.ui.adapter.RnakRecycleAdapte
import wenge.com.myeyepetizekotlin.utils.ObjectSaveUtils
import wenge.com.myeyepetizekotlin.utils.SPUtils

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
        mAdapter = RnakRecycleAdapte(mList) {
            goToVideo(it)
        }
        rnak_recycler.layoutManager = LinearLayoutManager(context)
        rnak_recycler.adapter = mAdapter
        /**
         * RecyclerView item 滑动动画
         * 1.通过监测item滚动距离来设置动画播放时间
         * 2.获取下一个可见的item 执行动画
         */
//        rnak_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//                var layoutManager: LinearLayoutManager = recyclerView?.layoutManager as LinearLayoutManager
//                var lastPositon = layoutManager.findLastVisibleItemPosition()
//                AnkoLogger("" + lastPositon)
//
//                val childAt = recyclerView.getChildAt(lastPositon)
//                initAnimation(childAt)
//
//            }
//
//            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//            }
//        })

        mParam1?.let { HotPresenter(context, this).requestData(it) }         //请求数据
    }

    private fun goToVideo(it: HotBean.ItemListBean.DataBean) {
        var intent: Intent = Intent(context, VideoDetailActivity::class.java)
        //跳转视频详情页

        var playUrl = it?.playUrl
        var videoBean = VideoBean(it?.cover?.feed, it?.title,
                it?.description, it?.duration, playUrl,
                it?.category, it?.cover?.blurred,
                it?.consumption?.collectionCount, it?.consumption?.shareCount,
                it?.consumption?.replyCount, System.currentTimeMillis())

        var url = SPUtils.getInstance(context!!, "beans").getString(playUrl!!)
        if (url.equals("")) {
            var count = SPUtils.getInstance(context!!, "beans").getInt("count")
            if (count != -1) {
                count = count.inc()
            } else {
                count = 1
            }
            SPUtils.getInstance(context!!, "beans").put("count", count)
            SPUtils.getInstance(context!!, "beans").put(playUrl!!, playUrl)
            ObjectSaveUtils.saveObject(context!!, "bean$count", videoBean)
        }
        intent.putExtra("data", videoBean as Parcelable)
        context?.let { context -> context.startActivity(intent) }
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

    private fun initAnimation(childAt: View) {
//            // 步骤1：设置需要组合的动画效果
//            ObjectAnimator translation = ObjectAnimator.ofFloat(mButton, "translationX", curTranslationX, 300,curTranslationX);
//            // 平移动画
//             ObjectAnimator rotate = ObjectAnimator.ofFloat(mButton, "rotation", 0f, 360f);
//            // 旋转动画
//             ObjectAnimator alpha = ObjectAnimator.ofFloat(mButton, "alpha", 1f, 0f, 1f);
//            // 透明度动画
//            // 步骤2：创建组合动画的对象
//            // AnimatorSet animSet = new AnimatorSet();
//            // 步骤3：根据需求组合动画
//            animSet.play(translation).with(rotate).before(alpha);
//            animSet.setDuration(5000);
//            // 步骤4：启动动画
//             animSet.start();

        var scaleX: ObjectAnimator = ObjectAnimator.ofFloat(childAt, "scaleX", 1f, 0.8f, 1f)
        var scaleY: ObjectAnimator = ObjectAnimator.ofFloat(childAt, "scaleY", 1f, 0.8f, 1f)
        var alpha: ObjectAnimator = ObjectAnimator.ofFloat(childAt, "alpha", 1f, 0.5f, 1f)
        var aniSet: AnimatorSet = AnimatorSet()
        aniSet.play(scaleX).with(scaleY)
        aniSet.duration = 200
        aniSet.start()

    }
}
