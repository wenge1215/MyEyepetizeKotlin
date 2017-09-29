package wenge.com.myeyepetizekotlin.utils

/**
 * Created by WENGE on 2017/9/29.
 * 备注：
 */


object  TimeUtils{
   fun LongToTime(long: Long): String {
        var minute = long.div(60)
        var second = long.minus((minute?.times(60)) as Long)
        var realMinute: String
        var realSecond: String
        if (minute!! < 10) {
            realMinute = "0" + minute
        } else {
            realMinute = minute.toString()
        }
        if (second!! < 10) {
            realSecond = "0" + second
        } else {
            realSecond = second.toString()
        }
        return "$realMinute:$realSecond"
    }
}