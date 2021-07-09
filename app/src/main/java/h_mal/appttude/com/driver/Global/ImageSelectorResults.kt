package h_mal.appttude.com.driver.Global

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import h_mal.appttude.com.driver.utils.DateUtils.getDateStamp
import h_mal.appttude.com.driver.utils.DateUtils.getDateTimeStamp
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ImageSelectorResults  //    public FilepathResponse delegate;
{
    lateinit var activity: Activity

    interface FilepathResponse {
        fun processFinish(output: Uri?)
    }

    fun Results(
        activity: Activity, requestCode: Int, resultCode: Int, data: Intent?, filePath: Uri?,
        imageView: ImageView?, delegate: FilepathResponse
    ) {
        var filePath: Uri? = filePath
        this.activity = activity
        if ((requestCode == ImageSelectorDialog.PICK_IMAGE_REQUEST) && (resultCode == Activity.RESULT_OK
                    ) && (data != null) && (data.data != null)
        ) {
            val uri = data.data
            var bitmap: Bitmap? = null
            try {
                bitmap =
                    MediaStore.Images.Media.getBitmap(activity.contentResolver, uri)
                if (imageView!!.visibility != View.VISIBLE) {
                    imageView.visibility = View.VISIBLE
                }
                imageView.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (bitmap != null) {
                    delegate.processFinish(uri)
                }
            }
        }
        if (requestCode == ImageSelectorDialog.CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            //check if we have we have storage rights
            val permission: Int = ActivityCompat.checkSelfPermission(
                (activity)!!,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            if (permission != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(activity, "Storage permissions not granted", Toast.LENGTH_SHORT)
                    .show()
                return
            } else {
                try {
                    val f: File =
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    val fname: String = "driver" + getDateStamp() + ".jpg"
                    val image = File(f, fname)
                    val fileOutputStream = FileOutputStream(image)
                    filePath = ImageSelectorDialog.photoURI
                    val bitmap: Bitmap = MediaStore.Images.Media
                        .getBitmap(
                            activity.contentResolver,
                            ImageSelectorDialog.photoURI
                        )
                    imageView!!.setImageBitmap(bitmap)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream)
                    fileOutputStream.flush()
                    fileOutputStream.close()
                    galleryAddPic()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            delegate.processFinish(filePath)
        }
    }

    fun Results(
        activity: Activity,
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        filePath: Uri?,
        delegate: FilepathResponse
    ) {
        var filePath: Uri? = filePath
        this.activity = activity
        if ((requestCode == ImageSelectorDialog.PICK_IMAGE_REQUEST) && (resultCode == Activity.RESULT_OK
                    ) && (data != null) && (data.data != null)
        ) {
            filePath = data.data
            var bitmap: Bitmap? = null
            try {
                bitmap =
                    MediaStore.Images.Media.getBitmap(activity.contentResolver, filePath)
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (bitmap != null) {
                    delegate.processFinish(filePath)
                }
            }
        }
        if (requestCode == ImageSelectorDialog.CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            //check if we have we have storage rights
            val permission: Int = ActivityCompat.checkSelfPermission(
                (activity),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            if (permission != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(activity, "Storage permissions not granted", Toast.LENGTH_SHORT)
                    .show()
                return
            } else {
                try {
                    val f: File =
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    val fname: String = "driver" + getDateTimeStamp() + ".jpg"
                    val image: File = File(f, fname)
                    val fileOutputStream: FileOutputStream = FileOutputStream(image)
                    filePath = ImageSelectorDialog.photoURI
                    val bitmap: Bitmap = MediaStore.Images.Media
                        .getBitmap(
                            activity.contentResolver,
                            ImageSelectorDialog.photoURI
                        )
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream)
                    fileOutputStream.flush()
                    fileOutputStream.close()
                    galleryAddPic()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            delegate.processFinish(filePath)
        }
    }

    private fun galleryAddPic() {
        val mediaScanIntent: Intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val f: File = File(ImageSelectorDialog.photoURI!!.path)
        val contentUri: Uri = Uri.fromFile(f)
        mediaScanIntent.data = contentUri
        activity.sendBroadcast(mediaScanIntent)
    }
}