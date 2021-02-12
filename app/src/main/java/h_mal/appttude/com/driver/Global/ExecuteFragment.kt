package h_mal.appttude.com.driver.Global;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import h_mal.appttude.com.driver.R;

import static h_mal.appttude.com.driver.MainActivity.fragmentManager;

public class ExecuteFragment {

    public static final String UPLOAD_NEW = "upload_new";

    public static void executeFragment(Fragment fragment, Bundle bundle) {
        executeFragmentMethod(fragment,bundle);
    }

    public static void executeFragment(Fragment fragment) {
        executeFragmentMethod(fragment);
    }

    public static void executeFragment(Fragment fragment, String userId) {
        executeFragmentMethod(fragment,userId);
    }

    public static void executeFragment(Fragment fragment, String userId, String archive) {
        executeFragmentMethod(fragment,userId,archive);
    }

    private static void executeFragmentMethod(Fragment f){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,f).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(f.getClass().getSimpleName()).commit();
    }

    private static void executeFragmentMethod(Fragment f,String user_id){
        Bundle bundle = new Bundle();
        bundle.putString("user_id",user_id);

        f.setArguments(bundle);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,f).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(f.getClass().getSimpleName()).commit();
    }

    private static void executeFragmentMethod(Fragment f,String user_id,String archive){
        Bundle bundle = new Bundle();
        bundle.putString("user_id",user_id);
        bundle.putString("archive",archive);

        f.setArguments(bundle);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,f).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(f.getClass().getSimpleName()).commit();
    }

    private static void executeFragmentMethod(Fragment f, Bundle b){
        if (b != null){
            f.setArguments(b);
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,f).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(f.getClass().getSimpleName()).commit();
    }
}
