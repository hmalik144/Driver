package h_mal.appttude.com.driver.SuperUser;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.List;

import h_mal.appttude.com.driver.Objects.UserObject;
import h_mal.appttude.com.driver.Objects.WholeObject.MappedObject;
import h_mal.appttude.com.driver.R;

import static h_mal.appttude.com.driver.Global.ExecuteFragment.executeFragment;
import static h_mal.appttude.com.driver.Global.FirebaseClass.DRIVER_NUMBER;
import static h_mal.appttude.com.driver.Global.FirebaseClass.USER_FIREBASE;
import static h_mal.appttude.com.driver.MainActivity.approvalsClass;
import static h_mal.appttude.com.driver.MainActivity.imageViewClass;
import static h_mal.appttude.com.driver.MainActivity.loadImage;
import static h_mal.appttude.com.driver.MainActivity.mDatabase;

public class ListViewSuperAdapter extends ArrayAdapter<MappedObject> {

    public ListViewSuperAdapter(@NonNull Context context, @NonNull List<MappedObject> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int i, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_layout, parent, false);


        }

        Log.i("getviewposition", "getView: pos = " + i);

        final ImageView profilePicImage = listItemView.findViewById(R.id.driverPic);
        final ProgressBar progressBar = listItemView.findViewById(R.id.pb_su_list);
        TextView userNameTextView = listItemView.findViewById(R.id.username_text);
        TextView userEmailTextView = listItemView.findViewById(R.id.emailaddress_text);
        ImageView profileApprovalImage = listItemView.findViewById(R.id.approval_iv);
        final TextView driverNo = listItemView.findViewById(R.id.driver_no);
        final MappedObject mappedObject = getItem(i);

        final UserObject object = mappedObject.getWholeDriverObject().getUser_details();

        if (object.profilePicString != null){
                Picasso.get()
                        .load(object.getProfilePicString())
                        .resize(128,128)
                        .placeholder(R.drawable.choice_img_round)
                        .into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                progressBar.setVisibility(View.GONE);
                                profilePicImage.setImageBitmap(bitmap);

                            }

                            @Override
                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {
                                progressBar.setVisibility(View.VISIBLE);
                            }
                        });

        }else {
            profilePicImage.setImageResource(R.drawable.choice_img_round);
        }

        userNameTextView.setText(object.getProfileName());
        userEmailTextView.setText(object.getProfileEmail());
        if (mappedObject.getWholeDriverObject().driver_number == null){
            driverNo.setText("0");
        }else {
            String s = String.valueOf(mappedObject.getWholeDriverObject().getDriver_number());
            driverNo.setText(s);
        }


        driverNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final EditText input = new EditText(getContext());
                LinearLayout layout = new LinearLayout(getContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(28,0,56,0);
                input.setText(driverNo.getText().toString());
                input.setSelectAllOnFocus(true);
                layout.addView(input);
                builder.setTitle("Change Driver Number")
                        .setView(layout)
                        .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(final DialogInterface dialog, int which) {
                                        DatabaseReference reference = mDatabase.child(USER_FIREBASE).child(mappedObject.getUserId())
                                                .child(DRIVER_NUMBER);
                                        Log.i("Dialog Driver no", "onClick: " + reference.toString());

                                        reference.setValue(input.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    Toast.makeText(getContext(), "Driver Number Changed", Toast.LENGTH_SHORT).show();
                                                    dialog.dismiss();
                                                    notifyDataSetChanged();
                                                    Log.i("Dialog Driver no", "onComplete: " + task.getResult());
                                                }else {
                                                    Log.e("Dialog Driver no", "onComplete: ", task.getException());
                                                }

                                            }
                                        });
                                    }
                                }
                        ).create()
                        .show();
            }
        });

        profileApprovalImage.setImageResource(
                approvalsClass.getOverApprovalStatusCode(mappedObject.getWholeDriverObject()));


        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("mapped",mappedObject);

                executeFragment(new UserMainFragment(),bundle);
            }
        });

        return listItemView;
    }

    private boolean hasImage(@NonNull ImageView view) {
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable != null);

        if (hasImage && (drawable instanceof BitmapDrawable)) {
            hasImage = ((BitmapDrawable)drawable).getBitmap() != null;
        }

        return hasImage;
    }
}
