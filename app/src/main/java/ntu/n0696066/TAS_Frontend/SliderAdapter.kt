package ntu.n0696066.TAS_Frontend

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
        R.drawable.car_vector,
        R.drawable.teamwork_vector,
        R.drawable.features_vector,
        R.drawable.confirmation_vector
    )
    private var slideHeadings = arrayOf(
        "Welcome to TAS",
        "Safety",
        "Features",
        "Get Started"
    )
    private var slideDescs = arrayOf(
        "TAS is meant to be used hands-free, the app will provide visual and auditory cues ensuring" +
                " you the freedom to focus on the road ahead",
        "TAS uses a special communication link to provide you with cues to ensure your safety.",
        "Features include, forward-collision warning, headway monitoring and more through easy to" +
                "interpret images and animations.",
        "Explore what TAS has to offer"
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
        slideHeading.text = slideHeadings[position]
        slideDescription.text = slideDescs[position]

        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView( `object` as ConstraintLayout)
    }

}