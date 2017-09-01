package wenge.com.myeyepetizekotlin.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import wenge.com.myeyepetizekotlin.R

/**
 * Created by WENGE on 2017/9/1.
 * 备注：
 */

object ImageLoadUtils {

    fun initOption(): RequestOptions? {
        val option: RequestOptions = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_empty_picture)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.no_happy)
                .priority(Priority.HIGH)
        return option
    }


    fun display(context: Context, url: Any, iv: ImageView) {
        val option: RequestOptions = initOption() as RequestOptions

        Glide.with(iv.context)
                .setDefaultRequestOptions(option.format(DecodeFormat.PREFER_ARGB_8888))
                .load(url)
                .into(iv)

    }
}
