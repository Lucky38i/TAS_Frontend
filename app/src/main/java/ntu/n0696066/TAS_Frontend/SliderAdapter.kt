package ntu.n0696066.tas_frontend

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter

class SliderAdapter(private val context: Context) : PagerAdapter() {

    private lateinit var layoutInflater : LayoutInflater
    private var slideImages : IntArray = intArrayOf(
        R.drawable.vector_car,
        R.drawable.vector_teamwork,
        R.drawable.vector_features,
        R.drawable.vector_warning,
        R.drawable.vector_confirmation
    )
    private var slideHeadings = arrayOf(
        "Welcome to TAS",
        "Safety",
        "Features",
        "Disclaimer",
        "Get Started"
    )
    private var slideDescs = arrayOf(
        "TAS is meant to be used hands-free, the app will provide visual and auditory cues ensuring" +
                " you the freedom to focus on the road ahead",
        "TAS uses a special communication link to provide you with cues to ensure your safety.",
        "Features include, forward-collision warning, headway monitoring and more through easy to" +
                "interpret images and animations.",
        "Please be warned this is a work in progress as-well as a demonstrative platform and the " +
                "apps main purpose is to demonstrate V2V and its advantages for drivers",
        "Explore what TAS has to offer"
    )

    private var contentDesc = arrayOf(
        "An image of a car",
        "An image displaying teamwork to achieve a common goal",
        "An image displaying a features framework",
        "An image with a warning symbol",
        "An image with a checkpoint outlining confirmation"
    )

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return slideHeadings.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view : View = layoutInflater.inflate(R.layout.onboard_list_item, container,
            false)

        val slideImageView : ImageView = view.findViewById(R.id.slide_image)
        val slideHeading : TextView = view.findViewById(R.id.slide_heading)
        val slideDescription : TextView = view.findViewById(R.id.slide_desc)

        slideImageView.setImageResource(slideImages[position])
        slideImageView.contentDescription = contentDesc[position]
        slideHeading.text = slideHeadings[position]
        slideDescription.text = slideDescs[position]

        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView( `object` as ConstraintLayout)
    }

}