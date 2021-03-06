package ntu.n0696066.tas_frontend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation

class GreetingFragment : Fragment() {

    lateinit var navController : NavController
    lateinit var mGreetingNextBtn : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_greeting, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Init
        navController = Navigation.findNavController(view)
        mGreetingNextBtn = view.findViewById(R.id.greetingNextBtn)

        // Attribute Modification
        mGreetingNextBtn.animation = AnimationUtils.loadAnimation(this.requireContext(),
        R.anim.fade_in)
        mGreetingNextBtn.animation.startOffset = 2000
        mGreetingNextBtn.animation.start()

        //Listeners
        mGreetingNextBtn.setOnClickListener {
            navController.navigate(R.id.action_greetingFragment_to_onboard)
        }
    }
}
