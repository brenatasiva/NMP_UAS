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
        val username:String? = arguments?.getString("username")

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
                activity?.startActivity(Intent(activity,MainActivity::class.java))
                activity?.finish()
                }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}