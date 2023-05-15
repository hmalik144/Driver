package h_mal.appttude.com.driver.helpers

import android.os.Environment
import java.io.File

/**
 * File paths for images on device
 */
private const val BASE = "/Camera/images/"
const val PROFILE_PIC = "${BASE}driver_profile_pic.jpg"
const val INSURANCE = "${BASE}driver_insurance.jpg"
const val PRIVATE_HIRE = "${BASE}driver_license_private_hire.jpg"
const val PRIVATE_HIRE_CAR = "${BASE}driver_license_private_hire_car.jpg"
const val LOGBOOK = "${BASE}driver_logbook.jpg"
const val MOT = "${BASE}driver_mot.jpg"
const val LICENSE = "${BASE}driver_license_driver.jpg"

fun getImagePath(imageConst: String): String {
    return File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), imageConst).absolutePath
}