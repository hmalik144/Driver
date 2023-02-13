package h_mal.appttude.com.ui.driver.vehicleprofile

import android.os.Bundle
import android.view.View

import h_mal.appttude.com.dialogs.DateDialog
import h_mal.appttude.com.R
import h_mal.appttude.com.base.DataSubmissionBaseFragment
import h_mal.appttude.com.model.VehicleProfileObject
import h_mal.appttude.com.viewmodels.VehicleProfileViewModel
import kotlinx.android.synthetic.main.fragment_vehicle_setup.*


class VehicleProfileFragment: DataSubmissionBaseFragment
<VehicleProfileViewModel, VehicleProfileObject>(R.layout.fragment_vehicle_setup){

    private val viewmodel by getFragmentViewModel<VehicleProfileViewModel>()
    override fun getViewModel(): VehicleProfileViewModel = viewmodel
    override var model = VehicleProfileObject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reg.setTextOnChange { model.reg = it }
        make.setTextOnChange { model.make = it }
        car_model.setTextOnChange { model.model = it }
        colour.setTextOnChange { model.colour = it }
        keeper_name.setTextOnChange { model.keeperName = it }
        address.setTextOnChange { model.keeperAddress = it }
        postcode.setTextOnChange { model.keeperPostCode = it }
        start_date.apply {
            setOnClickListener {
                DateDialog(this){ date ->
                    model.startDate = date
                }
            }
        }
        seized_checkbox.setOnCheckedChangeListener { _, res -> model.isSeized = res}

        submit_vehicle.setOnClickListener { submit() }
    }

    override fun submit() {
        validateEditTexts(reg, make, car_model, colour, keeper_name, address, postcode, start_date)
            .takeIf { !it }
            ?.let { return }

        viewmodel.setDataInDatabase(model)
    }

    override fun setFields(data: VehicleProfileObject) {
        super.setFields(data)
        reg.setText(data.reg)
        make.setText(data.make)
        car_model.setText(data.model)
        colour.setText(data.colour)
        keeper_name.setText(data.keeperName)
        address.setText(data.keeperAddress)
        postcode.setText(data.keeperPostCode)
        start_date.setText(data.startDate)
        seized_checkbox.isChecked = data.isSeized
    }
}