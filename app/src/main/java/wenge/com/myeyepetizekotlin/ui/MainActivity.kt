package wenge.com.myeyepetizekotlin.ui

import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import wenge.com.myeyepetizekotlin.R
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(p0: View?) {
        Toast.makeText(this, "搜索点击事件", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()
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
}
