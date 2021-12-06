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
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_check_in.*
import kotlinx.android.synthetic.main.fragment_check_in.view.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class CheckInFragment : Fragment() {
    private var places:ArrayList<Place> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }


        val q = Volley.newRequestQueue(activity)
        val url = "https://ubaya.fun/native/160419091/ProtectCare51/getPlaces.php"
        var stringRequest = object : StringRequest(Request.Method.POST, url, Response.Listener {
            Log.d("cek", it)
            val obj = JSONObject(it)
            if(obj.getString("result") == "OK"){
                val objData = obj.getJSONArray("data")
                for(i in 0 until objData.length()) {
                    val obj = objData.getJSONObject(i)
                    val place = Place(
                        obj.getString("id"),
                        obj.getString("name")
                    )
                    places.add(place)
                }
            }
            else{

            }

        },Response.ErrorListener {

        })
        {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()

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
        val view:View = inflater.inflate(R.layout.fragment_check_in, container, false)

//        val adapter = activity?.applicationContext?.let {
//            ArrayAdapter(it, android.R.layout.simple_list_item_1,places).setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        }
//        spinner.adapter = adapter

        view.buttonCheckIn.setOnClickListener {
            val q = Volley.newRequestQueue(activity)
            val url = "https://ubaya.fun/native/160419091/ProtectCare51/checkin.php"
            val stringRequest = object : StringRequest(Request.Method.POST, url, Response.Listener {
                Log.d("cek", it)
                val obj = JSONObject(it)
                if(obj.getString("result") == "OK"){
                    val objData = obj.getJSONObject("data")
                    MainActivity.code = textInputEditCode.text.toString()
                    MainActivity.checkInDate = objData.getInt("checkin")
                    MainActivity.dose = objData.getInt("doses")
                    MainActivity.place = "Ubaya"

                    MainActivity.fragments[0] = CheckOutFragment()
                    (activity as MainActivity).adapterUpdate()
                }
                else{
                    Toast.makeText(activity, "ga oke", Toast.LENGTH_SHORT).show()
                }

            },Response.ErrorListener {
                Log.d("message", it.message.toString())
            })
            {
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params["username"] = MainActivity.username
                    params["code"] = textInputEditCode.text.toString()
                    params["placeName"] = "Ubaya"
                    return params
                }
            }
            q.add(stringRequest)
        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CheckInFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}