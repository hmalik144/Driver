package h_mal.appttude.com.driver.Global;

import android.app.Activity;
import android.view.View;

public class ViewController {

    private Activity activity;

    public ViewController(Activity activity) {
        this.activity = activity;
    }

    public void progress(int vis){
        if (activity instanceof ViewControllerInterface){
            ((ViewControllerInterface) activity).progressVisibility(vis);
        }
    }

    public void reloadDrawer(){
        if (activity instanceof ViewControllerInterface){
            ((ViewControllerInterface) activity).updateDrawer();
        }
    }

    public interface ViewControllerInterface {

        void progressVisibility(int vis);

        void updateDrawer();
    }
}
