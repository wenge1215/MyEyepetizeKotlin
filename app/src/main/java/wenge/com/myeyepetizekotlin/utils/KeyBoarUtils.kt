package wenge.com.myeyepetizekotlin.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

@SuppressLint("ServiceCast")
        /**
 * Created by WENGE on 2017/9/8.
 * 备注：
 */


fun showKeyboar(editText: EditText){
    val inputService = editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputService.showSoftInput(editText, InputMethodManager.RESULT_SHOWN)
    inputService.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)

}

fun hideKeyBoar(editText: EditText){
    val inputService = editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (inputService.isActive) {
        inputService.hideSoftInputFromWindow(editText.windowToken,0)
    }
}