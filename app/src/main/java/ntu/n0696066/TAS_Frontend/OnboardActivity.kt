package ntu.n0696066.TAS_Frontend

import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import kotlin.properties.Delegates


class OnboardActivity : AppCompatActivity() {

    private lateinit var mSlideViewPager : ViewPager
    private lateinit var mDotLayout : LinearLayout
    private lateinit var sliderAdapter : SliderAdapter
    private lateinit var mDots : Array<TextView>
    private lateinit var mOnboardNextButton : Button
    private var mCurrentPage : Int by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboard)

        setupObjects()
        addDotsIndicator(0)
        mSlideViewPager.addOnPageChangeListener(SlideViewListener())
        setupListeners()
    }

    private fun setupObjects() {
        mSlideViewPager = findViewById(R.id.slideViewPager)
        mDotLayout = findViewById(R.id.dotsLayout)
        mOnboardNextButton = findViewById(R.id.onboardNextBtn)
        sliderAdapter = SliderAdapter(this)
        mSlideViewPager.adapter = sliderAdapter
    }

    private fun setupListeners() {
        mOnboardNextButton.setOnClickListener {
            mSlideViewPager.currentItem = mCurrentPage + 1
        }
    }

    fun addDotsIndicator(position : Int) {
        mDots = Array(sliderAdapter.count) { TextView(this) }
        mDotLayout.removeAllViews()

        for (i in mDots.indices) {
            mDots[i] = TextView(this)
            mDots[i].text = Html.fromHtml("&#8226;", 0)
            mDots[i].textSize = 35F
            mDots[i].setTextColor(getColor(R.color.colorWhite))

            mDotLayout.addView(mDots[i])

        }
        if(mDots.isNotEmpty()) {
            mDots[position].setTextColor(getColor(R.color.colorAccent))
        }
    }

    inner class SlideViewListener : ViewPager.OnPageChangeListener {

        override fun onPageScrollStateChanged(state: Int) {
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            addDotsIndicator(position)
            mCurrentPage = position

            when (position) {
                0 -> mOnboardNextButton.isEnabled = true
                mDots.size - 1 -> mOnboardNextButton.text = getString(R.string.Finished)
                else -> mOnboardNextButton.text = getString(R.string.Next)
            }
        }

    }
}