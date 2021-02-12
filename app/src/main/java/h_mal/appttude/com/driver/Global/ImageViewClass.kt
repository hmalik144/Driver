package h_mal.appttude.com.driver.Global

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.chrisbanes.photoview.PhotoView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import h_mal.appttude.com.driver.MainActivity
import h_mal.appttude.com.driver.R
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


class ImageViewClass {
    fun open(bitmap: Bitmap?) {
        Companion.bitmap = bitmap
        ExecuteFragment.executeFragment(ImageViewerFragment())
    }

    class ImageViewerFragment : Fragment() {
        private var view: View? = null
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
        }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_image_viewer, container, false)
            val fab: FloatingActionButton = view.findViewById(R.id.download_pic)
            if (bitmap != null) {
                val photoView: PhotoView = view.findViewById(R.id.photo_view)
                photoView.setImageBitmap(bitmap)
                fab.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View) {
                        try {
                            downloadPic()
                        } catch (e: FileNotFoundException) {
                            e.printStackTrace()
                        }
                    }
                })
            }
            return view
        }

        override fun onResume() {
            super.onResume()
            (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
            activity!!.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

        override fun onStop() {
            super.onStop()
            (activity as AppCompatActivity?)!!.supportActionBar!!.show()
            activity!!.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

        @Throws(FileNotFoundException::class)
        private fun downloadPic() {
            val f: File =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val fname: String = "driver" + MainActivity.Companion.dateStamp + ".jpg"
            val image: File = File(f, fname)
            val fileOutputStream: FileOutputStream = FileOutputStream(image)
            bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            try {
                fileOutputStream.flush()
                fileOutputStream.close()
                val mediaScanIntent: Intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                val contentUri: Uri = Uri.fromFile(image)
                mediaScanIntent.data = contentUri
                activity!!.sendBroadcast(mediaScanIntent)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        val IMAGE_VALUE: String = "image"
        private var bitmap: Bitmap? = null
    }
}