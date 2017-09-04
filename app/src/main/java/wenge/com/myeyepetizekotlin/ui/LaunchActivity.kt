package wenge.com.myeyepetizekotlin.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.*
import kotlinx.android.synthetic.main.activity_launch.*
import wenge.com.myeyepetizekotlin.R

class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        gotoMain()
    }

    /**
     * 延时跳转
     */

    private fun gotoMain() {
        val alphaAnimation = AlphaAnimation(0.1f,1.0f)
        alphaAnimation.duration = 1000
        val scaleAnimation = ScaleAnimation(0.1f,1.0f,0.1f,1.0f, ScaleAnimation.RELATIVE_TO_SELF,1.0f, ScaleAnimation.RELATIVE_TO_SELF,0.5f)
        val  rotateAnimation = RotateAnimation(0f,360f,Animation.RELATIVE_TO_SELF,
                0.5f,Animation.RELATIVE_TO_SELF,0.5f)
        scaleAnimation.duration =1000
        val animationSet = AnimationSet(true)
        animationSet.addAnimation(alphaAnimation)
        animationSet.addAnimation(scaleAnimation)
        animationSet.addAnimation(rotateAnimation)
        animationSet.duration = 1000
        iv_anmotion.startAnimation(animationSet)
        animationSet.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                val intent = Intent(this@LaunchActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

    override fun onResume() {
        super.onResume()
    }
}
