package h_mal.appttude.com.driver.ui

import com.google.android.material.snackbar.Snackbar
import h_mal.appttude.com.driver.base.BaseFragment
import h_mal.appttude.com.driver.databinding.FragmentApproverBinding
import h_mal.appttude.com.driver.model.ApprovalStatus
import h_mal.appttude.com.driver.viewmodels.ApproverViewModel


class ApproverFragment : BaseFragment<ApproverViewModel, FragmentApproverBinding>() {

    override fun setupView(binding: FragmentApproverBinding) = binding.run {
        super.setupView(binding)

        val args = requireArguments()
        viewModel.init(args)

        // Retrieve fragment name argument saved from previous fragment
        val fragmentClass = viewModel.getFragmentClass()

        childFragmentManager.beginTransaction()
            .replace(container.id, fragmentClass, args, null)
            .commitNow()

        approve.setOnClickListener { viewModel.approveDocument() }
        decline.setOnClickListener { viewModel.declineDocument() }
    }

    override fun onSuccess(data: Any?) {
        super.onSuccess(data)
        when (data) {
            ApprovalStatus.APPROVED -> displaySnackBar("approved")
            ApprovalStatus.DENIED -> displaySnackBar("declined")
        }
    }

    private fun displaySnackBar(status: String) {
        Snackbar.make(
            requireView(),
            "Document has been $status",
            Snackbar.LENGTH_LONG
        ).show()
    }
}