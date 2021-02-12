package h_mal.appttude.com.driver.SuperUser

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import h_mal.appttude.com.driver.Archive.ArchiveFragment
import h_mal.appttude.com.driver.Driver.*
import h_mal.appttude.com.driver.Global.ExecuteFragment
import h_mal.appttude.com.driver.Global.FirebaseClass
import h_mal.appttude.com.driver.Global.SetApprovalDialog
import h_mal.appttude.com.driver.MainActivity
import h_mal.appttude.com.driver.Objects.ApprovalsObject
import h_mal.appttude.com.driver.Objects.ArchiveObject
import h_mal.appttude.com.driver.Objects.WholeDriverObject
import h_mal.appttude.com.driver.Objects.WholeObject.MappedObject
import h_mal.appttude.com.driver.R


class ApprovalListAdapter constructor(activity: Activity, objects: Array<MappedObject>) :
    ArrayAdapter<MappedObject?>(activity, 0, objects) {
    private val TAG: String = "ApprovalListAdapter"
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
    var mappedObject: MappedObject
    var activity: Activity
    var approvalCode: Int = 0
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView: View? = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(activity).inflate(
                R.layout.approval_list_grid_item, parent, false
            )
        }
        approvalCode = getApprovalStatusCode(position)
        val textView: TextView = listItemView!!.findViewById(R.id.approval_text)
        textView.text = names.get(position)
        val imageView: ImageView = listItemView.findViewById(R.id.approval_iv)
        imageView.setImageResource(
            MainActivity.approvalsClass!!.setImageResource(
                approvalCode
            )
        )
        imageView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                SetApprovalDialog(
                    approvalCode,
                    activity,
                    mappedObject.getUserId(),
                    position,
                    imageView
                )
            }
        })
        val archiveImage: ImageView = listItemView.findViewById(R.id.archive_icon)
        if (mappedObject.getWholeDriverObject().archive != null) {
            Log.i(
                TAG,
                "getView: archive = " + getArchive(
                    position,
                    mappedObject.getWholeDriverObject().getArchive()
                )
            )
            archiveImage.visibility = getArchive(
                position,
                mappedObject.getWholeDriverObject().getArchive()
            )
            archiveImage.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View) {
                    var s: String? = null
                    when (position) {
                        1 -> s = FirebaseClass.DRIVERS_LICENSE_FIREBASE
                        2 -> s = FirebaseClass.PRIVATE_HIRE_FIREBASE
                        3 -> s = FirebaseClass.VEHICLE_DETAILS_FIREBASE
                        4 -> s = FirebaseClass.INSURANCE_FIREBASE
                        5 -> s = FirebaseClass.MOT_FIREBASE
                        6 -> s = FirebaseClass.LOG_BOOK_FIREBASE
                        7 -> s = FirebaseClass.PRIVATE_HIRE_VEHICLE_LICENSE
                    }
                    ExecuteFragment.executeFragment(ArchiveFragment(), mappedObject.getUserId(), s)
                }
            })
        }
        listItemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                getFragment(position)
            }
        })
        listItemView.minimumHeight = parent.height / 4
        listItemView.setPadding(
            convertDpToPixel(9f, context).toInt(),
            convertDpToPixel(9f, context).toInt(),
            convertDpToPixel(9f, context).toInt(),
            convertDpToPixel(9f, context).toInt()
        )
        return (listItemView)
    }

    override fun getCount(): Int {
        return 8
    }

    private fun getArchive(i: Int, archiveObject: ArchiveObject?): Int {
        var o: Any? = null
        val visible: Int
        when (i) {
            0 -> {
            }
            1 -> o = archiveObject!!.driver_license
            2 -> o = archiveObject!!.private_hire
            3 -> o = archiveObject!!.vehicle_details
            4 -> o = archiveObject!!.insurance_details
            5 -> o = archiveObject!!.mot_details
            6 -> o = archiveObject!!.log_book
            7 -> o = archiveObject.private_hire_vehicle
        }
        if (o != null) {
            visible = View.VISIBLE
        } else {
            visible = View.GONE
        }
        return visible
    }

    private fun getFragment(i: Int) {
        var f: Fragment? = null
        val wholeDriverObject: WholeDriverObject? = mappedObject.getWholeDriverObject()
        var o: Any? = null
        when (i) {
            0 -> {
                f = DriverProfileFragment()
                if (wholeDriverObject!!.driver_profile != null && wholeDriverObject.getDriver_profile().driver_profile != null) {
                    o = wholeDriverObject.getDriver_profile().getDriver_profile()
                }
            }
            1 -> {
                f = DriverLicenseFragment()
                if (wholeDriverObject!!.driver_profile != null && wholeDriverObject.getDriver_profile().driver_license != null) {
                    o = wholeDriverObject.getDriver_profile().driver_license
                }
            }
            2 -> {
                f = PrivateHireLicenseFragment()
                if (wholeDriverObject!!.driver_profile != null && wholeDriverObject.getDriver_profile().private_hire != null) {
                    o = wholeDriverObject.getDriver_profile().private_hire
                }
            }
            3 -> {
                f = VehicleSetupFragment()
                if (wholeDriverObject!!.vehicle_profile != null && wholeDriverObject.getVehicle_profile().vehicle_details != null) {
                    o = wholeDriverObject.getVehicle_profile().getVehicle_details()
                }
            }
            4 -> {
                f = InsuranceFragment()
                if (wholeDriverObject!!.vehicle_profile != null && wholeDriverObject.getVehicle_profile().insurance_details != null) {
                    o = wholeDriverObject.getVehicle_profile().getInsurance_details()
                }
            }
            5 -> {
                f = MotFragment()
                if (wholeDriverObject!!.vehicle_profile != null && wholeDriverObject.getVehicle_profile().mot_details != null) {
                    o = wholeDriverObject.getVehicle_profile().getMot_details()
                }
            }
            6 -> {
                f = logbookFragment()
                if (wholeDriverObject!!.vehicle_profile != null && wholeDriverObject.getVehicle_profile().log_book != null) {
                    o = wholeDriverObject.getVehicle_profile().getLog_book()
                }
            }
            7 -> {
                f = PrivateHireVehicleFragment()
                if (wholeDriverObject!!.vehicle_profile != null && wholeDriverObject.getVehicle_profile().private_hire_vehicle != null) {
                    o = wholeDriverObject.getVehicle_profile().getPrivateHireVehicleObject()
                }
            }
        }
        if (o == null) {
            ExecuteFragment.executeFragment(f, mappedObject.getUserId())
        } else {
            MainActivity.archiveClass!!.openDialogArchive(
                context, o, mappedObject.getUserId(), f
            )
        }
    }

    private fun getApprovalStatusCode(i: Int): Int {
        var statusCode: Int = FirebaseClass.NO_DATE_PRESENT
        if (mappedObject.getWholeDriverObject().approvalsObject != null) {
            val approvalsObject: ApprovalsObject? =
                mappedObject.getWholeDriverObject().approvalsObject
            when (i) {
                0 -> if (approvalsObject!!.driver_details_approval != 0) {
                    statusCode = approvalsObject.getDriver_details_approval()
                }
                1 -> if (approvalsObject!!.driver_license_approval != 0) {
                    statusCode = approvalsObject.driver_license_approval
                }
                2 -> if (approvalsObject!!.private_hire_approval != 0) {
                    statusCode = approvalsObject.private_hire_approval
                }
                3 -> if (approvalsObject!!.vehicle_details_approval != 0) {
                    statusCode = approvalsObject.vehicle_details_approval
                }
                4 -> if (approvalsObject!!.insurance_details_approval != 0) {
                    statusCode = approvalsObject.insurance_details_approval
                }
                5 -> if (approvalsObject!!.mot_details_approval != 0) {
                    statusCode = approvalsObject.getMot_details_approval()
                }
                6 -> if (approvalsObject!!.log_book_approval != 0) {
                    statusCode = approvalsObject.getLog_book_approval()
                }
                7 -> if (approvalsObject.private_hire_vehicle_approval != 0) {
                    statusCode = approvalsObject.getPh_car_approval()
                }
            }
        }
        return statusCode
    }

    companion object {
        fun convertDpToPixel(dp: Float, context: Context): Float {
            return dp * (context.resources
                .displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        }
    }

    init {
        mappedObject = objects.get(0)
        this.activity = activity
    }
}