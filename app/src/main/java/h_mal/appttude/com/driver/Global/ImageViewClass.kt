package h_mal.appttude.com.driver.Global;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import h_mal.appttude.com.driver.R;

import static android.os.Environment.DIRECTORY_PICTURES;
import static h_mal.appttude.com.driver.Global.ExecuteFragment.executeFragment;
import static h_mal.appttude.com.driver.Global.ImageSelectorDialog.photoURI;
import static h_mal.appttude.com.driver.MainActivity.getDateStamp;

public class ImageViewClass {

    public static final String IMAGE_VALUE = "image";
    private static Bitmap bitmap;

    public ImageViewClass() {
    }

    public void open(Bitmap bitmap){
        ImageViewClass.bitmap = bitmap;
        executeFragment(new ImageViewerFragment());
    }

    public static class ImageViewerFragment extends Fragment {

        private View view;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_image_viewer, container, false);

            FloatingActionButton fab = view.findViewById(R.id.download_pic);

            if (bitmap != null){
                PhotoView photoView = view.findViewById(R.id.photo_view);
                photoView.setImageBitmap(bitmap);

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            downloadPic();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }



            return view;
        }

        @Override
        public void onResume() {
            super.onResume();
            ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        @Override
        public void onStop() {
            super.onStop();
            ((AppCompatActivity)getActivity()).getSupportActionBar().show();
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        private void downloadPic() throws FileNotFoundException {
            File f = Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES);
            String fname = "driver"+ getDateStamp() + ".jpg";
            File image = new File(f,fname);
            FileOutputStream fileOutputStream = new FileOutputStream(image);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100,fileOutputStream);
            try {
                fileOutputStream.flush();
                fileOutputStream.close();

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(image);
                mediaScanIntent.setData(contentUri);
                getActivity().sendBroadcast(mediaScanIntent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
