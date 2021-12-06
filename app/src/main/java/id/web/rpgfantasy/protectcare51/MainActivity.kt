package id.web.rpgfantasy.protectcare51

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_check_in.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {
    companion object{
        val fragments: ArrayList<Fragment> = arrayListOf(
            CheckInFragment(),
            HistoryFragment(),
            ProfileFragment()
        )
    }

    fun adapterUpdate(){
        viewPager.adapter = Adapter(this, fragments)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapterUpdate()

        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                bottomNav.selectedItemId = bottomNav.menu.getItem(position).itemId
            }
        })
        bottomNav.setOnItemSelectedListener {
            viewPager.currentItem = when(it.itemId){
                R.id.itemCheckIn -> 0
                R.id.itemHistory -> 1
                R.id.itemProfile -> 2
                else -> 0
            }
            true
        }
    }
}