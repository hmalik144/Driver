package h_mal.appttude.com.driver.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import h_mal.appttude.com.driver.databinding.ApprovalListItemBinding
import h_mal.appttude.com.driver.model.ApprovalStatus
import h_mal.appttude.com.driver.utils.hide
import java.io.IOException


class ApprovalListAdapter(
    private val context: Context,
    private val data: List<Pair<String, ApprovalStatus>>,
    private val callback: (String) -> Unit
) : ArrayAdapter<Pair<String, ApprovalStatus>>(context, 0, data) {

    override fun getCount(): Int = data.size

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView: View? = convertView
        val binding: ApprovalListItemBinding
        if (listItemView == null) {
            // Inflate view binding into listview cell
            binding = ApprovalListItemBinding.inflate(LayoutInflater.from(context), parent, false)
            listItemView = binding.root
            listItemView.setTag(listItemView.id, binding)
        } else {
            // cell exists so recycling view
            binding = listItemView.getTag(listItemView.id) as ApprovalListItemBinding
        }

        val key: String = getItem(position)?.first ?: throw IOException("No document name provided")
        val approvalStatus: ApprovalStatus? = getItem(position)?.second

        binding.approvalText.text = key
        approvalStatus?.let { item ->
            item.score.takeIf { it != 0 }?.let {
                binding.root.setOnClickListener { callback.invoke(key) }
            }
            binding.approvalIv.setImageResource(item.drawableId)
            binding.approvalStatus.text = context.getString(item.stringId)
        }
        // hide divider for first cell
        if (position == 0) binding.divider.hide()

        return (listItemView)
    }

    fun updateAdapter(date: List<Pair<String, ApprovalStatus>>) {
        clear()
        addAll(date)
    }

}