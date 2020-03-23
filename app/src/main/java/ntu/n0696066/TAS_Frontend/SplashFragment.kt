package ntu.n0696066.TAS_Frontend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.navigation.NavController
import androidx.navigation.Navigation

class Splash : Fragment() {
    private lateinit var splashProgress : ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController : NavController = Navigation.findNavController(view)
        splashProgress = view.findViewById(R.id.splash_progress)
        Thread.sleep(3000L)
        navController.navigate(R.id.action_splash_to_onboard)
    }
}
