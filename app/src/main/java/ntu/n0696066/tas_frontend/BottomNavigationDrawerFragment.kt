package ntu.n0696066.tas_frontend

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bottomsheet.*

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private val sharedModel : FragmentSharedModel by lazy {
        ViewModelProvider(this).get(FragmentSharedModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottomsheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigation_view.setNavigationItemSelectedListener {item ->
            when (item.itemId) {
                R.id.nav_fcw -> {
                    Toast.makeText(this.requireContext(), "FCW Clicked",
                        Toast.LENGTH_SHORT).show()
                    sharedModel.chosenItem(item.itemId)
                    d("NavFragment", sharedModel.itemId.value.toString())
                }
                R.id.nav_rcw -> {
                    Toast.makeText(this.requireContext(), "RCW Clicked",
                        Toast.LENGTH_SHORT).show()
                    sharedModel.chosenItem(item.itemId)
                }
                R.id.nav_headway -> {
                    Toast.makeText(this.requireContext(), "Headway Clicked",
                        Toast.LENGTH_SHORT).show()
                    sharedModel.chosenItem(item.itemId)
                }
                R.id.nav_lcw -> {
                    Toast.makeText(this.requireContext(), "LCW Clicked",
                        Toast.LENGTH_SHORT).show()
                    sharedModel.chosenItem(item.itemId)
                }
            }
            true

        }
    }
}