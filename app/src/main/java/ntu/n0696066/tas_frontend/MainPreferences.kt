package ntu.n0696066.tas_frontend

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class MainPreferences : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_main, rootKey)
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {

        return super.onPreferenceTreeClick(preference)
    }
}