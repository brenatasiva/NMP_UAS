package id.web.rpgfantasy.protectcare51

import android.content.Context
import android.content.Intent
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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CheckInFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CheckInFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_check_in, container, false)

        var places:ArrayList<Place> = arrayListOf()

        val q = Volley.newRequestQueue(activity)
        val url = "https://ubaya.fun/native/160419091/ProtectCare51/checkin.php"
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
                params["getPlaces"] = "true"
                return params
            }
        }
        q.add(stringRequest)

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
                    params["btnCheckIn"] = "true"
                    params["username"] = "brenatasiva"
                    params["code"] = textInputEditCode.text.toString()
                    params["placeName"] = "Ubaya"
                    params["checkInDate"] = SimpleDateFormat("dd MMMM yyyy hh:mm").format(Date()).toString()
                    return params
                }
            }
            q.add(stringRequest)
        }

        return view
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CheckInFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}