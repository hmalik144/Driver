package h_mal.appttude.com.driver.Global

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.squareup.picasso.Picasso
import h_mal.appttude.com.driver.MainActivity
import h_mal.appttude.com.driver.R
import java.util.*


class ImageSwiperClass constructor(private val context: Context?, wholeView: View?) {
    private val left: ImageView
    private var mainImage: ImageView? = null
    private val right: ImageView
    var imageStrings: MutableList<String?>? = null
        private set
    private var adapter: SlidingImageViewAdapter? = null
    var viewPager: ViewPager
    var delete: ImageView
    fun addPhotoString(s: String?) {
        if (imageStrings == null) {
            imageStrings = ArrayList()
        }
        imageStrings!!.add(s)
        adapter = SlidingImageViewAdapter()
        viewPager.adapter = adapter
    }

    fun reinstantiateList(imageStrings: MutableList<String?>?) {
        this.imageStrings = imageStrings
        adapter = SlidingImageViewAdapter()
        viewPager.adapter = adapter
        setArrows()
    }

    fun hideDelete() {
        delete.visibility = View.INVISIBLE
        Log.i(TAG, "hideDelete: hides deleete?")
    }

    private val leftClick: View.OnClickListener = object : View.OnClickListener {
        override fun onClick(v: View) {
            viewPager.currentItem = viewPager.currentItem - 1
            setArrows()
        }
    }
    private val rightClick: View.OnClickListener = object : View.OnClickListener {
        override fun onClick(v: View) {
            viewPager.currentItem = viewPager.currentItem + 1
            setArrows()
        }
    }

    private fun deleteDialog(position: Int) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setMessage("Are you sure you want to delete?")
            .setPositiveButton(android.R.string.ok, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    imageStrings!!.removeAt(position)
                    viewPager.adapter = adapter
                    //                        adapter.notifyDataSetChanged();
                }
            })
            .setNegativeButton(android.R.string.cancel, null)
            .create()
            .show()
    }

    private fun setArrows() {
        if (imageStrings != null && imageStrings!!.size > 0) {
            //left arrow
            if (viewPager.currentItem > 0) {
                setAnimation(left, true)
            } else {
                setAnimation(left, false)
            }

            //right
            if (viewPager.currentItem == imageStrings!!.size - 1) {
                setAnimation(right, false)
            } else {
                setAnimation(right, true)
            }
        }
        if (imageStrings == null) {
            setAnimation(left, false)
            setAnimation(right, false)
        }
    }

    private fun setAnimation(view: ImageView, up: Boolean) {
        val start: Float
        val finish: Float
        if (up) {
            start = 0.2f
            finish = 1.0f
        } else {
            start = 1.0f
            finish = 0.2f
        }
        //        if (view.getAlpha() != start){
        val animation1: AlphaAnimation = AlphaAnimation(start, finish)
        animation1.duration = 500
        animation1.fillAfter = true
        //            view.startAnimation(animation1);
        view.alpha = finish
        //        }
    }

    internal inner class SlidingImageViewAdapter : PagerAdapter() {
        override fun getCount(): Int {
            if (imageStrings == null || imageStrings!!.size < 1) {
                delete.visibility = View.GONE
                return 0
            } else {
                delete.visibility = View.VISIBLE
                return imageStrings!!.size
            }
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            if (`object` != null) {
                container.removeView(`object` as View?)
            } else {
                container.removeViewAt(position)
            }
        }

        override fun isViewFromObject(view: View, o: Any): Boolean {
            return (view == o)
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val pagerPic: View =
                LayoutInflater.from(context).inflate(R.layout.insurance_item, container, false)
            setArrows()
            if (imageStrings != null && imageStrings!!.size > 0) {
                mainImage = pagerPic.rootView.findViewById(R.id.main_image)
                Picasso.get().load(imageStrings!!.get(position))
                    .placeholder(R.drawable.choice_img)
                    .into(MainActivity.loadImage(mainImage))
            }
            container.addView(pagerPic, 0)
            return pagerPic
        }

        override fun notifyDataSetChanged() {
            super.notifyDataSetChanged()
        }
    }

    companion object {
        private val TAG: String = "ImageSwiperClass"
    }

    init {
        left = wholeView!!.findViewById(R.id.left)
        right = wholeView.findViewById(R.id.right)
        left.setOnClickListener(leftClick)
        right.setOnClickListener(rightClick)
        viewPager = wholeView.findViewById(R.id.view_pager)
        delete = wholeView.findViewById(R.id.delete)
        viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {
                val animation1: AlphaAnimation = AlphaAnimation(0.2f, 1.0f)
                animation1.duration = 200
                animation1.fillAfter = true
                delete.startAnimation(animation1)
            }

            override fun onPageSelected(i: Int) {
                Log.i(TAG, "onPageSelected: position = " + i)
                setArrows()
                delete.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View) {
                        deleteDialog(i)
                    }
                })
            }

            override fun onPageScrollStateChanged(i: Int) {}
        })
        Log.i(TAG, "ImageSwiperClass: viewpager = " + viewPager.id)
    }
}