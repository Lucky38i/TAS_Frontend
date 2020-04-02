package ntu.n0696066.tas_frontend

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.Navigation
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {

    private lateinit var mMainToolbar : Toolbar
    private lateinit var mMainDrawer : DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar_main))
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Instantiation
        mMainToolbar = findViewById(R.id.toolbar_main)
        mMainDrawer = findViewById(R.id.drawerLayoutMain)

        // Listeners
        mMainToolbar.setNavigationOnClickListener {
            mMainDrawer.open()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bottombar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.app_bar_settings) {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

}