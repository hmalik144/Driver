package h_mal.appttude.com.driver.SuperUser

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import h_mal.appttude.com.driver.ui.driver.MainActivity
import h_mal.appttude.com.driver.Objects.UserObject
import h_mal.appttude.com.driver.Objects.WholeObject.MappedObject
import h_mal.appttude.com.driver.R


class RecyclerViewAdapter constructor(var context: Context?, var objects: List<MappedObject>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        val viewCurrent: View =
            LayoutInflater.from(context).inflate(R.layout.list_item_layout, viewGroup, false)
        return ViewHolderMain(viewCurrent)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        val viewHolderCurrent: ViewHolderMain = viewHolder as ViewHolderMain
        val mappedObject: MappedObject = objects!!.get(i)
        val `object`: UserObject? = mappedObject.wholeDriverObject?.user_details
        if (`object`!!.profilePicString != null) {
            Picasso.get()
                .load(`object`.profilePicString)
                .resize(128, 128)
                .placeholder(R.drawable.choice_img_round)
                .into(viewHolderCurrent.profilePicImage)
        } else {
            viewHolderCurrent.profilePicImage.setImageResource(R.drawable.choice_img_round)
        }
        viewHolderCurrent.userNameTextView.setText(`object`.profileName)
        viewHolderCurrent.userEmailTextView.setText(`object`.profileEmail)
        if (mappedObject.wholeDriverObject?.driver_number == null) {
            viewHolderCurrent.driverNo.text = "0"
        } else {
            val s: String = mappedObject.wholeDriverObject?.driver_number.toString()
            viewHolderCurrent.driverNo.text = s
        }
        viewHolderCurrent.driverNo.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(
                context
            )
            val input: EditText = EditText(context)
            val layout: LinearLayout = LinearLayout(context)
            layout.orientation = LinearLayout.VERTICAL
            layout.setPadding(28, 0, 56, 0)
            input.setText(viewHolderCurrent.driverNo.text.toString())
            input.setSelectAllOnFocus(true)
            layout.addView(input)
            builder.setTitle("Change Driver Number")
                .setView(layout)
                .setPositiveButton(
                    "Submit"
                ) { dialog, which ->

                }.create()
                .show()
        }
//        viewHolderCurrent.profileApprovalImage.setImageResource(
//            MainActivity.approvalsClass!!.getOverApprovalStatusCode(mappedObject.wholeDriverObject)
//        )
        viewHolderCurrent.itemView.setOnClickListener {
            val bundle: Bundle = Bundle()
            bundle.putParcelable("mapped", mappedObject)
//            executeFragment(UserMainFragment(), bundle)
        }
    }

    override fun getItemCount(): Int {
        return objects!!.size
    }

    internal inner class ViewHolderMain constructor(listItemView: View) :
        RecyclerView.ViewHolder(listItemView) {
        var profilePicImage: ImageView
        var userNameTextView: TextView
        var userEmailTextView: TextView

        //        CardView statusCard;
        var profileApprovalImage: ImageView
        var driverNo: TextView

        init {
            profilePicImage = listItemView.findViewById(R.id.driverPic)
            userNameTextView = listItemView.findViewById(R.id.username_text)
            //            statusCard = listItemView.findViewById(R.id.status_icon);
            userEmailTextView = listItemView.findViewById(R.id.emailaddress_text)
            profileApprovalImage = listItemView.findViewById(R.id.approval_iv)
            driverNo = listItemView.findViewById(R.id.driver_no)
        }
    }
}