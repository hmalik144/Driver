package h_mal.appttude.com.driver;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static h_mal.appttude.com.driver.MainActivity.auth;
import static h_mal.appttude.com.driver.MainActivity.storageReference;

public class FirebaseClass {

    public static final String USER_FIREBASE = "user";
    public static final String DRIVER_FIREBASE = "driver_profile";
    public static final String DRIVER_DETAILS_FIREBASE = "driver_details";
    public static final String PRIVATE_HIRE_FIREBASE = "private_hire";
    public static final String DRIVERS_LICENSE_FIREBASE = "driver_license";

    public static final String VEHICLE_FIREBASE = "vehicle_profile";
    public static final String MOT_FIREBASE = "mot_details";
    public static final String VEHICLE_DETAILS_FIREBASE = "vehicle_details";
    public static final String INSURANCE_FIREBASE = "insurance_details";
    public static final String LOG_BOOK_FIREBASE = "log_book";

    Context context;
    Uri filePath;

    public interface Response {
        void processFinish(Uri output);
    }

    public Response delegate;

    public FirebaseClass(Context context, Uri filePath, Response delegate) {
        this.context = context;
        this.filePath = filePath;
        this.delegate = delegate;
    }

    public void uploadImage(String path, String name) {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference ref = storageReference.child("images/"+  auth.getCurrentUser().getUid() + "/" + path
                            + "/" + name);

            UploadTask uploadTask = ref.putFile(filePath);

            uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                            .getTotalByteCount());
                    progressDialog.setMessage("Uploaded "+(int)progress+"%");
                }
            }).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        delegate.processFinish(task.getResult());
                        progressDialog.dismiss();
                        Toast.makeText(context, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        Log.i(context.getClass().getSimpleName(), "onComplete: uploaded Successful uri: " + task.getResult());
                    } else {
                        delegate.processFinish(null);
                        progressDialog.dismiss();
                        Toast.makeText(context, "Failed to upload", Toast.LENGTH_SHORT).show();
                        Log.i(context.getClass().getSimpleName(), "onComplete: failed to get url");
                    }
                }
            });

        }
    }

}
