package wenge.com.myeyepetizekotlin.ui.fragment

import android.app.DialogFragment
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.util.Log
import android.view.*
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.android.synthetic.main.fragment_serach.*
import org.jetbrains.anko.toast
import wenge.com.myeyepetizekotlin.R
import wenge.com.myeyepetizekotlin.serach.CircularRevealAnim
import wenge.com.myeyepetizekotlin.ui.adapter.SearchAdapter
import wenge.com.myeyepetizekotlin.utils.hideKeyBoar
import wenge.com.myeyepetizekotlin.utils.showKeyboar


/**
 * Created by WENGE on 2017/9/5.
 * 备注：
 */


class SerachFragment : DialogFragment(), CircularRevealAnim.AnimListener, ViewTreeObserver.OnPreDrawListener, View.OnClickListener {

    var mCircularRevealAnim: CircularRevealAnim? = null
    lateinit var mRootView: View
    var toggle: Int = -1

    var data : MutableList<String> = arrayListOf("脱口秀","城会玩","666","笑cry","漫威",
            "清新","匠心","VR","心理学","舞蹈","品牌广告","粉丝自制","电影相关","萝莉","魔性"
            ,"第一视角","教程","毕业设计","奥斯卡","燃","冰与火之歌","温情","线下campaign","公益")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mRootView = inflater!!.inflate(R.layout.fragment_serach, container, false)
        return mRootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setData()
    }

    override fun onStart() {
        Log.e("SeanchFragment","onStart")
        super.onStart()
        initDialog()
    }

    private fun setData() {
        recyclerView.adapter = SearchAdapter(data as ArrayList<String>) {
            toast(it.toString())
        }
        val manager = FlexboxLayoutManager()
        //设置主轴排列方式
        manager.flexDirection = FlexDirection.ROW
        //设置是否换行
        manager.flexWrap = FlexWrap.WRAP
        recyclerView.layoutManager = manager
        recyclerView.itemAnimator = DefaultItemAnimator()

    }

    private fun init() {
        mCircularRevealAnim = CircularRevealAnim()
        mCircularRevealAnim!!.setAnimListener(this)
        iv_search_search.viewTreeObserver.addOnPreDrawListener(this)            //添加视图树监听
        iv_search_back.setOnClickListener(this)
        iv_search_search.setOnClickListener(this)
    }

    private fun initDialog() {
        val window = dialog.window
        val metrics = resources.displayMetrics
        val width = (metrics.widthPixels * 0.98).toInt() //DialogSearch的宽
        val hight = (metrics.heightPixels * 0.96).toInt()//DialogSearch的高
//        window!!.setLayout(width, WindowManager.LayoutParams.MATCH_PARENT)
        window!!.setLayout(width,hight)
        window.setGravity(Gravity.TOP)
        window.setWindowAnimations(R.style.DialogEmptyAnimation)//取消过渡动画 , 使DialogSearch的出现更加平滑
    }


    /**
     * 即将绘制视图树回调方法
     */
    override fun onPreDraw(): Boolean {
        Log.e("SeanchFragment","onPreDraw")
        iv_search_search.viewTreeObserver.removeOnPreDrawListener(this);  //移除视图树监听
        mCircularRevealAnim!!.show(iv_search_search, mRootView)
        return true
    }

    fun onFinish(){
        hideKeyBoar(et_search_keyword)
        mCircularRevealAnim!!.hide(iv_search_search,mRootView)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.iv_search_back -> onFinish()

            R.id.iv_search_search -> toast("Search")
        }
    }

    override fun onHideAnimationEnd() {
        dismiss()
    }

    override fun onShowAnimationEnd() {
        showKeyboar(et_search_keyword)
    }
}