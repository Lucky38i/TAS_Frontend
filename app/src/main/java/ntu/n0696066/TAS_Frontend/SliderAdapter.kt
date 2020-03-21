package ntu.n0696066.TAS_Frontend

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter

class SliderAdapter(private val context: Context) : PagerAdapter() {

    private lateinit var layoutInflater : LayoutInflater
    private var slideImages : IntArray = intArrayOf(
        R.drawable.traffic_icon,
        R.drawable.car_icon,
        R.drawable.explosion_icon
    )
    private var slideHeadings = arrayOf(
        "Welcome to TAS",
        "Headway Monitoring",
        "Forward Collision Warning"
    )
    private var slideDescs = arrayOf(
        "Welcome to the Traffic Avoidance System, scroll through to see the features offered",
        "This feature will make you aware if you are in eminent threat to colliding with a vehicle" +
                "that is in-front of you",
        "This feature provides you with discretionary headway with the vehicle ahead of you, " +
                "this distance can be changed within the settings"

    )

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return slideHeadings.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view : View = layoutInflater.inflate(R.layout.slide_layout, container,
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