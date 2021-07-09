package h_mal.appttude.com.driver.SuperUser

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import h_mal.appttude.com.driver.Global.FirebaseClass
import h_mal.appttude.com.driver.Global.SetApprovalDialog
import h_mal.appttude.com.driver.ui.driver.MainActivity
import h_mal.appttude.com.driver.Objects.ArchiveObject
import h_mal.appttude.com.driver.Objects.WholeObject.MappedObject
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.ui.driver.vehicleprofile.InsuranceFragment
import h_mal.appttude.com.driver.ui.driver.driverprofile.DriverLicenseFragment
import h_mal.appttude.com.driver.ui.driver.driverprofile.DriverProfileFragment
import h_mal.appttude.com.driver.ui.driver.driverprofile.PrivateHireLicenseFragment
import h_mal.appttude.com.driver.ui.driver.vehicleprofile.LogbookFragment
import h_mal.appttude.com.driver.ui.driver.vehicleprofile.MotFragment
import h_mal.appttude.com.driver.ui.driver.vehicleprofile.PrivateHireVehicleFragment
import h_mal.appttude.com.driver.ui.driver.vehicleprofile.VehicleProfileFragment


class ApprovalListAdapter(
    val activity: Activity,
    objects: Array<MappedObject>
): ArrayAdapter<MappedObject?>(activity, 0, objects) {

    var mappedObject: MappedObject = objects[0]

    var names: Array<String> = arrayOf(
        "Driver Profile",
        "Driver License",
        "Private Hire",
        "Vehicle Profile",
        "Insurance",
        "MOT",
        "Logbook",
        "P/H Vehicle"
    )


    var approvalCode: Int = 0
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView: View? = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(activity).inflate(
                R.layout.approval_list_grid_item, parent, false
            )
        }
//        approvalCode = getApprovalStatusCode(position)
//        val textView: TextView = listItemView!!.findViewById(R.id.approval_text)
//        textView.text = names.get(position)
//        val imageView: ImageView = listItemView.findViewById(R.id.approval_iv)
//        imageView.setImageResource(
//            MainActivity.approvalsClass!!.setImageResource(
//                approvalCode
//            )
//        )
//        imageView.setOnClickListener {
//            SetApprovalDialog(
//                approvalCode,
//                activity,
//                mappedObject.userId,
//                position,
//                imageView
//            )
//        }
//        val archiveImage: ImageView = listItemView.findViewById(R.id.archive_icon)
//        mappedObject.wholeDriverObject?.archive?.let {
//
//
//            archiveImage.visibility = getArchive(
//                position,
//                it
//            )
//            archiveImage.setOnClickListener {
//                var s: String? = null
//                when (position) {
//                    1 -> s = FirebaseClass.DRIVERS_LICENSE_FIREBASE
//                    2 -> s = FirebaseClass.PRIVATE_HIRE_FIREBASE
//                    3 -> s = FirebaseClass.VEHICLE_DETAILS_FIREBASE
//                    4 -> s = FirebaseClass.INSURANCE_FIREBASE
//                    5 -> s = FirebaseClass.MOT_FIREBASE
//                    6 -> s = FirebaseClass.LOG_BOOK_FIREBASE
//                    7 -> s = FirebaseClass.PRIVATE_HIRE_VEHICLE_LICENSE
//                }
////                executeFragment(ArchiveFragment(), mappedObject.userId, s)
//            }
//        }
//        listItemView.setOnClickListener(View.OnClickListener { getFragment(position) })
//        listItemView.minimumHeight = parent.height / 4
//        listItemView.setPadding(
//            convertDpToPixel(9f, context).toInt(),
//            convertDpToPixel(9f, context).toInt(),
//            convertDpToPixel(9f, context).toInt(),
//            convertDpToPixel(9f, context).toInt()
//        )
        return (listItemView)!!
    }

//    override fun getCount(): Int {
//        return 8
//    }
//
//    private fun getArchive(i: Int, archiveObject: ArchiveObject?): Int {
//        var o: Any? = null
//        val visible: Int
//        when (i) {
//            0 -> { }
//            1 -> o = archiveObject!!.driver_license
//            2 -> o = archiveObject!!.private_hire
//            3 -> o = archiveObject!!.vehicle_details
//            4 -> o = archiveObject!!.insurance_details
//            5 -> o = archiveObject!!.mot_details
//            6 -> o = archiveObject!!.log_book
//            7 -> o = archiveObject!!.ph_car
//        }
//        if (o != null) {
//            visible = View.VISIBLE
//        } else {
//            visible = View.GONE
//        }
//        return visible
//    }
//
//    private fun getFragment(i: Int) {
//        lateinit var f: Fragment
//        val driverProfile by lazy { mappedObject.wholeDriverObject?.driver_profile }
//        val vehicleProfile by lazy { mappedObject.wholeDriverObject?.vehicle_profile }
//        val o: Any? = when (i) {
//            0 -> {
//                f = DriverProfileFragment()
//                driverProfile?.driver_profile
//            }
//            1 -> {
//                f = DriverLicenseFragment()
//                driverProfile?.driver_license
//            }
//            2 -> {
//                f = PrivateHireLicenseFragment()
//                driverProfile?.private_hire
//            }
//            3 -> {
//                f = VehicleProfileFragment()
//                vehicleProfile?.vehicle_details
//            }
//            4 -> {
//                f = InsuranceFragment()
//                vehicleProfile?.insurance_details
//            }
//            5 -> {
//                f = MotFragment()
//                vehicleProfile?.insurance_details
//            }
//            6 -> {
//                f = LogbookFragment()
//                vehicleProfile?.log_book
//            }
//            7 -> {
//                f = PrivateHireVehicleFragment()
//                vehicleProfile?.privateHireVehicleObject
//            }
//            else -> null
//        }
//        if (o == null) {
////            executeFragment(f, mappedObject.userId)
//        } else {
//            MainActivity.archiveClass.openDialogArchive(
//                context, o, mappedObject.userId, f
//            )
//        }
//    }
//
//    private fun getApprovalStatusCode(i: Int): Int {
//        val statusCode = mappedObject.wholeDriverObject?.approvalsObject?.let{
//                when (i) {
//                0 -> it.driver_details_approval
//                1 -> it.driver_license_approval
//                2 -> it.private_hire_approval
//                3 -> it.vehicle_details_approval
//                4 -> it.insurance_details_approval
//                5 -> it.mot_details_approval
//                6 -> it.log_book_approval
//                7 -> it.ph_car_approval
//                else -> FirebaseClass.NO_DATE_PRESENT
//            }
//        }
//        return statusCode ?: FirebaseClass.NO_DATE_PRESENT
//    }
//
//    companion object {
//        fun convertDpToPixel(dp: Float, context: Context): Float {
//            return dp * (context.resources
//                .displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
//        }
//    }


}