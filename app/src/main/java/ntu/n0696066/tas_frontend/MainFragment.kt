package ntu.n0696066.tas_frontend

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_main.*

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
    }

    private fun simulateFcw() {
        val mTimer = object : CountDownTimer(30000, 5000) {
            var counter = 1
            override fun onFinish() {
            }

            override fun onTick(millisUntilFinished: Long) {
                mTxtHeadway.text = counter.toString()
                counter++
            }
        }
        mTimer.start()
    }
}
