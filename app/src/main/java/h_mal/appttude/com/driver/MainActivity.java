package h_mal.appttude.com.driver;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import h_mal.appttude.com.driver.Driver.DriverOverallFragment;
import h_mal.appttude.com.driver.Driver.VehicleOverallFragment;
import h_mal.appttude.com.driver.Driver.VehicleSetupFragment;
import h_mal.appttude.com.driver.Driver.DriverProfileFragment;
import h_mal.appttude.com.driver.Driver.homeDriverFragment;
import h_mal.appttude.com.driver.Global.ApprovalsClass;
import h_mal.appttude.com.driver.Global.ArchiveClass;
import h_mal.appttude.com.driver.Global.ImageViewClass;
import h_mal.appttude.com.driver.Global.ViewController;
import h_mal.appttude.com.driver.SuperUser.homeSuperUserFragment;
import h_mal.appttude.com.driver.User.LoginActivity;
import h_mal.appttude.com.driver.User.profileFragment;

import static h_mal.appttude.com.driver.Global.ExecuteFragment.executeFragment;
import static h_mal.appttude.com.driver.Global.FirebaseClass.USER_FIREBASE;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ViewController.ViewControllerInterface {

    private static String TAG = MainActivity.class.getSimpleName();

    public static FragmentManager fragmentManager;
    public static FirebaseAuth auth;
    public static FirebaseStorage storage;
    public static StorageReference storageReference;
    public static DatabaseReference mDatabase;

    public NavigationView navigationView;
    ProgressBar progressBar;
    public Toolbar toolbar;

    public static ViewController viewController;
    public static ImageViewClass imageViewClass;
    public static ApprovalsClass approvalsClass;
    public static ArchiveClass archiveClass;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewController = new ViewController(this);
        imageViewClass = new ImageViewClass();
        approvalsClass = new ApprovalsClass();
        archiveClass = new ArchiveClass();

        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        DatabaseReference ref = mDatabase.child(USER_FIREBASE)
                .child(auth.getCurrentUser().getUid())
                .child("role");


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setupDrawer();

        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(backStackChangedListener);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String role = (String) dataSnapshot.getValue();
                Log.i(TAG, "onDataChange: " + role);
                if (role.equals("driver")){
                    executeFragment(new homeDriverFragment());

                }else if(role.equals("super_user")){
                    executeFragment(new homeSuperUserFragment());
                }
                drawerMenuItems(role);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public FragmentManager.OnBackStackChangedListener backStackChangedListener= new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            String fragmentString = fragmentManager.getFragments().get(0).getClass().getSimpleName();
            String title;

            switch (fragmentString){
                case "DriverProfileFragment":
                    title = "Driver Profile";
                break;
                case "DriverLicenseFragment":
                    title = "Drivers License";
                    break;
                case "InsuranceFragment":
                    title = "Insurance";
                    break;
                case "logbookFragment":
                    title = "Logbook";
                    break;
                case "MotFragment":
                    title = "M.O.T";
                    break;
                case "PrivateHireLicenseFragment":
                    title = "Private Hire License";
                    break;
                case "VehicleSetupFragment":
                    title = "Vehicle Profile";
                    break;
                case "UserMainFragment":
                    return;
                case "ArchiveFragment":
                    return;
                default:
                    title = getResources().getString(R.string.app_name);
            }

            setTitle(title);
        }
    };

    @Override
    public void setTitle(CharSequence title) {
//        super.setTitle(title);

        toolbar.setTitle(title);
    }

    public void drawerMenuItems(String s){
        if (s.equals("super_user")){
            Menu menu = navigationView.getMenu();
            menu.removeGroup(R.id.menu_group);
        }
    }

    public void setupDrawer(){
        View header = navigationView.getHeaderView(0);

        TextView driverEmail = header.findViewById(R.id.driver_email);
        TextView driverName = header.findViewById(R.id.driver_name);
        ImageView driverImage = header.findViewById(R.id.profileImage);

        if (auth != null){
            FirebaseUser user = auth.getCurrentUser();
            if (user.getEmail() != null){
                driverEmail.setText(user.getEmail());
            }
            if (user.getDisplayName() != null){
                driverName.setText(user.getDisplayName());
            }

            Picasso.get()
                    .load(user.getPhotoUrl())
                    .placeholder(R.drawable.choice_img_round)
                    .into(driverImage);
        }

        TextView textView = findViewById(R.id.logout);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (fragmentManager.getBackStackEntryCount() > 1) {
                if (fragmentManager.getFragments().get(0).getClass()
                        .getSimpleName().equals("InsuranceFragment")){
                    new AlertDialog.Builder(this)
                            .setTitle("Return to previous?")
                            .setMessage("Progress unsaved. Are you sure?")
                            .setNegativeButton(android.R.string.no, null)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface arg0, int arg1) {
                                    fragmentManager.popBackStack();
                                }
                            }).create().show();
                }else{
                    fragmentManager.popBackStack();
                }

            }else{
                new AlertDialog.Builder(this)
                        .setTitle("Leave?")
                        .setMessage("Are you sure you want to exit?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                finish();
                                System.exit(0);
                            }
                        }).create().show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            executeFragment(new profileFragment());
        } else if (id == R.id.nav_gallery) {
            executeFragment(new DriverOverallFragment());
        } else if (id == R.id.nav_slideshow) {
            executeFragment(new VehicleOverallFragment());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public static Target loadImage (final ProgressBar pb, final ImageView mainImage){

        Target target =  new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                pb.setVisibility(View.GONE);

                mainImage.setImageBitmap(bitmap);
                mainImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageViewClass.open(bitmap);
                    }
                });
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                pb.setVisibility(View.GONE);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                pb.setVisibility(View.VISIBLE);
            }
        };
        mainImage.setTag(target);

        return target;

    }

    public static String getDateStamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmm");
        return sdf.format(new Date());
    }

    public static String getDateTimeStamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return sdf.format(new Date());
    }

    public static String setAsDateTime(String strCurrentDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date newDate = format.parse(strCurrentDate);

        format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(newDate);
    }

    public static void printObjectAsJson(String TAG, Object o){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(new Gson().toJson(o));
        String prettyJsonString = gson.toJson(je);

        Log.i(TAG, "onBindViewHolder: object" + prettyJsonString);
    }

    @Override
    public void progressVisibility(int vis) {
        progressBar.setVisibility(vis);
    }

    @Override
    public void updateDrawer() {
        setupDrawer();
    }
}
