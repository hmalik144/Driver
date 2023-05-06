package h_mal.appttude.com.driver.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.base.BaseFirebaseAdapter
import h_mal.appttude.com.driver.base.BaseFragment
import h_mal.appttude.com.driver.base.CustomViewHolder
import h_mal.appttude.com.driver.data.USER_CONST
import h_mal.appttude.com.driver.databinding.FragmentHomeSuperUserBinding
import h_mal.appttude.com.driver.databinding.ListItemLayoutBinding
import h_mal.appttude.com.driver.model.SortOption
import h_mal.appttude.com.driver.objects.UserObject
import h_mal.appttude.com.driver.objects.WholeDriverObject
import h_mal.appttude.com.driver.utils.*
import h_mal.appttude.com.driver.viewmodels.SuperUserViewModel
import java.util.*


class HomeSuperUserFragment : BaseFragment<SuperUserViewModel, FragmentHomeSuperUserBinding>(), MenuProvider {
    private lateinit var adapter: FirebaseRecyclerAdapter<WholeDriverObject, CustomViewHolder<ListItemLayoutBinding>>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        viewModel.retrieveDefaultFirebaseOptions()
    }

    override fun onSuccess(data: Any?) {
        super.onSuccess(data)
        when (data) {
            is FirebaseRecyclerOptions<*> -> setAdapterToRecyclerView(data)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun setAdapterToRecyclerView(options: FirebaseRecyclerOptions<*>) {
        applyBinding {
            progressCircular.show()
            if (recyclerView.adapter == null) {
                // create an adapter for the first time
                adapter = createAdapter(options = options as FirebaseRecyclerOptions<WholeDriverObject>)
                recyclerView.adapter = adapter
                recyclerView.setHasFixedSize(true)
                adapter.startListening()
            } else {
                adapter.updateOptions(options as FirebaseRecyclerOptions<WholeDriverObject>)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    private fun createAdapter(options: FirebaseRecyclerOptions<WholeDriverObject>): BaseFirebaseAdapter<WholeDriverObject, ListItemLayoutBinding> {
        return object : BaseFirebaseAdapter<WholeDriverObject, ListItemLayoutBinding>(options, layoutInflater) {

            override fun onBindViewHolder(
                holder: CustomViewHolder<ListItemLayoutBinding>,
                position: Int,
                model: WholeDriverObject
            ) {
                val userDetails: UserObject? = model.user_details
                holder.viewBinding.apply {
                    driverPic.setGlideImage(userDetails?.profilePicString)
                    usernameText.text = userDetails?.profileName
                    emailaddressText.text = userDetails?.profileEmail
                    driverNo.run {
                        val number = model.driver_number ?: "0"
                        text = number
                        setOnClickListener {
                            val uid = getKeyAtPosition(position)
                            if (uid != null) {
                                showChangeNumberDialog(uid, number)
                            }
                        }
                    }
                    root.setOnClickListener {
                        it.navigateTo(
                            R.id.action_homeAdminFragment_to_userMainFragment,
                            snapshots.getSnapshot(position).key?.toBundle(USER_CONST)
                        )
                    }
                }
            }

            override fun onDataChanged() {
                super.onDataChanged()
                applyBinding {
                    // If there are no chat messages, show a view that invites the user to add a message.
                    if (itemCount == 0) {
                        emptyView.root.visibility = if (itemCount == 0) View.VISIBLE else View.GONE
                    }

                    progressCircular.hide()
                }
            }

            override fun connectionLost() {
                requireContext().displayToast("No connection available")
            }
        }
    }

    private fun showChangeNumberDialog(defaultNumber: String, uid: String) {
        val inputText = EditText(context).apply {
            setText(defaultNumber)
            setSelectAllOnFocus(true)
        }
        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(28, 0, 56, 0)
            addView(inputText)
        }

        AlertDialog.Builder(context).setTitle("Change Driver Number")
            .setView(layout)
            .setPositiveButton("Submit") { _, _ ->
                val input = inputText.text?.toString()
                viewModel.updateDriverNumber(uid, input)
            }.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_calls_fragment, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.archive) {
            displaySortOptions()
        }
        return true
    }

    private fun displaySortOptions() {
        val groupName: Array<String> = arrayOf("Driver Name", "Driver Number")
        val defaultPosition = viewModel.getSelectionAsPosition()
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("Sort by:")
            .setSingleChoiceItems(
                groupName,
                defaultPosition
            ) { _, pos ->
                val option = SortOption.getSortOptionByLabel(groupName[pos])
                viewModel.createFirebaseOptions(sort = option)
            }
            .create().show()
    }
}