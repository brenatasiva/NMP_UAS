package id.web.rpgfantasy.protectcare51

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_profile.*
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    var v:View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val username:String? = MainActivity.username

        val q = Volley.newRequestQueue(activity)
        val url = "https://ubaya.fun/native/160419091/ProtectCare51/getUser.php"
        val stringRequest = object : StringRequest(Request.Method.POST, url, Response.Listener {
            Log.d("cek", it)
            val obj = JSONObject(it)
            if(obj.getString("result") == "OK"){
                val objData = obj.getJSONObject("data")
                textViewProfileName.text = "Name : " + objData.getString("name")
                textViewProfileDoses.text = "Number of vaccination doses : " + objData.getString("vaccination")
            }
            else{

            }

        },Response.ErrorListener {

        })
        {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                username?.let {
                    params["username"] = it
                }

                return params
            }
        }
        q.add(stringRequest)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_profile, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fab = v?.findViewById<FloatingActionButton>(R.id.fab)
        fab?.let {
            it.setOnClickListener {
                val username = ""
                var sharedFile = activity?.packageName
                var shared: SharedPreferences? = activity?.getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
                var editor: SharedPreferences.Editor? = shared?.edit()
                editor?.putString(LoginActivity.EXTRA_USERNAME,username)
                editor?.apply()
                GlobalData.history.clear()
                activity?.startActivity(Intent(activity,LoginActivity::class.java))
                activity?.finish()
                }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}