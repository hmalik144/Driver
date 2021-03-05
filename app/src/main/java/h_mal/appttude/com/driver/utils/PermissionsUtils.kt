package h_mal.appttude.com.driver.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


object PermissionsUtils {
    /**
     * Check if you have been granted a particular permission.
     *
     * @param permission The name of the permission being checked.
     *
     * @return boolean if you have the permission, or  if not.
     */
    fun Context.isPermissionsAllowed(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this, permission
        ) == PackageManager.PERMISSION_GRANTED
    }


    fun Activity.askForPermissions(permission: String, requestCode: Int): Boolean {
        if (!isPermissionsAllowed(permission)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
            }
            return false
        }
        return true
    }

    fun Fragment.askForPermissions(permission: String, requestCode: Int): Boolean =
        requireActivity().askForPermissions(permission, requestCode)

    fun isGranted(grantResults: IntArray): Boolean =
        grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED


    private fun Context.showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App Settings.")
            .setPositiveButton("App Settings") { _, _ ->
                // send to app settings if permission is denied permanently
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

}