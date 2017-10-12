package wenge.com.myeyepetizekotlin.ui.adapter

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.adapter_rnak_item.view.*
import wenge.com.myeyepetizekotlin.R
import wenge.com.myeyepetizekotlin.mvp.model.bean.HotBean
import wenge.com.myeyepetizekotlin.utils.ImageLoadUtils
import wenge.com.myeyepetizekotlin.utils.TimeUtils

/**
 * Created by WENGE on 2017/9/29.
 * 备注：
 */


class RnakRecycleAdapte(val list: ArrayList<HotBean.ItemListBean.DataBean>, val itemClick: (HotBean.ItemListBean.DataBean) -> Any) :
        RecyclerView.Adapter<RnakRecycleAdapte.ViewHolder>() {
    override fun getItemCount(): Int = list?.size!!

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.adapter_rnak_item, parent, false)
        return ViewHolder(view, itemClick)

    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindData(list, position)
    }


    class ViewHolder(itemView: View?, val itemClick: (HotBean.ItemListBean.DataBean) -> Any) : RecyclerView.ViewHolder(itemView) {

        fun bindData(list: ArrayList<HotBean.ItemListBean.DataBean>, position: Int) {
            val dataBean = list?.get(position)
            ImageLoadUtils.display(itemView.context, dataBean.cover?.feed!!, itemView.iv_photo)
            itemView.tv_title.text = dataBean.title
            itemView.tv_time.text = "${dataBean.type}/${TimeUtils.LongToTime(dataBean.duration)}"
            itemView.tv_description.text = dataBean.description
            with(dataBean) {
                itemView.setOnClickListener {
                    initAnimation(dataBean)

                }
            }

        }

        private fun initAnimation(dataBean: HotBean.ItemListBean.DataBean) {
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

            var scaleX: ObjectAnimator = ObjectAnimator.ofFloat(itemView, "scaleX", 1f, 0.8f, 1f)
            var scaleY: ObjectAnimator = ObjectAnimator.ofFloat(itemView, "scaleY", 1f, 0.8f, 1f)
            var alpha: ObjectAnimator = ObjectAnimator.ofFloat(itemView, "alpha", 1f, 0.5f, 1f)
            var aniSet: AnimatorSet = AnimatorSet()
            aniSet.play(scaleX).with(scaleY).with(alpha)
            aniSet.duration = 200
            aniSet.start()
            aniSet.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(p0: Animator?) {

                }

                override fun onAnimationCancel(p0: Animator?) {

                }

                override fun onAnimationStart(p0: Animator?) {

                }

                override fun onAnimationEnd(p0: Animator?) {
                    itemClick(dataBean)
                }


            })

        }
    }
}