package wenge.com.myeyepetizekotlin.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gyf.barlibrary.ImmersionBar
import kotlinx.android.synthetic.main.activity_result.*
import wenge.com.myeyepetizekotlin.R

class ResultActivity : AppCompatActivity() {
    lateinit var key: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        key = intent.getStringExtra("key")
        initToolBar()
    }

    @SuppressLint("ResourceAsColor")
    private fun initToolBar() {
        //透明导航栏
        var immersionBar = ImmersionBar.with(this)
        immersionBar?.statusBarColor(R.color.colorAccent)?.barAlpha(0.2f)?.fitsSystemWindows(true)?.statusBarDarkFont(true)?.init()
        tb_result.title = StringBuilder().append("'").append(key).append("'").append(" 相关").toString()
        tb_result.setNavigationIcon(R.drawable.back_dark)
        tb_result.setBackgroundColor(R.color.colorAccent)
        tb_result.setNavigationOnClickListener { finish() }

    }
}
