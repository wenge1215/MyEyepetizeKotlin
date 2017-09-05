package wenge.com.myeyepetizekotlin.ui.fragment

import android.app.DialogFragment
import android.os.Bundle
import android.view.*
import kotlinx.android.synthetic.main.fragment_serach.*
import wenge.com.myeyepetizekotlin.R
import wenge.com.myeyepetizekotlin.serach.CircularRevealAnim


/**
 * Created by WENGE on 2017/9/5.
 * 备注：
 */


class SerachFragment : DialogFragment(), CircularRevealAnim.AnimListener, ViewTreeObserver.OnPreDrawListener{
    var mCircularRevealAnim: CircularRevealAnim? = null
    lateinit var mRootView: View
    override fun onHideAnimationEnd() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onShowAnimationEnd() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DialogStyle)
    }

    override fun onStart() {
        super.onStart()
        initDialog()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
       mRootView = inflater!!.inflate(R.layout.fragment_serach, container, false)
        return mRootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        mCircularRevealAnim = CircularRevealAnim()
        mCircularRevealAnim!!.setAnimListener(this)
    }

    private fun initDialog() {
        val window = dialog.window
        val metrics = resources.displayMetrics
        val width = (metrics.widthPixels * 0.98).toInt() //DialogSearch的宽
        window!!.setLayout(width, WindowManager.LayoutParams.MATCH_PARENT)
        window.setGravity(Gravity.TOP)
        window.setWindowAnimations(R.style.DialogEmptyAnimation)//取消过渡动画 , 使DialogSearch的出现更加平滑
    }

    override fun onPreDraw(): Boolean {
//        iv_search_search.viewTreeObserver.removeOnPreDrawListener(this);
        mCircularRevealAnim!!.show(tv_serach, mRootView);
        return true;
    }


}