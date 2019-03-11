package h_mal.appttude.com.driver.Archive;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import h_mal.appttude.com.driver.Global.ImageSwiperClass;
import h_mal.appttude.com.driver.Objects.ArchiveObject;
import h_mal.appttude.com.driver.Objects.DriverProfileObject;
import h_mal.appttude.com.driver.Objects.DriversLicenseObject;
import h_mal.appttude.com.driver.Objects.InsuranceObject;
import h_mal.appttude.com.driver.Objects.LogbookObject;
import h_mal.appttude.com.driver.Objects.MotObject;
import h_mal.appttude.com.driver.Objects.PrivateHireObject;
import h_mal.appttude.com.driver.Objects.PrivateHireVehicleObject;
import h_mal.appttude.com.driver.Objects.VehicleProfileObject;
import h_mal.appttude.com.driver.Objects.WholeObject.VehicleProfile;
import h_mal.appttude.com.driver.R;

import static h_mal.appttude.com.driver.Global.FirebaseClass.*;
import static h_mal.appttude.com.driver.MainActivity.loadImage;
import static h_mal.appttude.com.driver.MainActivity.printObjectAsJson;
import static h_mal.appttude.com.driver.MainActivity.setAsDateTime;

public class ArchiveObjectListAdapter extends BaseAdapter {

    private static String TAG = "ArchiveObjectListAdapte";

    ArchiveObject archiveObject;
    Context context;
    String archiveString;

    int size;
    String[] mKeys;

    private TextView dateArchivedText;

    public ArchiveObjectListAdapter(ArchiveObject archiveObject, Context context, String archiveString) {
        this.archiveObject = archiveObject;
        this.context = context;
        this.archiveString = archiveString;

        switch (archiveString){
            case PRIVATE_HIRE_FIREBASE:
                size = archiveObject.getPrivate_hire().size();
                mKeys = archiveObject.getPrivate_hire().keySet().toArray(new String[archiveObject.getPrivate_hire().size()]);
                break;
            case 	DRIVERS_LICENSE_FIREBASE:
                size = archiveObject.getDriver_license().size();
                mKeys = archiveObject.getDriver_license().keySet().toArray(new String[archiveObject.getDriver_license().size()]);
                break;
            case 	VEHICLE_DETAILS_FIREBASE:
                size = archiveObject.getVehicle_details().size();
                mKeys = archiveObject.getVehicle_details().keySet().toArray(new String[archiveObject.getVehicle_details().size()]);
                break;
            case 	MOT_FIREBASE:
                size = archiveObject.getMot_details().size();
                mKeys = archiveObject.getMot_details().keySet().toArray(new String[archiveObject.getMot_details().size()]);
                break;
            case 	INSURANCE_FIREBASE:
                size = archiveObject.getInsurance_details().size();
                mKeys = archiveObject.getInsurance_details().keySet().toArray(new String[archiveObject.getInsurance_details().size()]);
                break;
            case 	LOG_BOOK_FIREBASE:
                size = archiveObject.getLog_book().size();
                mKeys = archiveObject.getLog_book().keySet().toArray(new String[archiveObject.getLog_book().size()]);
                break;
            case 	PRIVATE_HIRE_VEHICLE_LICENSE:
                size = archiveObject.getPh_car().size();
                mKeys = archiveObject.getPh_car().keySet().toArray(new String[archiveObject.getPh_car().size()]);
                break;
        }


    }

    @Override
    public int getCount() {
        return size;
    }

