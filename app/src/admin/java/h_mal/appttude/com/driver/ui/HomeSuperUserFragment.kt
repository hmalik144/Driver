package h_mal.appttude.com.driver.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.view.MenuProvider
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import com.firebase.ui.common.ChangeEventType
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.base.BaseFirebaseAdapter
import h_mal.appttude.com.driver.base.BaseFirebaseListAdapter
import h_mal.appttude.com.driver.base.BaseFragment
import h_mal.appttude.com.driver.base.CustomViewHolder
import h_mal.appttude.com.driver.data.USER_CONST
import h_mal.appttude.com.driver.databinding.FragmentHomeSuperUserBinding
import h_mal.appttude.com.driver.databinding.ListItemLayoutBinding
import h_mal.appttude.com.driver.model.DatabaseStatus
import h_mal.appttude.com.driver.model.DatabaseStatus.*
import h_mal.appttude.com.driver.model.SortOption
import h_mal.appttude.com.driver.objects.UserObject
import h_mal.appttude.com.driver.objects.WholeDriverObject
import h_mal.appttude.com.driver.utils.*
import h_mal.appttude.com.driver.viewmodels.SuperUserViewModel
import java.util.*


class HomeSuperUserFragment : BaseFragment<SuperUserViewModel, FragmentHomeSuperUserBinding>(),
    MenuProvider {
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
    private fun setNonView(status: DatabaseStatus) {
        applyBinding {
            emptyView.run {
                root.setOnClickListener(null)
                root.visibility = View.VISIBLE
                icon.setImageResource(status.drawable)
                header.setText(status.header)
                subtext.setText(status.subtext)
            }
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

    override fun onStart() {
        super.onStart()
        applyBinding {
            if (recyclerView.adapter != null) {
                adapter.startListening()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    private fun createAdapter(options: FirebaseRecyclerOptions<WholeDriverObject>): BaseFirebaseAdapter<WholeDriverObject, ListItemLayoutBinding> {
        return object :
            BaseFirebaseAdapter<WholeDriverObject, ListItemLayoutBinding>(options, layoutInflater) {
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
                        val number =
                            if (model.driver_number.isNullOrBlank()) "#N/A" else model.driver_number
                        text = number
                        setOnClickListener {
                            getKeyAtPosition(position)?.let { showChangeNumberDialog(number!!, it) }
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
                    // If there are no driver data, show a view that informs the admin.
                    emptyView.root.visibility = if (itemCount == 0) View.VISIBLE else View.GONE
                    progressCircular.hide()
                }
            }

            override fun onChildChanged(
                type: ChangeEventType,
                snapshot: DataSnapshot,
                newIndex: Int,
                oldIndex: Int
            ) {
                super.onChildChanged(type, snapshot, newIndex, oldIndex)
                applyBinding { progressCircular.hide() }
            }

            override fun authorizationError() {
                setNonView(NO_AUTHORIZATION)
            }

            override fun cannotRetrieve() {
                setNonView(CANNOT_RETRIEVE)
            }

            override fun noConnection() {
                setNonView(NO_CONNECTION)
            }

            override fun permissionsDenied() {
                setNonView(NO_PERMISSION)
            }

            override fun emptyList() {
                setNonView(EMPTY_RESULTS)
            }
        }
    }

    private fun showChangeNumberDialog(defaultNumber: String, uid: String) {
        val inputText = EditText(context).apply {
            setTag(R.string.driver_identifier, "DriverIdentifierInput")
            setText(defaultNumber)
            setSelectAllOnFocus(true)
            doOnTextChanged { _, _, count, _ -> if (count > 6) showToast("Identifier cannot be larger than 6") }
        }
        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(28, 0, 56, 0)
            addView(inputText)
        }

        AlertDialog.Builder(requireContext(), R.style.AppTheme_AppBarOverlay)
            .setTitle("Change Driver Identifier")
            .setView(layout)
            .setPositiveButton("Submit") { _, _ ->
                val input = inputText.text?.toString()
                input?.let { viewModel.updateDriverNumber(uid, it) }
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