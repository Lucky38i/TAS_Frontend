package ntu.n0696066.TAS_Frontend

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager


class OnboardFragment : Fragment() {

    // UI Elements
    private lateinit var mSlideViewPager : ViewPager
    private lateinit var mDotLayout : LinearLayout
    private lateinit var sliderAdapter : SliderAdapter
    private lateinit var mDots : Array<TextView>
    private lateinit var mOnboardNextButton : Button
    private lateinit var buttonAnimation : AnimationDrawable


    // Normal Attributes
    private var mCurrentPage = 0

    fun addDotsIndicator(position : Int) {
        mDots = Array(sliderAdapter.count) { TextView(this.requireContext()) }
        mDotLayout.removeAllViews()

        for (i in mDots.indices) {
            mDots[i] = TextView(this.requireContext())
            mDots[i].text = Html.fromHtml("&#8226;", 0)
            mDots[i].textSize = 35F
            mDots[i].setTextColor(resources.getColor( android.R.color.white, null))

            mDotLayout.addView(mDots[i])

        }
        if(mDots.isNotEmpty()) {
            mDots[position].setTextColor(resources.getColor( R.color.colorAccent, null))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Attribute Initialization
        mSlideViewPager = view.findViewById(R.id.slideViewPager)
        mDotLayout = view.findViewById(R.id.dotsLayout)
        mOnboardNextButton = view.findViewById(R.id.onboardNextBtn)
        mOnboardNextButton.apply {
            setBackgroundResource(R.drawable.button_bg_change)
            // TODO make a more fluid animation
            buttonAnimation = background as AnimationDrawable
        }
        sliderAdapter = SliderAdapter(this.requireContext())
        mSlideViewPager.adapter = sliderAdapter

        // Methods Calls
        addDotsIndicator(mCurrentPage)

        // Listeners
        mSlideViewPager.addOnPageChangeListener(SlideViewListener())
        mOnboardNextButton.setOnClickListener {
            mSlideViewPager.currentItem = mCurrentPage + 1
        }
    }

        inner class SlideViewListener : ViewPager.OnPageChangeListener {

        override fun onPageScrollStateChanged(state: Int) {}

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {}

        override fun onPageSelected(position: Int) {
            addDotsIndicator(position)
            mCurrentPage = position

            when (position) {
                0 -> mOnboardNextButton.isEnabled = true
                mDots.size - 1 -> {
                    mOnboardNextButton.text = resources.getString(R.string.Finished)
                    mOnboardNextButton.setTextColor(resources.getColor(android.R.color.white,
                        null))
                    buttonAnimation.start()

                }
                else ->{
                    mOnboardNextButton.text = getString(R.string.Next)
                    mOnboardNextButton.setTextColor(resources.getColor(R.color.colorPrimary,
                        null))
                }
            }
        }

    }



}