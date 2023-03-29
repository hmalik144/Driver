package h_mal.appttude.com.driver.archive

//import h_mal.appttude.com.driver.Global.FirebaseClass
//import h_mal.appttude.com.driver.Global.ImageSwiperClass
//import h_mal.appttude.com.driver.Objects.ArchiveObject
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import h_mal.appttude.com.driver.utils.DateUtils.convertDateStringDatePattern
import java.text.ParseException

class ArchiveObjectListAdapter(
//    var archiveObject: ArchiveObject?,
    var context: Context?,
    var archiveString: String?
) : BaseAdapter() {
    var size: Int = 0
    lateinit var mKeys: Array<String>
    private var dateArchivedText: TextView? = null
    override fun getCount(): Int {
        return size
    }

    override fun getItem(position: Int): Any? {
        when (archiveString) {
//            FirebaseClass.PRIVATE_HIRE_FIREBASE -> return archiveObject?.private_hire
//                ?.get(mKeys[position])
//            FirebaseClass.DRIVERS_LICENSE_FIREBASE -> return archiveObject?.driver_license
//                ?.get(mKeys[position])
//            FirebaseClass.VEHICLE_DETAILS_FIREBASE -> return archiveObject?.vehicle_details
//                ?.get(mKeys[position])
//            FirebaseClass.MOT_FIREBASE -> return archiveObject?.mot_details?.get(mKeys[position])
//            FirebaseClass.INSURANCE_FIREBASE -> return archiveObject?.insurance_details?.get(
//                mKeys[position]
//            )
//            FirebaseClass.LOG_BOOK_FIREBASE -> return archiveObject?.log_book
//                ?.get(mKeys.get(position))
//            FirebaseClass.PRIVATE_HIRE_VEHICLE_LICENSE -> return archiveObject?.ph_car?.get(mKeys[position])
            else -> return mKeys[position]
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        var listItemView: View = convertView
//
//        if (listItemView == null) {
//            if (((archiveString == FirebaseClass.PRIVATE_HIRE_FIREBASE) || (archiveString == FirebaseClass.DRIVERS_LICENSE_FIREBASE) || (archiveString == FirebaseClass.MOT_FIREBASE) || (archiveString == FirebaseClass.LOG_BOOK_FIREBASE) || (archiveString == FirebaseClass.PRIVATE_HIRE_VEHICLE_LICENSE))) {
//                listItemView = LayoutInflater.from(context).inflate(
//                    R.layout.archive_license_item, parent, false
//                )
//                val imageView: ImageView = listItemView.findViewById(R.id.image_archive)
//                dateArchivedText = listItemView.findViewById(R.id.date_archived)
//                val expiryHolder: LinearLayout = listItemView.findViewById(R.id.expiry_view)
//                val fieldTwo: LinearLayout = listItemView.findViewById(R.id.field_two_view)
//                val expiryText: TextView = listItemView.findViewById(R.id.exp_text)
//                val fiewTwoLable: TextView = listItemView.findViewById(R.id.field_two)
//                val fieldTwoText: TextView = listItemView.findViewById(R.id.field_two_text)
//                when (archiveString) {
////                    FirebaseClass.PRIVATE_HIRE_FIREBASE -> {
////                        expiryHolder.visibility = View.VISIBLE
////                        fieldTwo.visibility = View.VISIBLE
////                        val privateHireObject: PrivateHireObject =
////                            getItem(position) as PrivateHireObject
////                        Picasso.get().load(privateHireObject.phImageString)
////                            .placeholder(R.drawable.choice_img)
////                            .into(imageView)
////                        dateString(position)
////                        expiryText.text = privateHireObject.phExpiry
////                        fiewTwoLable.text = "Private Hire License No.:"
////                        fieldTwoText.text = privateHireObject.phNumber
////                    }
////                    FirebaseClass.DRIVERS_LICENSE_FIREBASE -> {
////                        expiryHolder.visibility = View.VISIBLE
////                        fieldTwo.visibility = View.VISIBLE
////                        val driversLicenseObject: DriversLicenseObject =
////                            getItem(position) as DriversLicenseObject
////                        Picasso.get().load(driversLicenseObject.licenseImageString)
////                            .placeholder(R.drawable.choice_img)
////                            .into(imageView)
////                        dateString(position)
////                        expiryText.text = driversLicenseObject.licenseExpiry
////                        fiewTwoLable.text = "License No.:"
////                        fieldTwoText.text = driversLicenseObject.licenseNumber
////                    }
////                    FirebaseClass.MOT_FIREBASE -> {
////                        expiryHolder.visibility = View.VISIBLE
////                        fieldTwo.visibility = View.GONE
////                        val motObject: MotObject = getItem(position) as MotObject
////                        Picasso.get().load(motObject.motImageString)
////                            .placeholder(R.drawable.choice_img)
////                            .into(imageView)
////                        dateString(position)
////                        expiryText.text = motObject.motExpiry
////                    }
////                    FirebaseClass.LOG_BOOK_FIREBASE -> {
////                        expiryHolder.visibility = View.GONE
////                        fieldTwo.visibility = View.VISIBLE
////                        val logbookObject: LogbookObject = getItem(position) as LogbookObject
////                        Picasso.get().load(logbookObject.photoString)
////                            .into(MainActivity.loadImage(imageView))
////                        dateString(position)
////                        fiewTwoLable.text = "V5C No.:"
////                        fieldTwoText.text = logbookObject.v5cnumber
////                    }
////                    FirebaseClass.PRIVATE_HIRE_VEHICLE_LICENSE -> {
////                        expiryHolder.visibility = View.VISIBLE
////                        fieldTwo.visibility = View.VISIBLE
////                        val privateHireVehicleObject: PrivateHireVehicleObject =
////                            getItem(position) as PrivateHireVehicleObject
////                        Picasso.get().load(privateHireVehicleObject.phCarImageString)
////                            .into(MainActivity.loadImage(imageView))
////                        dateString(position)
////                        expiryText.text = privateHireVehicleObject.phCarExpiry
////                        fiewTwoLable.text = "Private Hire Vehicle License No.:"
////                        fieldTwoText.text = privateHireVehicleObject.phCarNumber
////                    }
////                }
////            } else if ((archiveString == FirebaseClass.INSURANCE_FIREBASE)) {
////                listItemView = LayoutInflater.from(context).inflate(
////                    R.layout.archive_insurance_item, parent, false
////                )
////                val holder: View = listItemView.findViewById(R.id.image_pager)
////                val swiperClass: ImageSwiperClass = ImageSwiperClass(context, holder)
////                //                swiperClass.hideDelete();
////                listItemView.findViewById<View>(R.id.delete).visibility = View.GONE
////                //                holder.findViewById(R.id.delete).setVisibility(View.INVISIBLE);
////                dateArchivedText = listItemView.findViewById(R.id.date_archived)
////                dateString(position)
////                val expiryText: TextView = listItemView.findViewById(R.id.exp_text)
////                val fieldTwoText: TextView = listItemView.findViewById(R.id.archive_insurer)
////                val insuranceObject: InsuranceObject = getItem(position) as InsuranceObject
//////                swiperClass.reinstantiateList(insuranceObject.photoStrings)
////                expiryText.text = insuranceObject.expiryDate
////                fieldTwoText.text = insuranceObject.insurerName
////            } else if ((archiveString == FirebaseClass.VEHICLE_DETAILS_FIREBASE)) {
////                listItemView = LayoutInflater.from(context).inflate(
////                    R.layout.archive_vehicle_item, parent, false
////                )
////                dateArchivedText = listItemView.findViewById(R.id.date_archived)
////                dateString(position)
////                val numberPlate: TextView = listItemView.findViewById(R.id.number_plate)
////                val keeperName: TextView = listItemView.findViewById(R.id.keeper_name)
////                val keeperAddress: TextView = listItemView.findViewById(R.id.keeper_address)
////                val carText: TextView = listItemView.findViewById(R.id.car_text_arch)
////                val carColour: TextView = listItemView.findViewById(R.id.car_colour)
////                val carSeized: TextView = listItemView.findViewById(R.id.seized_checkbox)
////                val startDate: TextView = listItemView.findViewById(R.id.first_date)
////                val vehicleProfileObject: VehicleProfileObject =
////                    getItem(position) as VehicleProfileObject
////                numberPlate.text = vehicleProfileObject.reg
////                keeperName.text = vehicleProfileObject.keeperName
////                keeperAddress.text = vehicleProfileObject.keeperAddress + "\n" + vehicleProfileObject.keeperPostCode
////                carText.text = vehicleProfileObject.make + " " + vehicleProfileObject.model
////                carColour.text = vehicleProfileObject.colour
////                val s: String
////                if (vehicleProfileObject.isSeized) {
////                    s = "Yes"
////                } else {
////                    s = "No"
////                }
////                carSeized.text = s
////                startDate.text = vehicleProfileObject.startDate
////            }
////        }
        return listItemView
    }

    private fun dateString(position: Int) {
        var success: Boolean = true
        try {
            dateArchivedText!!.text =
                mKeys[position].convertDateStringDatePattern("yyyyMMdd_HHmmss", "dd/MM/yyyy")
        } catch (e: ParseException) {
            e.printStackTrace()
            success = false
        } finally {
            if (!success) {
                dateArchivedText!!.text = mKeys.get(position).substring(0, 8)
            }
        }
    }

    companion object {
        private val TAG: String = "ArchiveObjectListAdapte"
    }

//    init {
//        archiveObject?.apply {
//            val map = when (archiveString) {
//                FirebaseClass.PRIVATE_HIRE_FIREBASE -> private_hire
//                FirebaseClass.DRIVERS_LICENSE_FIREBASE -> driver_license
//                FirebaseClass.VEHICLE_DETAILS_FIREBASE -> vehicle_details
//                FirebaseClass.MOT_FIREBASE -> mot_details
//                FirebaseClass.INSURANCE_FIREBASE -> insurance_details
//                FirebaseClass.LOG_BOOK_FIREBASE -> log_book
//                FirebaseClass.PRIVATE_HIRE_VEHICLE_LICENSE -> ph_car
//                else -> null
//            }
//            setUp(map)
//        }
//
//    }

    private fun setUp(map: HashMap<String, *>?) {
        size = map?.size ?: 0
        map?.keys?.toTypedArray()?.let {
            mKeys = it
        }
    }
}