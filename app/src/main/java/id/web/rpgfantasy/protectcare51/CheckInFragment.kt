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
import android.widget.Spinner
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
        var view:View = inflater.inflate(R.layout.fragment_check_in, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = activity?.applicationContext?.let {
            ArrayAdapter(it, android.R.layout.simple_list_item_1, GlobalData.places)
        }
        adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        buttonCheckIn.setOnClickListener {
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
                    MainActivity.place = GlobalData.places[spinner.selectedItemPosition].name

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
                    params["placeName"] = GlobalData.places[spinner.selectedItemPosition].name
                    return params
                }
            }
            q.add(stringRequest)
        }
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