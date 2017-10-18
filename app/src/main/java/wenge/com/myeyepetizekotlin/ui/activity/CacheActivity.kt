package wenge.com.myeyepetizekotlin.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_cache.*
import wenge.com.myeyepetizekotlin.R

class CacheActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cache)
        initView()
    }

    private fun initView() {
        tb_mine.setNavigationIcon(R.drawable.back)
        tb_mine.setTitle("我的缓存")
        tb_mine.setNavigationOnClickListener { finish() }
    }
}
