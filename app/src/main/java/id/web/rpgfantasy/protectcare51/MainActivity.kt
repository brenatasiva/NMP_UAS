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
    val fragments: ArrayList<Fragment> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val username = intent.getStringExtra(LoginActivity.EXTRA_USERNAME).toString()

        val bundle = Bundle().apply {
            putString("username",username)
        }

        val profileFragment = ProfileFragment()
        profileFragment.arguments = bundle

        fragments.add(CheckInFragment())
        fragments.add(HistoryFragment())
        fragments.add(profileFragment)

        viewPager.adapter = Adapter(this, fragments)
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

//        buttonCheckIn.setOnClickListener {
//            val q = Volley.newRequestQueue(this)
//            val url = "https://ubaya.fun/native/160419091/ProtectCare51/checkin.php"
//            val stringRequest = object : StringRequest(Request.Method.POST, url, Response.Listener {
//                Log.d("cek", it)
//                val obj = JSONObject(it)
//                if(obj.getString("result") == "OK"){
//                    startActivity(Intent(this, CheckOutActivity::class.java))
//
//                }else{
//                    Toast.makeText(this, "ga oke", Toast.LENGTH_SHORT).show()
//                }
//
//            }, Response.ErrorListener {
//                Log.d("message", it.message.toString())
//            })
//            {
//                override fun getParams(): MutableMap<String, String> {
//                    val params = HashMap<String, String>()
//                    params["btnCheckIn"] = "true"
//                    params["username"] = "brenatasiva"
//                    params["code"] = textInputEditCode.text.toString()
//                    params["placeName"] = "Ubaya"
//                    params["checkInDate"] = SimpleDateFormat("dd MMMM yyyy hh:mm").format(Date()).toString()
//                    return params
//                }
//            }
//            q.add(stringRequest)
//        }
    }
}