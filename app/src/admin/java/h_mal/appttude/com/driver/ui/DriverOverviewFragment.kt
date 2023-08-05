package h_mal.appttude.com.driver.ui

import android.widget.ListView
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.base.BaseFragment
import h_mal.appttude.com.driver.data.USER_CONST
import h_mal.appttude.com.driver.databinding.FragmentUserMainBinding
import h_mal.appttude.com.driver.model.ApprovalStatus
import h_mal.appttude.com.driver.utils.FRAGMENT
import h_mal.appttude.com.driver.utils.navigateTo
import h_mal.appttude.com.driver.utils.toBundle
import h_mal.appttude.com.driver.viewmodels.DriverOverviewViewModel
import java.io.IOException


class DriverOverviewFragment : BaseFragment<DriverOverviewViewModel, FragmentUserMainBinding>() {

    private lateinit var listView: ListView
    private lateinit var driverId: String

    override fun setupView(binding: FragmentUserMainBinding) {
        listView = binding.approvalsList
        loadList()
    }

    override fun onResume() {
        super.onResume()
        loadList()
    }

    override fun onSuccess(data: Any?) {
        super.onSuccess(data)
        @Suppress("UNCHECKED_CAST")
        if (data is List<*>) {
            val listData = data as List<Pair<String, ApprovalStatus>>
            if (listView.adapter == null) {
                listView.adapter = ApprovalListAdapter(requireContext(), listData) {
                    this.view?.navigateTo(
                        R.id.to_approverFragment,
                        driverId.toBundle(USER_CONST).apply { putString(FRAGMENT, it) })
                }
                listView.isScrollContainer = false
            } else {
                (listView.adapter as ApprovalListAdapter).updateAdapter(listData)
            }
        }
    }

    private fun loadList() {
        driverId = requireArguments().getString(USER_CONST)
            ?: throw IOException("No user ID has been passed")
        viewModel.loadDriverApprovals(driverId)
    }
}