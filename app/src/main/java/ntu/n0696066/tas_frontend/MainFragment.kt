package ntu.n0696066.tas_frontend

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log.d
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
import kotlin.math.round

class MainFragment : Fragment() {

    private lateinit var mImgCar : ImageView
    private lateinit var mImgLeft : ImageView
    private lateinit var mImgRight : ImageView
    private lateinit var mTxtHeadway : TextView

    private lateinit var navController : NavController
    private lateinit var mainPreferences : SharedPreferences
    private var headWayDistance : Int? = null
    private var unitSelected : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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

        // Assignments
        headWayDistance = mainPreferences.getInt(getString(R.string.pref_headway_key),
            resources.getInteger(R.integer.pref_headway_default))
        unitSelected = mainPreferences.getString(getString(R.string.pref_unitsMeasure_key),
            getString(R.string.pref_unitsMeasure_default))

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
                R.id.nav_headway -> {true}
                R.id.nav_rcw -> {true}
                R.id.nav_lcw -> {true}
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
             * actions occur no matter where the change is made making it easily adaptable to when
             * V2V HMI is being used
             */
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val goingDown = if (beforeInt != null) {
                    if (s.toString().toIntOrNull() != null) s.toString().toInt() < beforeInt!!
                    else false
                } else false  // Used to tell if the monitored headway is incrementing

                when {
                    s.toString().toInt() == headWayDistance!! * 4 -> {
                        mTxtHeadway.setTextColor(resources.getColor(android.R.color.white,
                            null))
                        if (!goingDown) {
                            mImgCar.setImageResource(R.drawable.avd_fcw_start_car)
                            (mImgCar.drawable as AnimatedVectorDrawable).start()
                        }

                    }
                    s.toString().toInt() == headWayDistance!! * 3 -> {
                        mTxtHeadway.setTextColor(resources.getColor(android.R.color.holo_green_dark,
                            null))
                        if (goingDown) {
                            (mImgCar.drawable as AnimatedVectorDrawable).start()
                        } else {
                            mImgCar.setImageResource(R.drawable.avd_fcw_med_start)
                            (mImgCar.drawable as AnimatedVectorDrawable).start()
                        }
                    }
                    s.toString().toInt() == headWayDistance!! * 2 -> {
                        mTxtHeadway.setTextColor(resources.getColor(R.color.yellow,
                            null))
                        if (goingDown) {
                            mImgCar.setImageResource(R.drawable.avd_fcw_start_med)
                            (mImgCar.drawable as AnimatedVectorDrawable).start()
                        } else {
                            mImgCar.setImageResource(R.drawable.avd_fcw_end_med)
                            (mImgCar.drawable as AnimatedVectorDrawable).start()
                        }
                    }
                    s.toString().toInt() == headWayDistance!! -> {
                        mTxtHeadway.setTextColor(resources.getColor(android.R.color.holo_red_dark,
                            null))
                        if (goingDown) {
                            mImgCar.setImageResource(R.drawable.avd_fcw_med_end)
                            (mImgCar.drawable as AnimatedVectorDrawable).start()
                        }
                    }
                }
            }
        })
        // TODO Create listener for when settings have been changed
    }

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
        mTimerCollision.start()
    }
}
