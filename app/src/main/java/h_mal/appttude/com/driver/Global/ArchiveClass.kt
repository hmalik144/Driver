package h_mal.appttude.com.driver.Global

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import h_mal.appttude.com.driver.MainActivity


class ArchiveClass {
    fun archiveRecord(UID: String?, item: String?, `object`: Any?) {
        val toPath: DatabaseReference =
            MainActivity.mDatabase!!.child(FirebaseClass.USER_FIREBASE).child(
                (UID)!!
            )
                .child(FirebaseClass.ARCHIVE_FIREBASE).child((item)!!)
        toPath.child(MainActivity.Companion.getDateTimeStamp()).setValue(`object`)
            .addOnCompleteListener(object : OnCompleteListener<Void?> {
                override fun onComplete(task: Task<Void?>) {
                    if (task.isSuccessful) {
                        Log.i(TAG, "onComplete: archive successful")
                    } else {
                        Log.i(TAG, "onComplete: archive unsuccessful")
                    }
                }
            })
    }

    fun openDialogArchive(context: Context?, `object`: Any?, fragment: Fragment) {
        if (`object` == null) {
            ExecuteFragment.executeFragment(fragment)
        } else {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setMessage("Do you want to View/Edit or Upload new?")
                .setPositiveButton("View/Edit", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, which: Int) {
                        ExecuteFragment.executeFragment(fragment)
                    }
                })
                .setNegativeButton("Upload New", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, which: Int) {
                        val bundle: Bundle = Bundle()
                        bundle.putString(ExecuteFragment.UPLOAD_NEW, "Yes")
                        fragment.arguments = bundle
                        ExecuteFragment.executeFragment(fragment)
                    }
                })
                .create().show()
        }
    }

    fun openDialogArchive(context: Context?, `object`: Any?, user: String?, fragment: Fragment?) {
        val bundle: Bundle = Bundle()
        bundle.putString("user_id", user)
        fragment!!.arguments = bundle
        if (`object` == null) {
            ExecuteFragment.executeFragment(fragment)
        } else {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setMessage("Do you want to View/Edit or Upload new?")
                .setPositiveButton("View/Edit", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, which: Int) {
                        ExecuteFragment.executeFragment(fragment)
                    }
                })
                .setNegativeButton("Upload New", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, which: Int) {
                        bundle.putString(ExecuteFragment.UPLOAD_NEW, "Yes")
                        ExecuteFragment.executeFragment(fragment)
                    }
                })
                .create().show()
        }
    }

    companion object {
        private val TAG: String = "ArchiveClass"
    }
}