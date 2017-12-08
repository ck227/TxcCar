package com.ck.txccar

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import com.ck.util.GuideAdapter
import kotlinx.android.synthetic.main.activity_guide.*
import java.util.ArrayList

/**
 * Created by cnbs5 on 2017/12/8.
 */

class GuideActivity : BaseActivity() {

//    private var viewPager: ViewPager? = null
    private var views: MutableList<View>? = null
    private var adapter: GuideAdapter? = null
    private val pics = intArrayOf(R.layout.guide_one, R.layout.guide_two, R.layout.guide_one, R.layout.guide_two)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)

        views = ArrayList<View>()
        val inflater = layoutInflater
        for (i in pics.indices) {
            views!!.add(inflater.inflate(pics[i], null))
        }
        adapter = GuideAdapter(views)
        viewPager!!.setAdapter(adapter)
        views!!.get(3).setOnClickListener {
            val i = Intent(this@GuideActivity, LoginActivity::class.java)
            startActivity(i)
            finish()
        }

        viewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    dot1!!.setImageResource(R.drawable.shape_dot_on)
                    dot2!!.setImageResource(R.drawable.shape_dot_off)
                    dot3!!.setImageResource(R.drawable.shape_dot_off)
                    dot4!!.setImageResource(R.drawable.shape_dot_off)
                } else if (position == 1) {
                    dot1!!.setImageResource(R.drawable.shape_dot_off)
                    dot2!!.setImageResource(R.drawable.shape_dot_on)
                    dot3!!.setImageResource(R.drawable.shape_dot_off)
                    dot4!!.setImageResource(R.drawable.shape_dot_off)
                } else if (position == 2)  {
                    dot1!!.setImageResource(R.drawable.shape_dot_off)
                    dot2!!.setImageResource(R.drawable.shape_dot_off)
                    dot3!!.setImageResource(R.drawable.shape_dot_on)
                    dot4!!.setImageResource(R.drawable.shape_dot_off)
                }else {
                    dot1!!.setImageResource(R.drawable.shape_dot_off)
                    dot2!!.setImageResource(R.drawable.shape_dot_off)
                    dot3!!.setImageResource(R.drawable.shape_dot_off)
                    dot4!!.setImageResource(R.drawable.shape_dot_on)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }
}