package h_mal.appttude.com.driver.Global;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static android.os.Environment.DIRECTORY_PICTURES;
import static h_mal.appttude.com.driver.Global.ImageSelectorDialog.CAMERA_REQUEST;
import static h_mal.appttude.com.driver.Global.ImageSelectorDialog.PICK_IMAGE_REQUEST;
import static h_mal.appttude.com.driver.Global.ImageSelectorDialog.photoURI;
import static h_mal.appttude.com.driver.MainActivity.getDateStamp;

public class ImageSelectorResults {

    Activity activity;

    public interface FilepathResponse {
        void processFinish(Uri output);
    }

//    public FilepathResponse delegate;

    public ImageSelectorResults() {
    }

    public void Results(Activity activity, int requestCode, int resultCode, Intent data, Uri filePath,
                        ImageView imageView,FilepathResponse delegate){

        this.activity = activity;

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), filePath);
                if (imageView.getVisibility() != View.VISIBLE) {
                    imageView.setVisibility(View.VISIBLE);
                }
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally {
                if (bitmap != null){
                    delegate.processFinish(filePath);
                    Log.i(getClass().getSimpleName(), "Results: " + filePath);
                }
            }


        }

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            //check if we have we have storage rights
            int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (permission != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(activity, "Storage permissions not granted", Toast.LENGTH_SHORT).show();
                return;
            }else {

                try {
                    File f = Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES);
                    String fname = "driver"+ getDateStamp() + ".jpg";
                    File image = new File(f,fname);
                    FileOutputStream fileOutputStream = new FileOutputStream(image);

                    filePath = photoURI;

                    Bitmap bitmap = MediaStore.Images.Media
                            .getBitmap(activity.getContentResolver(), photoURI);

                    imageView.setImageBitmap(bitmap);

                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90,fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    galleryAddPic();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            delegate.processFinish(filePath);
            Log.i(getClass().getSimpleName(), "Results: " + filePath);
        }
    }

    public void Results(Activity activity, int requestCode, int resultCode, Intent data, Uri filePath,FilepathResponse delegate){

        this.activity = activity;

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), filePath);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally {
                if (bitmap != null){
                    delegate.processFinish(filePath);
                    Log.i(getClass().getSimpleName(), "Results: " + filePath);
                }
            }
        }

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            //check if we have we have storage rights
            int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (permission != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(activity, "Storage permissions not granted", Toast.LENGTH_SHORT).show();
                return;
            }else {

                try {
                    File f = Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES);
                    String fname = "driver"+ getDateStamp() + ".jpg";
                    File image = new File(f,fname);
                    FileOutputStream fileOutputStream = new FileOutputStream(image);

                    filePath = photoURI;

                    Bitmap bitmap = MediaStore.Images.Media
                            .getBitmap(activity.getContentResolver(), photoURI);


                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90,fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    galleryAddPic();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            delegate.processFinish(filePath);
            Log.i(getClass().getSimpleName(), "Results: " + filePath);

        }


    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(photoURI.getPath());
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        activity.sendBroadcast(mediaScanIntent);
    }

}
