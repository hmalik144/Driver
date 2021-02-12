package h_mal.appttude.com.driver.SuperUser

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target
import h_mal.appttude.com.driver.Global.ExecuteFragment
import h_mal.appttude.com.driver.Global.FirebaseClass
import h_mal.appttude.com.driver.MainActivity
import h_mal.appttude.com.driver.Objects.UserObject
import h_mal.appttude.com.driver.Objects.WholeObject.MappedObject
import h_mal.appttude.com.driver.R


class ListViewSuperAdapter constructor(context: Context, objects: List<MappedObject?>) :
    ArrayAdapter<MappedObject?>(context, 0, objects) {
    override fun getView(i: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView: View? = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(
                R.layout.list_item_layout, parent, false
            )
        }
        Log.i("getviewposition", "getView: pos = " + i)
        val profilePicImage: ImageView = listItemView!!.findViewById(R.id.driverPic)
        //        final ProgressBar progressBar = listItemView.findViewById(R.id.pb_su_list);
        val userNameTextView: TextView = listItemView.findViewById(R.id.username_text)
        val userEmailTextView: TextView = listItemView.findViewById(R.id.emailaddress_text)
        val profileApprovalImage: ImageView = listItemView.findViewById(R.id.approval_iv)
        val driverNo: TextView = listItemView.findViewById(R.id.driver_no)
        val mappedObject: MappedObject? = getItem(i)
        val `object`: UserObject? = mappedObject.getWholeDriverObject().getUser_details()
        if (profilePicImage.drawable == null) {
            if (`object`!!.profilePicString != null) {
                Picasso.get()
                    .load(`object`.getProfilePicString())
                    .resize(128, 128)
                    .placeholder(R.drawable.choice_img_round)
                    .into(object : Target {
                        override fun onBitmapLoaded(bitmap: Bitmap, from: LoadedFrom) {
//                                progressBar.setVisibility(View.GONE);
                            profilePicImage.setImageBitmap(bitmap)
                        }

                        override fun onBitmapFailed(e: Exception, errorDrawable: Drawable) {
//                                progressBar.setVisibility(View.GONE);
                        }

                        override fun onPrepareLoad(placeHolderDrawable: Drawable) {
//                                progressBar.setVisibility(View.VISIBLE);
                        }
                    })
            } else {
                profilePicImage.setImageResource(R.drawable.choice_img_round)
            }
        }
        userNameTextView.setText(`object`.getProfileName())
        userEmailTextView.setText(`object`.getProfileEmail())
        if (mappedObject.getWholeDriverObject().driver_number == null) {
            driverNo.text = "0"
        } else {
            val s: String = mappedObject.getWholeDriverObject().getDriver_number().toString()
            driverNo.text = s
        }
        driverNo.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                val input: EditText = EditText(context)
                val layout: LinearLayout = LinearLayout(context)
                layout.orientation = LinearLayout.VERTICAL
                layout.setPadding(28, 0, 56, 0)
                input.setText(driverNo.text.toString())
                input.setSelectAllOnFocus(true)
                layout.addView(input)
                builder.setTitle("Change Driver Number")
                    .setView(layout)
                    .setPositiveButton("Submit", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface, which: Int) {
                            val reference: DatabaseReference =
                                MainActivity.mDatabase!!.child(FirebaseClass.USER_FIREBASE)
                                    .child(mappedObject.getUserId())
                                    .child(FirebaseClass.DRIVER_NUMBER)
                            Log.i("Dialog Driver no", "onClick: " + reference.toString())
                            reference.setValue(input.text.toString())
                                .addOnCompleteListener(object : OnCompleteListener<Void> {
                                    override fun onComplete(task: Task<Void>) {
                                        if (task.isSuccessful) {
                                            Toast.makeText(
                                                context,
                                                "Driver Number Changed",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            dialog.dismiss()
                                            notifyDataSetChanged()
                                            Log.i(
                                                "Dialog Driver no",
                                                "onComplete: " + task.result
                                            )
                                        } else {
                                            Log.e(
                                                "Dialog Driver no",
                                                "onComplete: ",
                                                task.exception
                                            )
                                        }
                                    }
                                })
                        }
                    }
                    ).create()
                    .show()
            }
        })
        profileApprovalImage.setImageResource(
            MainActivity.approvalsClass!!.getOverApprovalStatusCode(mappedObject.getWholeDriverObject())
        )
        listItemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val bundle: Bundle = Bundle()
                bundle.putParcelable("mapped", mappedObject)
                ExecuteFragment.executeFragment(UserMainFragment(), bundle)
            }
        })
        return (listItemView)
    }

    private fun hasImage(view: ImageView): Boolean {
        val drawable: Drawable? = view.drawable
        var hasImage: Boolean = (drawable != null)
        if (hasImage && (drawable is BitmapDrawable)) {
            hasImage = drawable.bitmap != null
        }
        return hasImage
    }
}