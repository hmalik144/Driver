package h_mal.appttude.com.driver.Global;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.UUID;

import static h_mal.appttude.com.driver.Global.ExecuteFragment.UPLOAD_NEW;
import static h_mal.appttude.com.driver.Global.ExecuteFragment.executeFragment;
import static h_mal.appttude.com.driver.Global.FirebaseClass.ARCHIVE_FIREBASE;
import static h_mal.appttude.com.driver.Global.FirebaseClass.USER_FIREBASE;
import static h_mal.appttude.com.driver.MainActivity.getDateTimeStamp;
import static h_mal.appttude.com.driver.MainActivity.mDatabase;

public class ArchiveClass {
    private static final String TAG = "ArchiveClass";

    public ArchiveClass() {
    }

    public void archiveRecord(String UID, String item, Object object) {
        final DatabaseReference toPath = mDatabase.child(USER_FIREBASE).child(UID)
                .child(ARCHIVE_FIREBASE).child(item);

        toPath.child(getDateTimeStamp()).setValue(object).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Log.i(TAG, "onComplete: archive successful");
                }else {
                    Log.i(TAG, "onComplete: archive unsuccessful");
                }
            }
        });
    }

    public void openDialogArchive(Context context,Object object, final Fragment fragment){
        if (object == null){
            executeFragment(fragment);
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Do you want to View/Edit or Upload new?")
                    .setPositiveButton("View/Edit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            executeFragment(fragment);
                        }
                    })
                    .setNegativeButton("Upload New", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Bundle bundle = new Bundle();
                            bundle.putString(UPLOAD_NEW,"Yes");
                            fragment.setArguments(bundle);
                            executeFragment(fragment);
                        }
                    })
                    .create().show();
        }

    }

    public void openDialogArchive(Context context,Object object, String user,final Fragment fragment){
        final Bundle bundle = new Bundle();
        bundle.putString("user_id",user);
        fragment.setArguments(bundle);

        if (object == null){
            executeFragment(fragment);
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Do you want to View/Edit or Upload new?")
                    .setPositiveButton("View/Edit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            executeFragment(fragment);
                        }
                    })
                    .setNegativeButton("Upload New", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            bundle.putString(UPLOAD_NEW,"Yes");
                            executeFragment(fragment);
                        }
                    })
                    .create().show();
        }

    }
}