    @Override
    public Object getItem(int position) {
        switch (archiveString){
            case PRIVATE_HIRE_FIREBASE:
                return archiveObject.getPrivate_hire().get(mKeys[position]);
            case DRIVERS_LICENSE_FIREBASE:
                return archiveObject.getDriver_license().get(mKeys[position]);
            case VEHICLE_DETAILS_FIREBASE:
                return archiveObject.getVehicle_details().get(mKeys[position]);
            case MOT_FIREBASE:
                return archiveObject.getMot_details().get(mKeys[position]);
            case INSURANCE_FIREBASE:
                return archiveObject.getInsurance_details().get(mKeys[position]);
            case LOG_BOOK_FIREBASE:
                return archiveObject.getLog_book().get(mKeys[position]);
            case PRIVATE_HIRE_VEHICLE_LICENSE:
                return archiveObject.getPh_car().get(mKeys[position]);
            default:
                return mKeys[position];
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            if (archiveString.equals(PRIVATE_HIRE_FIREBASE) ||
                    archiveString.equals(DRIVERS_LICENSE_FIREBASE) ||
                    archiveString.equals(MOT_FIREBASE) ||
                    archiveString.equals(LOG_BOOK_FIREBASE) ||
                    archiveString.equals(PRIVATE_HIRE_VEHICLE_LICENSE)){

                listItemView = LayoutInflater.from(context).inflate(
                        R.layout.archive_license_item, parent, false);

                ImageView imageView = listItemView.findViewById(R.id.image_archive);
                ProgressBar progressBar = listItemView.findViewById(R.id.prog_archive);
                dateArchivedText = listItemView.findViewById(R.id.date_archived);

                LinearLayout expiryHolder = listItemView.findViewById(R.id.expiry_view);
                LinearLayout fieldTwo = listItemView.findViewById(R.id.field_two_view);

                TextView expiryText = listItemView.findViewById(R.id.exp_text);
                TextView fiewTwoLable = listItemView.findViewById(R.id.field_two);
                TextView fieldTwoText = listItemView.findViewById(R.id.field_two_text);

                switch (archiveString){
                    case PRIVATE_HIRE_FIREBASE:
                        expiryHolder.setVisibility(View.VISIBLE);
                        fieldTwo.setVisibility(View.VISIBLE);

                        PrivateHireObject privateHireObject = (PrivateHireObject) getItem(position);

                        Picasso.get().load(privateHireObject.getPhImageString())
                                .into(loadImage(progressBar,imageView));
                        dateString(position);

                        expiryText.setText(privateHireObject.getPhExpiry());
                        fiewTwoLable.setText("Private Hire License No.:");
                        fieldTwoText.setText(privateHireObject.getPhNumber());
                        break;
                    case 	DRIVERS_LICENSE_FIREBASE:
                        expiryHolder.setVisibility(View.VISIBLE);
                        fieldTwo.setVisibility(View.VISIBLE);

                        DriversLicenseObject driversLicenseObject = (DriversLicenseObject) getItem(position);

                        Picasso.get().load(driversLicenseObject.getLicenseImageString())
                                .into(loadImage(progressBar,imageView));
                        dateString(position);

                        expiryText.setText(driversLicenseObject.getLicenseExpiry());
                        fiewTwoLable.setText("License No.:");
                        fieldTwoText.setText(driversLicenseObject.getLicenseNumber());
                        break;
                    case 	MOT_FIREBASE:
                        Log.i(TAG, "getView: MOT OBJECT");
                        expiryHolder.setVisibility(View.VISIBLE);
                        fieldTwo.setVisibility(View.GONE);

                        MotObject motObject = (MotObject) getItem(position);

                        Picasso.get().load(motObject.getMotImageString())
                                .into(loadImage(progressBar,imageView));
                        dateString(position);
                        expiryText.setText(motObject.getMotExpiry());
                        break;
                    case 	LOG_BOOK_FIREBASE:
                        expiryHolder.setVisibility(View.GONE);
                        fieldTwo.setVisibility(View.VISIBLE);

                        LogbookObject logbookObject = (LogbookObject) getItem(position);

                        Picasso.get().load(logbookObject.getPhotoString())
                                .into(loadImage(progressBar,imageView));
                        dateString(position);

                        fiewTwoLable.setText("V5C No.:");
                        fieldTwoText.setText(logbookObject.getV5cnumber());
                        break;
                    case PRIVATE_HIRE_VEHICLE_LICENSE:
                        expiryHolder.setVisibility(View.VISIBLE);
                        fieldTwo.setVisibility(View.VISIBLE);

                        PrivateHireVehicleObject privateHireVehicleObject = (PrivateHireVehicleObject) getItem(position);

                        Picasso.get().load(privateHireVehicleObject.getPhCarImageString())
                                .into(loadImage(progressBar,imageView));
                        dateString(position);

                        expiryText.setText(privateHireVehicleObject.getPhCarExpiry());
                        fiewTwoLable.setText("Private Hire Vehicle License No.:");
                        fieldTwoText.setText(privateHireVehicleObject.getPhCarNumber());
                        break;
                }


            }else if (archiveString.equals(INSURANCE_FIREBASE)){
                listItemView = LayoutInflater.from(context).inflate(
                        R.layout.archive_insurance_item, parent, false);

                View holder = listItemView.findViewById(R.id.image_pager);
                ImageSwiperClass swiperClass = new ImageSwiperClass(context,holder);
//                swiperClass.hideDelete();
                listItemView.findViewById(R.id.delete).setVisibility(View.GONE);
//                holder.findViewById(R.id.delete).setVisibility(View.INVISIBLE);

                dateArchivedText = listItemView.findViewById(R.id.date_archived);
                dateString(position);

                TextView expiryText = listItemView.findViewById(R.id.exp_text);
                TextView fieldTwoText = listItemView.findViewById(R.id.archive_insurer);

                InsuranceObject insuranceObject = (InsuranceObject) getItem(position);
                swiperClass.reinstantiateList(insuranceObject.getPhotoStrings());

                expiryText.setText(insuranceObject.getExpiryDate());
                fieldTwoText.setText(insuranceObject.getInsurerName());

            }else if(archiveString.equals(VEHICLE_DETAILS_FIREBASE)){
                listItemView = LayoutInflater.from(context).inflate(
                        R.layout.archive_vehicle_item, parent, false);

                dateArchivedText = listItemView.findViewById(R.id.date_archived);
                dateString(position);

                TextView numberPlate = listItemView.findViewById(R.id.number_plate);
                TextView keeperName = listItemView.findViewById(R.id.keeper_name);
                TextView keeperAddress = listItemView.findViewById(R.id.keeper_address);
                TextView carText = listItemView.findViewById(R.id.car_text_arch);
                TextView carColour = listItemView.findViewById(R.id.car_colour);
                TextView carSeized = listItemView.findViewById(R.id.seized);
                TextView startDate = listItemView.findViewById(R.id.first_date);

                VehicleProfileObject vehicleProfileObject = (VehicleProfileObject) getItem(position);

                numberPlate.setText(vehicleProfileObject.getReg());
                keeperName.setText(vehicleProfileObject.getKeeperName());
                keeperAddress.setText(vehicleProfileObject.getKeeperAddress() + "\n" +vehicleProfileObject.getKeeperPostCode());
                carText.setText(vehicleProfileObject.getMake() + " " + vehicleProfileObject.getModel());
                carColour.setText(vehicleProfileObject.getColour());

                String s;
                if (vehicleProfileObject.isSeized()){
                    s = "Yes";
                }else {
                    s = "No";
                }
                carSeized.setText(s);

                startDate.setText(vehicleProfileObject.getStartDate());
            }

        }

        return listItemView;
    }

    private void dateString(int position){
        boolean success = true;
        try {
            dateArchivedText.setText(setAsDateTime(mKeys[position]));
        } catch (ParseException e) {
            e.printStackTrace();
            success = false;
        }finally {
            if (!success){
                dateArchivedText.setText(mKeys[position].substring(0,8));
            }
        }
    }
}
