package ntu.n0696066.tas_frontend

import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.preference.PreferenceManager

class Splash : Fragment() {
    private lateinit var mainPreferences : SharedPreferences
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        mainPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

        // Shitty attempt at a splash screen, will re-do properly later
        val mTimer = object : CountDownTimer(3000, 1000) {
            override fun onFinish() {
                if ( mainPreferences.getBoolean(getString(R.string.pref_onboardFinished_key),
                        resources.getBoolean(R.bool.pref_onboardFinished_default))) {
                    navController.navigate(R.id.action_splash_to_mainFragment)
                } else navController.navigate(R.id.action_splash_to_greetingFragment)
            }
            override fun onTick(millisUntilFinished: Long) {}
        }
        mTimer.start()


    }
}
