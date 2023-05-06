package h_mal.appttude.com.driver.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.databinding.ApprovalListItemBinding
import h_mal.appttude.com.driver.utils.hide


class ApprovalListAdapter(
    private val layoutInflater: LayoutInflater,
    private var approvals: Map<String, Int?>,
    private val callback: (String) -> Unit
) : BaseAdapter() {
    override fun getCount(): Int = approvals.size
    override fun getItem(position: Int): Map.Entry<String, Int?> = approvals.entries.elementAt(position)
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView: View? = convertView
        val binding: ApprovalListItemBinding
        if (listItemView == null) {
            binding = ApprovalListItemBinding.inflate(layoutInflater, parent, false)
            listItemView = binding.root
            listItemView.setTag(listItemView.id, binding)
        } else {
            binding = listItemView.getTag(listItemView.id) as ApprovalListItemBinding
        }

        val key = getItem(position).key
        val itemValue = getItem(position).value

        binding.approvalText.text = key
        if (itemValue != 0) {
            binding.root.setOnClickListener { callback.invoke(key) }
        }
        binding.approvalIv.setImageResource(getImageResourceBasedOnApproval(itemValue))
        binding.approvalStatus.text = listItemView.context.getString(getStringResourceBasedOnApproval(itemValue))

        if (position == 0) {
            binding.divider.hide()
        }

        return (listItemView)
    }
    @DrawableRes
    private fun getImageResourceBasedOnApproval(value: Int?):  Int {
        return when(value) {
            0 -> R.drawable.denied
            1 -> R.drawable.denied
            2 -> R.drawable.pending
            3 -> R.drawable.approved
            else -> R.drawable.pending
        }
    }

    @StringRes
    private fun getStringResourceBasedOnApproval(value: Int?):  Int {
        return when(value) {
            0 -> R.string.not_submitted
            1 -> R.string.denied
            2 -> R.string.pending
            3 -> R.string.approved
            else -> R.string.pending
        }
    }

    fun updateAdapter(data: Map<String, Int?>) {
        approvals = data
        notifyDataSetChanged()
    }

}