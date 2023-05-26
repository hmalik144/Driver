package h_mal.appttude.com.driver.viewmodels

import com.firebase.ui.database.FirebaseRecyclerOptions
import h_mal.appttude.com.driver.base.BaseViewModel
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.data.prefs.PreferenceProvider
import h_mal.appttude.com.driver.model.SortOption
import h_mal.appttude.com.driver.objects.WholeDriverObject
import h_mal.appttude.com.driver.utils.Coroutines.io
import h_mal.appttude.com.driver.utils.isNotNull


class SuperUserViewModel(
    private val firebaseDatabaseSource: FirebaseDatabaseSource,
    private val preferenceProvider: PreferenceProvider
) : BaseViewModel() {

    fun retrieveDefaultFirebaseOptions() {
        val optionLabel = preferenceProvider.getSortOption()
        val option = SortOption.getSortOptionByLabel(optionLabel)
        createFirebaseOptions(option)
    }

    fun createFirebaseOptions(sort: SortOption? = null) {
        val ref = firebaseDatabaseSource.getUsersRef()

        sort?.isNotNull { preferenceProvider.setSortOption(it.label) }

//        val query = when(sort) {
//            NAME, NUMBER -> ref.orderByChild(sort.key)
////            SortOption.APPROVAL -> TODO()
//            else -> ref.orderByKey()
//        }

        val options = FirebaseRecyclerOptions.Builder<WholeDriverObject>()
            .setQuery(ref.orderByKey(), WholeDriverObject::class.java)
            .build()

        onSuccess(options)
    }

    fun updateDriverNumber(uid: String, input: String?) {
        io {
            doTryOperation("failed update driver identifier") {
                // validate input
                if (input.isNullOrBlank()) {
                    onError("No driver identifier provided")
                    return@doTryOperation
                }
                val text = if (input.length > 6) input.substring(0,7) else input

                firebaseDatabaseSource.run {
                    postToDatabaseRed(getDriverNumberRef(uid), text)
                    onSuccess(Unit)
                }
            }
        }
    }

    fun getSelectionAsPosition(): Int {
        val optionLabel = preferenceProvider.getSortOption()
        return SortOption.getPositionByLabel(optionLabel) ?: -1
    }

}