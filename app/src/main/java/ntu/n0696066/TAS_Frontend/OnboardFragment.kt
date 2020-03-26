package ntu.n0696066.TAS_Frontend

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager


class OnboardFragment : Fragment() {

    // UI Elements
    private lateinit var mSlideViewPager : ViewPager
    private lateinit var mDotLayout : LinearLayout
    private lateinit var sliderAdapter : SliderAdapter
    private lateinit var mDots : Array<TextView>
    private lateinit var mOnboardNextButton : Button
    private lateinit var mOnboardSkipButton : Button

    lateinit var navController : NavController


    // Attributes
    private var mCurrentPage = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Variables

        // Attribute Initialization
        navController = Navigation.findNavController(view)
        mSlideViewPager = view.findViewById(R.id.slideViewPager)
        mDotLayout = view.findViewById(R.id.dotsLayout)
        mOnboardNextButton = view.findViewById(R.id.onboardNextBtn)
        mOnboardSkipButton = view.findViewById(R.id.onboardSkipBtn)
        sliderAdapter = SliderAdapter(this.requireContext())
        mSlideViewPager.adapter = sliderAdapter

        // Methods Calls
        addDotsIndicator(mCurrentPage)


        // Listeners
        mSlideViewPager.addOnPageChangeListener(SlideViewListener())
        mOnboardNextButton.setOnClickListener {
            mSlideViewPager.currentItem = mCurrentPage + 1
            if (mOnboardNextButton.text == resources.getText(R.string.Finished))
            {
                // TODO create main screen fragment
                // navController.navigate(R.id.action_greetingFragment_to_onboard)
            }
        }
        mOnboardSkipButton.setOnClickListener {
            // navController.navigate()
        }
    }

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
                }
                else ->{
                    mOnboardNextButton.text = getString(R.string.Next)
                }
            }
        }

}



}