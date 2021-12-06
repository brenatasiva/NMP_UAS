package id.web.rpgfantasy.protectcare51

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
        val EXTRA_USERNAME = "EXTRA_USERNAME"
        var username = ""
        var checkInDate = 0
        var code = ""
        var dose = 0
        var place = ""
    }

    fun adapterUpdate(){
        viewPager.adapter = Adapter(this, fragments)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        username = intent.getStringExtra(LoginActivity.EXTRA_USERNAME).toString()

        val q = Volley.newRequestQueue(this)
        val url = "https://ubaya.fun/native/160419091/ProtectCare51/getStatus.php"
        var stringRequest = object : StringRequest(Request.Method.POST, url, Response.Listener {
            Log.d("cek", it)
            val obj = JSONObject(it)
            if(obj.getString("result") == "OK"){
                val objData = obj.getJSONObject("data")
                code = objData.getString("places_id")
                checkInDate = objData.getInt("checkin")
                dose = objData.getInt("doses")
                place = objData.getString("name")

                fragments[0] = CheckOutFragment()
                adapterUpdate()
            }
            else{
                fragments[0] = CheckInFragment()
                adapterUpdate()
            }

        },Response.ErrorListener {

        })
        {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["username"] = username
                return params
            }
        }
        q.add(stringRequest)



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