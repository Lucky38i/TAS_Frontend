package ntu.n0696066.tas_frontend

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator


class OnboardFragment : Fragment() {

    // UI Elements
    private lateinit var mSlideViewPager : ViewPager
    private lateinit var sliderAdapter : SliderAdapter
    private lateinit var mOnboardNextButton : Button
    private lateinit var mOnboardSkipButton : Button
    private lateinit var mWordDotsIndicator : WormDotsIndicator

    // Attributes
    private var mCurrentPage = 0
    lateinit var navController : NavController
    lateinit var buttonToFilled : AnimatorSet
    lateinit var buttonVisibility : AnimatorSet

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
        navController = Navigation.findNavController(view)
        mSlideViewPager = view.findViewById(R.id.slideViewPager)
        mWordDotsIndicator = view.findViewById(R.id.wormDotsIndicator)
        mOnboardNextButton = view.findViewById(R.id.onboardNextBtn)
        mOnboardSkipButton = view.findViewById(R.id.onboardSkipBtn)
        sliderAdapter = SliderAdapter(this.requireContext())
        buttonToFilled = AnimatorInflater.loadAnimator(view.context,
            R.animator.button_outline_to_filled) as AnimatorSet
        buttonVisibility = AnimatorInflater.loadAnimator(view.context,
            R.animator.button_visibility_to_invisible) as AnimatorSet

        // Inner Attributes

        // Methods Calls & Sets
        mSlideViewPager.adapter = sliderAdapter
        buttonToFilled.apply { setTarget(mOnboardNextButton) }
        mWordDotsIndicator.setViewPager(mSlideViewPager)
        buttonVisibility.apply { setTarget(mOnboardSkipButton) }

        // Listeners
        buttonToFilled.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                if (mCurrentPage == sliderAdapter.count - 1 ){
                    mOnboardNextButton.text = resources.getString(R.string.txt_finished)
                } else if (mCurrentPage == sliderAdapter.count - 2) {
                    mOnboardNextButton.text = resources.getString(R.string.txt_next)
                }
            }
        })
        mSlideViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            private var ranAnim : Boolean = false

            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float,
                                        positionOffsetPixels: Int ) {}

            override fun onPageSelected(position: Int) {
                mCurrentPage = position

                when (position) {
                    sliderAdapter.count - 1 -> {
                        buttonToFilled.start()
                        buttonVisibility.start()
                        ranAnim = true
                    }
                    sliderAdapter.count - 2 -> {
                        if(ranAnim){
                            buttonToFilled.reverse()
                            buttonVisibility.reverse()
                            ranAnim = false
                        }
                    }
                    else -> { }
                }
            }
        })
        mOnboardNextButton.setOnClickListener {
            mSlideViewPager.currentItem = mCurrentPage + 1
            if ((mOnboardNextButton.text == resources.getText(R.string.txt_finished)) && (mCurrentPage == sliderAdapter.count -1))
            {
                navController.navigate(R.id.action_onboard_to_mainFragment)
            }
        }
        mOnboardSkipButton.setOnClickListener {
            navController.navigate(R.id.action_onboard_to_mainFragment)
        }
    }
}