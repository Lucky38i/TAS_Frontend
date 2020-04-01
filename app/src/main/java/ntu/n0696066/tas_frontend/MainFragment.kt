package ntu.n0696066.tas_frontend

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private lateinit var mImgCar : ImageView
    private lateinit var mImgLeft : ImageView
    private lateinit var mImgRight : ImageView
    private lateinit var mTxtHeadway : TextView
    private lateinit var mMainDrawerLayout : DrawerLayout

    private val bottomNavigationDrawerFragment = BottomNavigationDrawerFragment()
    private val sharedModel : FragmentSharedModel by activityViewModels()
    private lateinit var navController : NavController
    private lateinit var mainPreferences : SharedPreferences
    private lateinit var mainPrefEditor : SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
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
        mMainDrawerLayout = view.findViewById(R.id.drawerLayoutMain)


        // method calls
        mainBottomAppBar.replaceMenu(R.menu.menu_bottombar)

        // Listeners
        mainBottomAppBar.setOnMenuItemClickListener { item ->
            when(item.itemId) {
                R.id.app_bar_settings -> {
                    // Temporary Measure
                    navController.navigate(R.id.action_mainFragment_to_mainPreferences)
                    true
                }
                else -> false
            }
        }
        mainBottomAppBar.setNavigationOnClickListener {
            bottomNavigationDrawerFragment.show(this.parentFragmentManager,
                bottomNavigationDrawerFragment.tag)
        }
        sharedModel.item.observe(this.viewLifecycleOwner,
            androidx.lifecycle.Observer { chosenID ->
                when (chosenID.itemId) {
                    R.id.nav_fcw -> mTxtHeadway.text = "fcw chosen"
                    R.id.nav_lcw -> mTxtHeadway.text = "lcw chosen"
                    R.id.nav_rcw -> mTxtHeadway.text = "rcw chosen"
                    R.id.nav_headway -> mTxtHeadway.text = "headway chosen"
                }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_bottombar, menu)
        super.onCreateOptionsMenu(menu, inflater)
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
