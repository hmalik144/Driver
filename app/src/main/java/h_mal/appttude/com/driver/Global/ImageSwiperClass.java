package h_mal.appttude.com.driver.Global;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import h_mal.appttude.com.driver.R;

import static h_mal.appttude.com.driver.MainActivity.imageViewClass;
import static h_mal.appttude.com.driver.MainActivity.loadImage;

public class ImageSwiperClass{

    private static final String TAG = "ImageSwiperClass";

    private ImageView left;
    private ImageView mainImage;
    private ImageView right;

    private List<String> imageStrings;
    private Context context;

    private SlidingImageViewAdapter adapter;

    ViewPager viewPager;
    public ImageView delete;

    public ImageSwiperClass(Context context, View wholeView) {
        this.context = context;

        left = wholeView.findViewById(R.id.left);
        right = wholeView.findViewById(R.id.right);

        left.setOnClickListener(leftClick);
        right.setOnClickListener(rightClick);

        viewPager = wholeView.findViewById(R.id.view_pager);

        delete = wholeView.findViewById(R.id.delete);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                AlphaAnimation animation1 = new AlphaAnimation(0.2f, 1.0f);
                animation1.setDuration(200);
                animation1.setFillAfter(true);
                delete.startAnimation(animation1);
            }

            @Override
            public void onPageSelected(final int i) {
                Log.i(TAG, "onPageSelected: position = " + i);
                setArrows();
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteDialog(i);
                    }
                });
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        Log.i(TAG, "ImageSwiperClass: viewpager = " + viewPager.getId());
    }

    public List<String> getImageStrings() {
        return imageStrings;
    }

    public void addPhotoString(String s){
        if (imageStrings == null){
            imageStrings = new ArrayList<>();
        }
        imageStrings.add(s);

        adapter = new SlidingImageViewAdapter();
        viewPager.setAdapter(adapter);
    }

    public void reinstantiateList(List<String> imageStrings){
        this.imageStrings = imageStrings;

        adapter = new SlidingImageViewAdapter();
        viewPager.setAdapter(adapter);

        setArrows();
    }

    public void hideDelete(){
        delete.setVisibility(View.INVISIBLE);
        Log.i(TAG, "hideDelete: hides deleete?");
    }

    private View.OnClickListener leftClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            setArrows();
        }
    };

    private View.OnClickListener rightClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() +1);
            setArrows();
        }
    };

    private void deleteDialog(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to delete?")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        imageStrings.remove(position);
                        viewPager.setAdapter(adapter);
//                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create()
                .show();
    }

    private void setArrows(){
        if (imageStrings != null && imageStrings.size() > 0){
            //left arrow
            if (viewPager.getCurrentItem() > 0){
                setAnimation(left,true);
            }else{
                setAnimation(left,false);
            }

            //right
            if (viewPager.getCurrentItem() == imageStrings.size() -1){
                setAnimation(right,false);
            }else{
                setAnimation(right,true);
            }
        }
        if (imageStrings == null){
            setAnimation(left,false);
            setAnimation(right,false);
        }
    }

    private void setAnimation(ImageView view,boolean up){
        float start;
        float finish;
        if(up){
            start = 0.2f;
            finish = 1.0f;
        }else {
            start = 1.0f;
            finish = 0.2f;
        }
//        if (view.getAlpha() != start){
            AlphaAnimation animation1 = new AlphaAnimation(start, finish);
            animation1.setDuration(500);
            animation1.setFillAfter(true);
//            view.startAnimation(animation1);
            view.setAlpha(finish);
//        }

    }

    class SlidingImageViewAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            if (imageStrings == null || imageStrings.size() < 1){
                delete.setVisibility(View.GONE);
                return 0;
            }else {
                delete.setVisibility(View.VISIBLE);
                return imageStrings.size();
            }

        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position,Object object) {
            if (object != null){
                container.removeView((View) object);
            }else {
                container.removeViewAt(position);
            }
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view.equals(o);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            View pagerPic = LayoutInflater.from(context).inflate(R.layout.insurance_item,container,false);
            setArrows();

            if(imageStrings != null && imageStrings.size() >0){
                mainImage = pagerPic.getRootView().findViewById(R.id.main_image);
                final ProgressBar pb = pagerPic.findViewById(R.id.pb_ins);

                Picasso.get().load(imageStrings.get(position)).into(loadImage(pb,mainImage));

            }

            container.addView(pagerPic, 0);

            return pagerPic;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }
    }
}
