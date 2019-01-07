package h_mal.appttude.com.driver;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.support.v4.app.ActivityCompat.requestPermissions;
import static android.support.v4.app.ActivityCompat.startActivityForResult;
import static android.support.v4.content.PermissionChecker.checkSelfPermission;
import static h_mal.appttude.com.driver.MainActivity.fragmentManager;
import static h_mal.appttude.com.driver.MainActivity.verifyStoragePermissions;

public class ImageSelectorDialog extends Dialog{

    private String TAG = this.getClass().getSimpleName();
    public static final int PICK_IMAGE_REQUEST = 71;
    public static final int CAMERA_REQUEST = 1888;
    public static final int MY_CAMERA_PERMISSION_CODE = 100;

    private String saveFileName;
    public static Uri photoURI;

    Fragment fragment;

    public ImageSelectorDialog(@NonNull Context context) {
        super(context);

        this.saveFileName = "default_name";
    }

    public ImageSelectorDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.saveFileName = "default_name";
    }

    protected ImageSelectorDialog(@NonNull Context context, boolean cancelable, @Nullable DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.saveFileName = "default_name";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_dialog);

        //check if we have we have storage rights
        int permission = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "Storage permissions not granted", Toast.LENGTH_SHORT).show();
        }else {

            fragment = fragmentManager.getFragments().get(0);

            Button upload = findViewById(R.id.upload);
            Button takePic = findViewById(R.id.take_pic);

            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chooseImage();
                    dismiss();
                }
            });

            takePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (
                            checkSelfPermission(fragment.getActivity(), Manifest.permission.CAMERA)
                                    != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(fragment.getActivity(), new String[]{Manifest.permission.CAMERA},
                                MY_CAMERA_PERMISSION_CODE);
                    } else {
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        File file = createFile();
                        photoURI = Uri.fromFile(file);
                        Uri imageUri = FileProvider.getUriForFile(
                                getContext(),
                                "h_mal.appttude.com.driver",
                                file);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        fragment.startActivityForResult(cameraIntent, CAMERA_REQUEST);

                    }

                    dismiss();
                }
            });
        }

    }

    public void setImageName(String saveFileName){
        this.saveFileName = saveFileName;
    }

    private File createFile(){
        //create directory
        File root = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //create file
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmm");
        String currentDateandTime = sdf.format(new Date());
        String fname = saveFileName+ currentDateandTime;
        File image = new File(root,fname);

        return image;

    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        fragment.startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }

}
