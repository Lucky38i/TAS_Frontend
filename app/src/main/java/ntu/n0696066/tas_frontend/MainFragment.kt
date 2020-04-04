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
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_main.*
import ntu.n0696066.tas_frontend.CompareDrawables.bytesEqualTo
import ntu.n0696066.tas_frontend.CompareDrawables.pixelsEqualTo
import java.util.concurrent.TimeUnit

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
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            /**
             * A sort of sloppy implementation but it works, any changes to headway text and various
             * action occur no matter where the change is made making it easily adaptable to when
             * V2V HMI is being used
             */
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                when {
                    s.toString().toInt() > headWayDistance!! * 3 -> {
                        mTxtHeadway.setTextColor(resources.getColor(android.R.color.white,
                            null))

                    }
                    s.toString().toInt() == headWayDistance!! * 3 -> {
                        mTxtHeadway.setTextColor(resources.getColor(android.R.color.holo_green_dark,
                            null))
                        (mImgCar.drawable as AnimatedVectorDrawable).start()
                    }
                    s.toString().toInt() == headWayDistance!! * 2 -> {
                        mTxtHeadway.setTextColor(resources.getColor(R.color.yellow,
                            null))
                        mImgCar.setImageResource(R.drawable.avd_fcw_start_med)
                        (mImgCar.drawable as AnimatedVectorDrawable).start()
                    }
                    s.toString().toInt() == headWayDistance!! -> {
                        mTxtHeadway.setTextColor(resources.getColor(android.R.color.holo_red_dark,
                            null))
                        mImgCar.setImageResource(R.drawable.avd_fcw_med_end)
                        (mImgCar.drawable as AnimatedVectorDrawable).start()
                    }
                }
            }
        })
        // TODO Create listener for when settings have been changed
    }

    private fun simulateFcw() {
        val mTimeCollisionUp = object : CountDownTimer(TimeUnit.SECONDS.toMillis(5),
            TimeUnit.SECONDS.toMillis(1)) {
            override fun onFinish() {
                TODO("Not yet implemented")
            }

            override fun onTick(millisUntilFinished: Long) {
                TODO("Not yet implemented")
            }

        }
        val mTimerCollision = object : CountDownTimer(TimeUnit.SECONDS.toMillis(10),
            TimeUnit.SECONDS.toMillis(1)) {

            override fun onFinish() {
            }

            override fun onTick(millisUntilFinished: Long) {
                mTxtHeadway.text = (mTxtHeadway.text.toString().toInt() - 1).toString()
            }
        }
        mTxtHeadway.text = 10.toString()
        mTimerCollision.start()
    }
}
