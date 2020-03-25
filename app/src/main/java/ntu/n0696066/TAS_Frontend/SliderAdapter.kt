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
        R.drawable.features_void
    )
    private var slideHeadings = arrayOf(
        "Welcome to TAS",
        "Features"
    )
    private var slideDescs = arrayOf(
        "Welcome to the Traffic Avoidance System, TAS is made to offer you vehicular awareness " +
                "features in order to make you a safer driver"
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