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

class MainFragment : Fragment() {

    private lateinit var mImgCar : ImageView
    private lateinit var mImgLeft : ImageView
    private lateinit var mImgRight : ImageView
    private lateinit var mTxtHeadway : TextView

    private val sharedModel : FragmentSharedModel by activityViewModels()
    private lateinit var navController : NavController
    private lateinit var mainPreferences : SharedPreferences
    private lateinit var mainPrefEditor : SharedPreferences.Editor

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
        mainPrefEditor = mainPreferences.edit()
        mImgCar = view.findViewById(R.id.imgCar)
        mImgLeft = view.findViewById(R.id.imgLeft)
        mImgRight = view.findViewById(R.id.imgRight)
        mTxtHeadway = view.findViewById(R.id.txtHeadway)

        // TODO setup listener from navigation drawer
    }

    fun simulateFcw() {
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
