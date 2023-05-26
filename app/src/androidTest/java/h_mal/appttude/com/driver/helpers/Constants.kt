package h_mal.appttude.com.driver.helpers

import android.os.Environment
import java.io.File

/**
 * File paths for images on device
 */
fun getImagePath(imageConst: String): String {
    return File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
        "/Camera/images/$imageConst"
    ).absolutePath
}