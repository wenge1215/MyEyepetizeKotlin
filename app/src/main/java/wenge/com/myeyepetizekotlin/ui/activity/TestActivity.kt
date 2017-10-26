package wenge.com.myeyepetizekotlin.ui.activity

import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.widget.CalendarView
import kotlinx.android.synthetic.main.activity_test.*
import org.jetbrains.anko.toast
import wenge.com.myeyepetizekotlin.R
import wenge.com.myeyepetizekotlin.utils.LogUtils
import java.text.SimpleDateFormat
import java.util.*

class TestActivity : AppCompatActivity(), CalendarView.OnDateChangeListener {
    override fun onSelectedDayChange(view: CalendarView?, year: Int, month: Int, dayOfMonth: Int) {
        view?.firstDayOfWeek     //return 1
        view?.weekDayTextAppearance  //
        LogUtils.aw("year:" + year + " moth:" + (month + 1) + " day:" + dayOfMonth + " week:" + view?.weekDayTextAppearance)
        toast("year:" + year + " moth:" + (month + 1) + " day:" + dayOfMonth)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        init()
    }

    private fun init() {
        val c: Calendar = Calendar.getInstance()
        calendar.date = System.currentTimeMillis()
//        calendar.setBackgroundResource(R.color.right_blue)
//        calendar.minDate = timeToLong(2017, 10, 20)       //设置最小日期
        calendar.minDate = System.currentTimeMillis()
        calendar.maxDate = timeToLong(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 2, c.get(Calendar.DAY_OF_MONTH))
        calendar.setOnDateChangeListener(this)
    }


    private fun timeToLong(year: Int, month: Int, dayOfMonth: Int): Long {
        var date = StringBuffer()
        date.append(year).append("-").append(month).append("-").append(dayOfMonth)
        var sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.parse(date.toString()).time
    }
    private fun saveFile(){
        Environment.getExternalStorageDirectory()
    }
}
