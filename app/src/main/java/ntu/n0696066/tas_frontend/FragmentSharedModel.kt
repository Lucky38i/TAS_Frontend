package ntu.n0696066.tas_frontend

import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FragmentSharedModel : ViewModel() {
    val item = MutableLiveData<MenuItem>()

    fun setItem(id : MenuItem) {
        item.value = id
    }

    fun getItem() : LiveData<MenuItem> {
        return item
    }
}