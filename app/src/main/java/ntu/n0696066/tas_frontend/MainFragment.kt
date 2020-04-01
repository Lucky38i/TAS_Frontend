package ntu.n0696066.tas_frontend

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log.d
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_bottomsheet.*
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private lateinit var mImgCar : ImageView
    private lateinit var mImgLeft : ImageView
    private lateinit var mImgRight : ImageView
    private lateinit var mTxtHeadway : TextView
    private lateinit var mMainDrawerLayout : DrawerLayout

    private val sharedModel : FragmentSharedModel by lazy {
        ViewModelProvider(this).get(FragmentSharedModel::class.java)
    }

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
        // Attributes

        // Instantiation
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
                    Toast.makeText(requireContext(), "Settings Clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        mainBottomAppBar.setNavigationOnClickListener {
            val bottomNavigationDrawerFragment = BottomNavigationDrawerFragment()
            bottomNavigationDrawerFragment.show(this.parentFragmentManager,
                bottomNavigationDrawerFragment.tag)
        }

        sharedModel.itemId.observe(this.viewLifecycleOwner,
            androidx.lifecycle.Observer { chosenID ->

                d("SharedModel", chosenID.toString())
                when (chosenID) {
                    R.id.nav_fcw -> {
                        mTxtHeadway.text = "fcw chosen"
                    }
                    R.id.nav_lcw -> {
                        mTxtHeadway.text = "lcw chosen"
                    }
                    R.id.nav_rcw -> {
                        mTxtHeadway.text = "rcw chosen"
                    }
                    R.id.nav_headway -> {
                        mTxtHeadway.text = "headway chosen"
                    }
                    else -> {}

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
