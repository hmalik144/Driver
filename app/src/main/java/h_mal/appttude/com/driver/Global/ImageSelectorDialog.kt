package h_mal.appttude.com.driver.Global

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import h_mal.appttude.com.driver.R
import java.io.File
import java.util.*


class ImageSelectorDialog : Dialog {
    private val TAG: String = this.javaClass.simpleName
    private var saveFileName: String
    var fragment: Fragment? = null

    constructor(context: Context) : super(context) {
        saveFileName = "default_name"
    }

    constructor(context: Context, themeResId: Int) : super(context, themeResId) {
        saveFileName = "default_name"
    }

    protected constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(context, cancelable, cancelListener) {
        saveFileName = "default_name"
    }

    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.address_dialog)

        //check if we have we have storage rights
        val permissionPic: Int = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val permissionCam: Int =
            ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
//        fragment = MainActivity.mainFragmentManager.fragments[0]
        val upload: Button = findViewById(R.id.upload)
        val takePic: Button = findViewById(R.id.take_pic)
        upload.setOnClickListener {
            if (permissionPic == PackageManager.PERMISSION_GRANTED) {
                chooseImage()
            } else {
                Toast.makeText(context, "Storage permissions required", Toast.LENGTH_SHORT)
                    .show()
                ActivityCompat.requestPermissions(
                    (fragment!!.requireActivity()),
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    STORAGE_PERMISSION_CODE
                )
            }
            dismiss()
        }
        takePic.setOnClickListener {
            if (permissionCam == PackageManager.PERMISSION_GRANTED) {
                val cameraIntent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                val file: File = createFile()
                photoURI = Uri.fromFile(file)
                val imageUri: Uri = FileProvider.getUriForFile(
                    context,
                    "h_mal.appttude.com.driver",
                    file
                )
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                fragment!!.startActivityForResult(cameraIntent, CAMERA_REQUEST)
            } else {
                Toast.makeText(context, "Camera Permissions required", Toast.LENGTH_SHORT)
                    .show()
                ActivityCompat.requestPermissions(
                    (fragment!!.requireActivity()), arrayOf(Manifest.permission.CAMERA),
                    MY_CAMERA_PERMISSION_CODE
                )
            }
            dismiss()
        }
    }

    fun setImageName(saveFileName: String) {
        this.saveFileName = saveFileName
    }

    private fun createFile(): File {
        //create directory
        val root: File = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        //create file
        val sdf: SimpleDateFormat = SimpleDateFormat("yyyyMMdd_HHmm")
        val currentDateandTime: String = sdf.format(Date())
        val fname: String = saveFileName + currentDateandTime
        val image: File = File(root, fname)
        return image
    }

    private fun chooseImage() {
        val intent: Intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        fragment!!.startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            PICK_IMAGE_REQUEST
        )
    }

    companion object {
        val PICK_IMAGE_REQUEST: Int = 71
        val CAMERA_REQUEST: Int = 1888
        val MY_CAMERA_PERMISSION_CODE: Int = 100
        val STORAGE_PERMISSION_CODE: Int = 101
        var photoURI: Uri? = null
    }
}