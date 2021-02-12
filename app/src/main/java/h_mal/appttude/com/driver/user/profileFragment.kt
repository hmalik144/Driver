package h_mal.appttude.com.driver.user

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import h_mal.appttude.com.driver.Global.FirebaseClass
import h_mal.appttude.com.driver.Global.ViewController
import h_mal.appttude.com.driver.MainActivity
import h_mal.appttude.com.driver.R


class profileFragment : Fragment() {
    private val TAG = this.javaClass.simpleName
    private var email: TextView? = null
    private var name: TextView? = null
    private var changePw: TextView? = null
    private var user: FirebaseUser? = null
    private var databaseReference: DatabaseReference? = null
    var viewController: ViewController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewController = ViewController(activity)
        user = MainActivity.auth!!.currentUser
        databaseReference =
            MainActivity.mDatabase!!.child(FirebaseClass.USER_FIREBASE).child(
                user!!.uid
            )
                .child(FirebaseClass.DRIVER_FIREBASE)
                .child(FirebaseClass.DRIVER_DETAILS_FIREBASE)
                .child("driverPic")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        email = view.findViewById(R.id.change_email)
        name = view.findViewById(R.id.change_profile_name)
        changePw = view.findViewById(R.id.change_pw)
        val button = view.findViewById<Button>(R.id.submit_profile)
        button.setOnClickListener(View.OnClickListener { MainActivity.fragmentManager!!.popBackStack() })
        name.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val dialog = AlertDialog.Builder(context)
                dialog.setTitle("Update Username")
                val titleBox = EditText(context)
                titleBox.setText(user!!.displayName)
                dialog.setView(titleBox)
                dialog.setPositiveButton("Update", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, which: Int) {
                        updateProfile(titleBox.text.toString().trim { it <= ' ' })
                    }
                })
                dialog.show()
            }
        })
        email.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                showDialog(EMAIL_CONSTANT)
            }
        })
        changePw.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                showDialog(PW_CONSTANT)
            }
        })
        return view
    }

    private fun updateProfile(profileName: String) {
        val profileUpdatesBuilder = UserProfileChangeRequest.Builder()
        if (!TextUtils.isEmpty(profileName)) {
            profileUpdatesBuilder.setDisplayName(profileName)
        }
        val profileUpdates = profileUpdatesBuilder.build()
        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener(object : OnCompleteListener<Void?> {
                override fun onComplete(task: Task<Void?>) {
                    if (task.isSuccessful) {
                        Log.d(TAG, "User profile updated.")
                        viewController!!.reloadDrawer()
                    }
                }
            })
            .addOnFailureListener(object : OnFailureListener {
                override fun onFailure(e: Exception) {
                    Toast.makeText(context, "Update Failed", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun changeCredentials(
        email: String,
        password: String,
        changeText: String,
        selector: String
    ) {
        // Get auth credentials from the user for re-authentication
        val credential = EmailAuthProvider
            .getCredential(email, password) // Current Login Credentials \\
        // Prompt the user to re-provide their sign-in credentials
        user!!.reauthenticate(credential)
            .addOnCompleteListener(object : OnCompleteListener<Void?> {
                override fun onComplete(task: Task<Void?>) {
                    Log.d(TAG, "User re-authenticated.")
                    user = FirebaseAuth.getInstance().currentUser
                    if ((selector == EMAIL_CONSTANT)) {
                        user!!.updateEmail(changeText)
                            .addOnCompleteListener(object : OnCompleteListener<Void?> {
                                override fun onComplete(task: Task<Void?>) {
                                    if (task.isSuccessful) {
                                        Log.d(TAG, "User email address updated.")
                                        Toast.makeText(
                                            context,
                                            "Update Successful",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        viewController!!.reloadDrawer()
                                    }
                                }
                            })
                    }
                    if ((selector == PW_CONSTANT)) {
                        user!!.updatePassword(changeText)
                            .addOnCompleteListener(object : OnCompleteListener<Void?> {
                                override fun onComplete(task: Task<Void?>) {
                                    if (task.isSuccessful) {
                                        Log.d(TAG, "User email address updated.")
                                        Toast.makeText(
                                            context,
                                            "Update Successful",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            })
                    }
                }
            })
    }

    private fun showDialog(update: String) {
        //Make new Dialog
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle("Update $update")
        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(28, 0, 56, 0)
        val box1 = EditText(context)
        box1.hint = "Current Email Address"
        box1.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        layout.addView(box1) // Notice this is an add method
        val box2 = EditText(context)
        box2.hint = "Current Password"
        box2.inputType = InputType.TYPE_CLASS_TEXT or
                InputType.TYPE_TEXT_VARIATION_PASSWORD
        layout.addView(box2) // Another add method
        val box3 = EditText(context)
        if ((update == PW_CONSTANT)) {
            box3.inputType = InputType.TYPE_CLASS_TEXT or
                    InputType.TYPE_TEXT_VARIATION_PASSWORD
        } else {
            box3.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        }
        box3.hint = "New $update"
        layout.addView(box3) // Another add method
        dialog.setView(layout)
        dialog.setPositiveButton("Update", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, which: Int) {
                val email = box1.text.toString().trim { it <= ' ' }
                val password = box2.text.toString().trim { it <= ' ' }
                val textThree = box3.text.toString().trim { it <= ' ' }
                changeCredentials(email, password, textThree, update)
            }
        })
        dialog.show()
    }

    companion object {
        private val EMAIL_CONSTANT = "Email Address"
        private val PW_CONSTANT = "Password"
    }
}