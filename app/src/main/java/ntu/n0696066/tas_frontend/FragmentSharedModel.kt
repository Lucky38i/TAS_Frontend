package ntu.n0696066.tas_frontend

import android.view.MenuItem
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FragmentSharedModel : ViewModel() {
    val itemId = MutableLiveData<Int>()

    fun chosenItem(id : Int) {
        itemId.value = id
    }
}