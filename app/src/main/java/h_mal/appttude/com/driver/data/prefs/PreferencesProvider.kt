package h_mal.appttude.com.driver.data.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

/**
 * Shared prefs class used for storing conversion name values as pairs
 * Then retrieving as pairs
 *
 */
const val SORT_OPTION = "SORT_OPTION"

class PreferenceProvider(context: Context) {

    private val appContext = context.applicationContext

    private val preference: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(appContext)

    fun setSortOption(sortLabel: String) {
        preference.edit()
            .putString(SORT_OPTION, sortLabel)
            .apply()
    }

    fun getSortOption(): String? = preference
        .getString(SORT_OPTION, null)

}