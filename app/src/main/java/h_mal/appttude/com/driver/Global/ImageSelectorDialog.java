package h_mal.appttude.com.driver.Global;

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
import android.support.v7.app.AppCompatActivity;
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

import h_mal.appttude.com.driver.R;

import static android.support.v4.app.ActivityCompat.getPermissionCompatDelegate;
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
    public static final int STORAGE_PERMISSION_CODE = 101;

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
        final int permissionPic = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        final int permissionCam = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);

            fragment = fragmentManager.getFragments().get(0);

            Button upload = findViewById(R.id.upload);
            Button takePic = findViewById(R.id.take_pic);

            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (permissionPic == PackageManager.PERMISSION_GRANTED){
                        chooseImage();

                    }else {
                        Toast.makeText(getContext(), "Storage permissions required", Toast.LENGTH_SHORT).show();
                        requestPermissions(fragment.getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                STORAGE_PERMISSION_CODE);
                    }
                    dismiss();
                }
            });

            takePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (permissionCam == PackageManager.PERMISSION_GRANTED){
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        File file = createFile();
                        photoURI = Uri.fromFile(file);
                        Uri imageUri = FileProvider.getUriForFile(
                                getContext(),
                                "h_mal.appttude.com.driver",
                                file);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        fragment.startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }else {
                        Toast.makeText(getContext(), "Camera Permissions required", Toast.LENGTH_SHORT).show();
                        requestPermissions(fragment.getActivity(), new String[]{Manifest.permission.CAMERA},
                                MY_CAMERA_PERMISSION_CODE);
                    }
                    dismiss();
                }
            });

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
