package ntu.n0696066.tas_frontend

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainFragment : Fragment() {

    enum class LCWPosition {
        FCW,
        RCW,
        LCW_BOTTOM_RIGHT,
        LCW_BOTTOM_LEFT,
        LCW_TOP_RIGHT,
        LCW_TOP_LEFT
    }

    // UI Attributes
    private lateinit var mImgCar : ImageView
    private lateinit var mImgLeft : ImageView
    private lateinit var mImgRight : ImageView
    private lateinit var mTxtHeadway : TextView

    // Attributes
    private lateinit var navController : NavController
    private lateinit var mainPreferences : SharedPreferences
    private lateinit var imgLeftVisibility : AnimatorSet
    private lateinit var imgRightVisibility : AnimatorSet
    private var headWayDistance : Int? = null
    private var unitSelected : String? = null
    private var lcwCurrentPos : LCWPosition? = null
    private var rightArrowAnim = false
    private var leftArrowAnim = false

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Instantiation
        navController = Navigation.findNavController(view)
        mainPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        mImgCar = view.findViewById(R.id.imgCar)
        mImgLeft = view.findViewById(R.id.imgLeft)
        mImgRight = view.findViewById(R.id.imgRight)
        mTxtHeadway = view.findViewById(R.id.txtHeadway)
        imgLeftVisibility = AnimatorInflater.loadAnimator(view.context,
            R.animator.invisible_to_visible) as AnimatorSet
        imgRightVisibility = AnimatorInflater.loadAnimator(view.context,
            R.animator.invisible_to_visible) as AnimatorSet

        // Assignments
        headWayDistance = mainPreferences.getInt(getString(R.string.pref_headway_key),
            resources.getInteger(R.integer.pref_headway_default))
        unitSelected = mainPreferences.getString(getString(R.string.pref_unitsMeasure_key),
            getString(R.string.pref_unitsMeasure_default))
        imgLeftVisibility.apply { setTarget(mImgLeft) }
        imgRightVisibility.apply { setTarget(mImgRight) }

        // Assertions
        assert(headWayDistance != null)
        assert(unitSelected != null)

        // Listeners
        requireActivity().navigation_view.setNavigationItemSelectedListener {selectedItem ->
            when (selectedItem.itemId) {
                R.id.nav_fcw ->{
                    simulateFcw()
                    requireActivity().drawerLayoutMain.closeDrawers()
                    true
                }
                R.id.nav_rcw -> {true}
                R.id.nav_lcw -> {
                    simulateLCW()
                    requireActivity().drawerLayoutMain.closeDrawers()
                    true
                }
                else -> false
            }
        }
        mTxtHeadway.addTextChangedListener(object : TextWatcher {
            var beforeInt : Int? = null
            override fun afterTextChanged(s: Editable?) {
                if (mTxtHeadway.text.toString().toInt() >= 15) mTxtHeadway.alpha = 0f
                else if (mTxtHeadway.text.toString().toInt() < 15) mTxtHeadway.alpha = 1f
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                beforeInt = s.toString().toIntOrNull()
            }

            /**
             * A sort of sloppy implementation but it works, any changes to headway text and various
             * actions occur no matter where the change is made. Making it easily adaptable to when
             * V2V HMI is being used as opposed to simulations
             */
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val goingDown = if (beforeInt != null) {
                    if (s.toString().toIntOrNull() != null) s.toString().toInt() < beforeInt!!
                    else false
                } else false  // Used to tell if the monitored headway is incrementing or not

                when(lcwCurrentPos) {
                    LCWPosition.LCW_BOTTOM_LEFT -> {
                        manageAnimations(s, goingDown,
                            R.drawable.avd_lcw_bottom_left_car_start,
                            R.drawable.avd_lcw_bottom_left_start_car,
                            R.drawable.avd_lcw_bottom_left_start_med,
                            R.drawable.avd_lcw_bottom_left_med_start,
                            R.drawable.avd_lcw_bottom_left_med_end,
                            R.drawable.avd_lcw_bottom_left_end_med)
                    }
                    LCWPosition.LCW_BOTTOM_RIGHT -> {
                        manageAnimations(s, goingDown,
                            R.drawable.avd_lcw_bottom_right_car_start,
                            R.drawable.avd_lcw_bottom_right_start_car,
                            R.drawable.avd_lcw_bottom_right_start_med,
                            R.drawable.avd_lcw_bottom_right_med_start,
                            R.drawable.avd_lcw_bottom_right_med_end,
                            R.drawable.avd_lcw_bottom_right_end_med)
                    }
                    LCWPosition.LCW_TOP_LEFT -> {
                        manageAnimations(s, goingDown,
                            R.drawable.avd_lcw_top_left_car_start,
                            R.drawable.avd_lcw_top_left_start_car,
                            R.drawable.avd_lcw_top_left_start_med,
                            R.drawable.avd_lcw_top_left_med_start,
                            R.drawable.avd_lcw_top_left_med_end,
                            R.drawable.avd_lcw_top_left_end_med)
                    }
                    LCWPosition.LCW_TOP_RIGHT -> {
                        manageAnimations(s, goingDown,
                            R.drawable.avd_lcw_top_right_car_start,
                            R.drawable.avd_lcw_top_right_start_car,
                            R.drawable.avd_lcw_top_right_start_med,
                            R.drawable.avd_lcw_top_right_med_start,
                            R.drawable.avd_lcw_top_right_med_end,
                            R.drawable.avd_lcw_top_right_end_med)
                    }
                    LCWPosition.FCW -> {
                       manageAnimations(s, goingDown,
                           R.drawable.avd_fcw_car_start, R.drawable.avd_fcw_start_car,
                           R.drawable.avd_fcw_start_med, R.drawable.avd_fcw_med_start,
                           R.drawable.avd_fcw_med_end, R.drawable.avd_fcw_end_med)
                    }
                    LCWPosition.RCW -> {

                    }
                }
            }
        })
        // TODO Create listener for when settings have been changed
    }

    /**
     * Manages sounds and animation cues for various warning systems.
     */
    private fun manageAnimations(newString: CharSequence?, goingDown : Boolean,
                                 car_start : Int, start_car : Int, start_med: Int,
                                 med_start: Int, med_end: Int, end_med: Int ) {
        when {
            newString.toString().toInt() == headWayDistance!! * 4 -> {
                mTxtHeadway.setTextColor(resources.getColor(android.R.color.white,
                    null))
                if (goingDown) {
                    mImgCar.setImageResource(car_start)
                } else {
                    mImgCar.setImageResource(start_car)
                    (mImgCar.drawable as AnimatedVectorDrawable).start()
                }

            }
            newString.toString().toInt() == headWayDistance!! * 3 -> {
                mTxtHeadway.setTextColor(resources.getColor(android.R.color.holo_green_dark,
                    null))
                if (goingDown) {
                    (mImgCar.drawable as AnimatedVectorDrawable).start()
                } else {
                    mImgCar.setImageResource(med_start)
                    (mImgCar.drawable as AnimatedVectorDrawable).start()
                }
            }
            newString.toString().toInt() == headWayDistance!! * 2 -> {

                when (lcwCurrentPos) {
                    LCWPosition.LCW_BOTTOM_LEFT, LCWPosition.LCW_TOP_LEFT -> {
                        if(rightArrowAnim) imgRightVisibility.reverse()
                    }
                    LCWPosition.LCW_BOTTOM_RIGHT, LCWPosition.LCW_TOP_RIGHT -> {
                        if(leftArrowAnim) imgLeftVisibility.reverse()
                    } else -> {}
                }

                mTxtHeadway.setTextColor(resources.getColor(R.color.yellow,
                    null))
                if (goingDown) {
                    mImgCar.setImageResource(start_med)
                    (mImgCar.drawable as AnimatedVectorDrawable).start()
                } else {
                    mImgCar.setImageResource(end_med)
                    (mImgCar.drawable as AnimatedVectorDrawable).start()
                }
            }
            newString.toString().toInt() == headWayDistance!! -> {

                when (lcwCurrentPos) {
                    LCWPosition.LCW_BOTTOM_LEFT, LCWPosition.LCW_TOP_LEFT -> {
                        if (!rightArrowAnim) {
                            imgRightVisibility.start()
                            rightArrowAnim = true
                        }
                    }
                    LCWPosition.LCW_BOTTOM_RIGHT, LCWPosition.LCW_TOP_RIGHT -> {
                        if (!leftArrowAnim) {
                            imgLeftVisibility.start()
                            leftArrowAnim = true
                        }
                    } else -> {}
                }

                mTxtHeadway.setTextColor(resources.getColor(android.R.color.holo_red_dark,
                    null))
                if (goingDown) {
                    mImgCar.setImageResource(med_end)
                    (mImgCar.drawable as AnimatedVectorDrawable).start()
                }
            }
        }
    }

    /**
     * Emulates FCW
     */
    private fun simulateFcw() {
        val duration : Long = 15
        val interval : Long = 1

        val mTimerCollisionUp = object : CountDownTimer(TimeUnit.SECONDS.toMillis(duration),
            TimeUnit.SECONDS.toMillis(interval)) {
            override fun onFinish() {}

            override fun onTick(millisUntilFinished: Long) {
                val counter = duration - TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                mTxtHeadway.text = counter.toString()
            }
        }

        val mTimerCollision = object : CountDownTimer(TimeUnit.SECONDS.toMillis(duration),
            TimeUnit.SECONDS.toMillis(interval)) {

            override fun onFinish() {
                mTimerCollisionUp.start()
            }

            override fun onTick(millisUntilFinished: Long) {
                mTxtHeadway.text = (mTxtHeadway.text.toString().toInt() - 1).toString()
            }
        }
        mTxtHeadway.text = duration.toString()
        lcwCurrentPos = LCWPosition.FCW
        mTimerCollision.start()

    }
    
    /**
     * Emulates LCW from various positions
     */
    private fun simulateLCW() {
        val duration : Long = 15
        val interval : Long = 1

        val mTimerLCWTopLeftUp = object : CountDownTimer(TimeUnit.SECONDS.toMillis(duration),
            TimeUnit.SECONDS.toMillis(interval)) {
            override fun onFinish() {}

            override fun onTick(millisUntilFinished: Long) {
                val counter = duration - TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                mTxtHeadway.text = counter.toString()
            }
        }

        val mTimerLCWTopLeft = object : CountDownTimer(TimeUnit.SECONDS.toMillis(duration),
            TimeUnit.SECONDS.toMillis(interval)) {

            override fun onFinish() {
                mTimerLCWTopLeftUp.start()
            }

            override fun onTick(millisUntilFinished: Long) {
                mTxtHeadway.text = (mTxtHeadway.text.toString().toInt() - 1).toString()
            }
        }

        val mTimerLCWTopRightUp = object : CountDownTimer(TimeUnit.SECONDS.toMillis(duration),
            TimeUnit.SECONDS.toMillis(interval)) {
            override fun onFinish() {
                lcwCurrentPos = LCWPosition.LCW_TOP_LEFT
                mTimerLCWTopLeft.start()
            }

            override fun onTick(millisUntilFinished: Long) {
                val counter = duration - TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                mTxtHeadway.text = counter.toString()
            }
        }
        val mTimerLCWTopRight = object : CountDownTimer(TimeUnit.SECONDS.toMillis(duration),
            TimeUnit.SECONDS.toMillis(interval)) {

            override fun onFinish() {
                mTimerLCWTopRightUp.start()
            }

            override fun onTick(millisUntilFinished: Long) {
                mTxtHeadway.text = (mTxtHeadway.text.toString().toInt() - 1).toString()
            }
        }

        val mTimerLCWBottomLeftUp = object : CountDownTimer(TimeUnit.SECONDS.toMillis(duration),
            TimeUnit.SECONDS.toMillis(interval)) {
            override fun onFinish() {
                lcwCurrentPos = LCWPosition.LCW_TOP_RIGHT
                mTimerLCWTopRight.start()
            }

            override fun onTick(millisUntilFinished: Long) {
                val counter = duration - TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                mTxtHeadway.text = counter.toString()
            }
        }
        val mTimerLCWBottomLeft = object : CountDownTimer(TimeUnit.SECONDS.toMillis(duration),
            TimeUnit.SECONDS.toMillis(interval)) {

            override fun onFinish() {
                mTimerLCWBottomLeftUp.start()
            }

            override fun onTick(millisUntilFinished: Long) {
                mTxtHeadway.text = (mTxtHeadway.text.toString().toInt() - 1).toString()
            }
        }

        val mTimerLCWBottomRightUp = object : CountDownTimer(TimeUnit.SECONDS.toMillis(duration),
            TimeUnit.SECONDS.toMillis(interval)) {
            override fun onFinish() {
                lcwCurrentPos = LCWPosition.LCW_BOTTOM_LEFT
                mTimerLCWBottomLeft.start()
            }

            override fun onTick(millisUntilFinished: Long) {
                val counter = duration - TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                mTxtHeadway.text = counter.toString()
            }
        }
        val mTimerLCWBottomRight = object : CountDownTimer(TimeUnit.SECONDS.toMillis(duration),
            TimeUnit.SECONDS.toMillis(interval)) {

            override fun onFinish() {
                mTimerLCWBottomRightUp.start()
            }

            override fun onTick(millisUntilFinished: Long) {
                mTxtHeadway.text = (mTxtHeadway.text.toString().toInt() - 1).toString()
            }
        }
        mTxtHeadway.text = duration.toString()
        lcwCurrentPos = LCWPosition.LCW_BOTTOM_RIGHT
        mTimerLCWBottomRight.start()
    }
}
