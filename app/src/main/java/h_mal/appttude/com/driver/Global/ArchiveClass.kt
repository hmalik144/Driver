package h_mal.appttude.com.driver.Global

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import h_mal.appttude.com.driver.utils.UPLOAD_NEW


class ArchiveClass {
    fun archiveRecord(UID: String?, item: String?, `object`: Any?) {
//        val toPath: DatabaseReference =
//            MainActivity.mDatabase!!.child(FirebaseClass.USER_FIREBASE).child(
//                (UID)!!
//            )
//                .child(FirebaseClass.ARCHIVE_FIREBASE).child((item)!!)
//        toPath.child(MainActivity.dateTimeStamp).setValue(`object`)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Log.i(TAG, "onComplete: archive successful")
//                } else {
//                    Log.i(TAG, "onComplete: archive unsuccessful")
//                }
//            }
    }

    fun openDialogArchive(context: Context?, `object`: Any?, fragment: Fragment) {
        if (`object` == null) {
//            executeFragment(fragment)
        } else {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setMessage("Do you want to View/Edit or Upload new?")
                .setPositiveButton("View/Edit"
                ) { _, _ ->
//                    executeFragment(fragment)
                }
                .setNegativeButton("Upload New"
                ) { _, _ ->
                    val bundle = Bundle()
                    bundle.putString(UPLOAD_NEW, "Yes")
                    fragment.arguments = bundle
//                    executeFragment(fragment)
                }
                .create().show()
        }
    }

    fun openDialogArchive(context: Context?, `object`: Any?, user: String?, fragment: Fragment?) {
        val bundle: Bundle = Bundle()
        bundle.putString("user_id", user)
        fragment!!.arguments = bundle
        if (`object` == null) {
//            executeFragment(fragment)
        } else {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setMessage("Do you want to View/Edit or Upload new?")
                .setPositiveButton("View/Edit"
                ) { _, _ ->
//                    executeFragment(fragment)
                }
                .setNegativeButton("Upload New"
                ) { _, _ ->
                    bundle.putString(UPLOAD_NEW, "Yes")
//                    executeFragment(fragment)
                }
                .create().show()
        }
    }

}