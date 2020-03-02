package ie.swcc.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Callback
import ie.swcc.R
import ie.swcc.fragments.*
import ie.swcc.main.SWCCApp
import ie.swcc.utils.uploadImageView
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.home.*
import kotlinx.android.synthetic.main.nav_header_home.view.*
import org.jetbrains.anko.toast
import org.jetbrains.anko.startActivity

import com.squareup.picasso.Picasso
import ie.swcc.utils.readImageUri
import ie.swcc.utils.writeImageRef
import jp.wasabeef.picasso.transformations.CropCircleTransformation

class Home : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    lateinit var ft: FragmentTransaction
    lateinit var app: SWCCApp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        setSupportActionBar(toolbar)
        app = application as SWCCApp


        navView.setNavigationItemSelectedListener(this)
        navView.getHeaderView(0).nav_header_email.text = app.auth.currentUser?.email


        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        ft = supportFragmentManager.beginTransaction()

        val fragment = BlogFragment.newInstance()
        ft.replace(R.id.homeFrame, fragment)
        ft.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_blog -> navigateTo(BlogFragment.newInstance())
            R.id.nav_bloglist -> navigateTo(ReportFragment.newInstance())
            R.id.nav_chat -> navigateTo(ChatFragment.newInstance())
            R.id.nav_report -> navigateTo(StravaFragment.newInstance())
            R.id.nav_news -> navigateTo(NewsFragment.newInstance())
            R.id.nav_sign_out -> signOut()

            else -> toast("You Selected Something Else")
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.action_report -> toast("You Selected Report")

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
         else
            super.onBackPressed()
    }

    private fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.homeFrame, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun signOut()
    {
        app.auth.signOut()
        startActivity<Login>()
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> {
                if (data != null) {
                    writeImageRef(app,readImageUri(resultCode, data).toString())
                    Picasso.get().load(readImageUri(resultCode, data).toString())
                        .resize(180, 180)
                        .transform(CropCircleTransformation())
                        .into(navView.getHeaderView(0).imageView, object : Callback {
                            override fun onSuccess() {
                                // Drawable is ready
                                uploadImageView(app,navView.getHeaderView(0).imageView)
                            }
                            override fun onError(e: Exception) {}
                        })
                }
            }
        }
    }
}
