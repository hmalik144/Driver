package h_mal.appttude.com.driver.Global

import android.content.Context
import android.content.ContextWrapper
import android.view.View
import android.widget.ImageView
import h_mal.appttude.com.driver.R


class ImageSwiperClass(
    private val context: Context?,
    wholeView: View
) : ContextWrapper(context) {
    private var left: ImageView = wholeView.findViewById(R.id.left)
    private var right: ImageView = wholeView.findViewById(R.id.right)

    var list: List<Any>? = null

//    fun addLocalString(uris: List<Uri>) {
//        list = uris
//    }
//
//    fun addFirebaseStrings(uris: List<String>) {
//        list = uris
//    }
//
//    private var adapter: SlidingImageViewAdapter? = null
//    lateinit var viewPager: ViewPager
//
//
//    fun reinstantiateList(imageStrings: MutableList<String?>?) {
//        this.imageStrings = imageStrings
//        adapter = SlidingImageViewAdapter()
//        viewPager.adapter = adapter
//        setArrows()
//    }
//
//    fun hideDelete() {
//        delete.hide()
//    }
//
//    private val leftClick: View.OnClickListener = View.OnClickListener {
//        viewPager.currentItem = viewPager.currentItem - 1
//        setArrows()
//    }
//    private val rightClick: View.OnClickListener = View.OnClickListener {
//        viewPager.currentItem = viewPager.currentItem + 1
//        setArrows()
//    }
//
//    private fun deleteDialog(position: Int) {
//        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
//        builder.setMessage("Are you sure you want to delete?")
//            .setPositiveButton(
//                android.R.string.ok
//            ) { _, _ ->
//                imageStrings!!.removeAt(position)
//                viewPager.adapter = adapter
//                //                        adapter.notifyDataSetChanged();
//            }
//            .setNegativeButton(android.R.string.cancel, null)
//            .create()
//            .show()
//    }
//
//    private fun setArrows() {
//        with(list) {
//            when (list) {
//                isNullOrEmpty() -> { }
//                isNotEmpty() -> { }
//
//            }
//        }
//
//        if (list?.isNotEmpty()) {
//            //left arrow
//            if (viewPager.currentItem > 0) {
//                setAnimation(left, true)
//            } else {
//                setAnimation(left, false)
//            }
//
//            //right
//            if (viewPager.currentItem == imageStrings!!.size - 1) {
//                setAnimation(right, false)
//            } else {
//                setAnimation(right, true)
//            }
//        }
//        if (imageStrings == null) {
//            setAnimation(left, false)
//            setAnimation(right, false)
//        }
//    }
//
//    private fun setAnimation(view: ImageView, up: Boolean) {
//        val start: Float
//        val finish: Float
//        if (up) {
//            start = 0.2f
//            finish = 1.0f
//        } else {
//            start = 1.0f
//            finish = 0.2f
//        }
//        val animation1 = AlphaAnimation(start, finish)
//        animation1.duration = 500
//        animation1.fillAfter = true
//        view.alpha = finish
//    }
//
//    internal inner class SlidingImageViewAdapter : PagerAdapter() {
//
//        override fun getCount(): Int = list?.size ?: 0
//
//        override fun destroyItem(container: ViewGroup, position: Int, item: Any) {
//            container.removeView(item as View?)
//        }
//
//        override fun isViewFromObject(view: View, o: Any): Boolean = view == o
//
//        override fun instantiateItem(container: ViewGroup, position: Int): Any {
//            val pagerPic: View =
//                LayoutInflater.from(context).inflate(R.layout.insurance_item, container, false)
//            setArrows()
//            if (imageStrings != null && imageStrings!!.size > 0) {
//                mainImage = pagerPic.rootView.findViewById(R.id.main_image)
//                Picasso.get().load(imageStrings!!.get(position))
//                    .placeholder(R.drawable.choice_img)
//                    .into(MainActivity.loadImage(mainImage))
//            }
//            container.addView(pagerPic, 0)
//            return pagerPic
//        }
//    }
//
//
//    init {
//        left.setOnClickListener(leftClick)
//        right.setOnClickListener(rightClick)
//        viewPager = wholeView.findViewById(R.id.view_pager)
//        delete = wholeView.findViewById(R.id.delete)
//        viewPager.addOnPageChangeListener(object : OnPageChangeListener {
//            override fun onPageScrolled(i: Int, v: Float, i1: Int) {
//                val animation1: AlphaAnimation = AlphaAnimation(0.2f, 1.0f)
//                animation1.duration = 200
//                animation1.fillAfter = true
//                delete.startAnimation(animation1)
//            }
//
//            override fun onPageSelected(i: Int) {
//                setArrows()
//                delete.setOnClickListener { deleteDialog(i) }
//            }
//
//            override fun onPageScrollStateChanged(i: Int) {}
//        })
//    }
}