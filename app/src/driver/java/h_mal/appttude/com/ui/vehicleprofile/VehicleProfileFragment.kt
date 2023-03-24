package h_mal.appttude.com.ui.vehicleprofile

import h_mal.appttude.com.base.DataSubmissionBaseFragment
import h_mal.appttude.com.databinding.FragmentVehicleSetupBinding
import h_mal.appttude.com.dialogs.DateDialog
import h_mal.appttude.com.model.VehicleProfile
import h_mal.appttude.com.utils.isTrue
import h_mal.appttude.com.viewmodels.VehicleProfileViewModel


class VehicleProfileFragment : DataSubmissionBaseFragment
<VehicleProfileViewModel, FragmentVehicleSetupBinding, VehicleProfile>() {

    override var model = VehicleProfile()

    override fun setupView(binding: FragmentVehicleSetupBinding) = binding.run {
        reg.setTextOnChange { model.reg = it }
        make.setTextOnChange { model.make = it }
        carModel.setTextOnChange { model.model = it }
        colour.setTextOnChange { model.colour = it }
        keeperName.setTextOnChange { model.keeperName = it }
        address.setTextOnChange { model.keeperAddress = it }
        postcode.setTextOnChange { model.keeperPostCode = it }
        startDate.apply {
            setOnClickListener {
                DateDialog(this) { date ->
                    model.startDate = date
                }
            }
        }
        seizedCheckbox.setOnCheckedChangeListener { _, res -> model.isSeized = res }

        submitVehicle.setOnClickListener {
            validateEditTexts(
                reg,
                make,
                carModel,
                colour,
                keeperName,
                address,
                postcode,
                startDate
            ).isTrue {
                viewModel.setDataInDatabase(model)
            }
        }
    }

    override fun setFields(data: VehicleProfile) {
        super.setFields(data)
        applyBinding {
            reg.setText(data.reg)
            make.setText(data.make)
            carModel.setText(data.model)
            colour.setText(data.colour)
            keeperName.setText(data.keeperName)
            address.setText(data.keeperAddress)
            postcode.setText(data.keeperPostCode)
            startDate.setText(data.startDate)
            seizedCheckbox.isChecked = data.isSeized
        }
    }
}