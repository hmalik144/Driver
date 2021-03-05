package h_mal.appttude.com.driver.ui.driver.vehicleprofile

import android.os.Bundle
import android.view.View
import h_mal.appttude.com.driver.DataFieldsInterface
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.base.DataSubmissionBaseFragment
import h_mal.appttude.com.driver.model.VehicleProfileObject
import h_mal.appttude.com.driver.viewmodels.VehicleProfileViewModel
import kotlinx.android.synthetic.main.fragment_vehicle_setup.*


class VehicleProfileFragment :
    DataSubmissionBaseFragment<VehicleProfileViewModel, VehicleProfileObject>(),
    DataFieldsInterface {

    private val viewmodel by getFragmentViewModel<VehicleProfileViewModel>()
    override fun getViewModel(): VehicleProfileViewModel = viewmodel
    override var model = VehicleProfileObject()
    override fun getLayoutId(): Int = R.layout.fragment_vehicle_setup

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reg.setTextOnChange { model.reg = it }
        make.setTextOnChange { model.make = it }
        car_model.setTextOnChange { model.model = it }
        colour.setTextOnChange { model.colour = it }
        keeper_name.setTextOnChange { model.keeperName = it }
        address.setTextOnChange { model.keeperAddress = it }
        postcode.setTextOnChange { model.keeperPostCode = it }
        start_date.setTextOnChange { model.startDate = it }
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
        reg.setFieldFromDataFetch(data.reg)
        make.setFieldFromDataFetch(data.make)
        car_model.setFieldFromDataFetch(data.model)
        colour.setFieldFromDataFetch(data.colour)
        keeper_name.setFieldFromDataFetch(data.keeperName)
        address.setFieldFromDataFetch(data.keeperAddress)
        postcode.setFieldFromDataFetch(data.keeperPostCode)
        start_date.setFieldFromDataFetch(data.startDate)
        seized_checkbox.isChecked = data.isSeized
    }
}