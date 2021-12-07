package id.web.rpgfantasy.protectcare51

import android.graphics.Color
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
import kotlinx.android.synthetic.main.fragment_check_in.*
import kotlinx.android.synthetic.main.fragment_check_out.view.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class CheckOutFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_check_out, container, false)

        view.textViewCheckOutPlace.text = MainActivity.place
        val formatter = SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm")
        var date = Date((MainActivity.checkInDate.toString()).toLong() * 1000)
        var dateCheckin = formatter.format(date)
        view.textViewCheckOutTimeCheckIn.text = "${dateCheckin}"

        if(MainActivity.dose > 1){
            view.cardViewCheckOut.setCardBackgroundColor(Color.parseColor("#8BC34A"))
        }else{
            view.cardViewCheckOut.setCardBackgroundColor(Color.parseColor("#FFEB3A"))
        }

        view.buttonCheckOut.setOnClickListener {
            val q = Volley.newRequestQueue(activity)
            val url = "https://ubaya.fun/native/160419091/ProtectCare51/checkout.php"
            val stringRequest = object : StringRequest(Request.Method.POST, url, Response.Listener {
                Log.d("cek", it)
                val obj = JSONObject(it)
                if(obj.getString("result") == "OK"){
                    MainActivity.fragments[0] = CheckInFragment()
                    (activity as MainActivity).adapterUpdate()
                }
                else{
                    Toast.makeText(activity, "ga oke", Toast.LENGTH_SHORT).show()
                }

            }, Response.ErrorListener {
                Log.d("message", it.message.toString())
            })
            {
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params["username"] = MainActivity.username
                    params["code"] = MainActivity.code
                    params["checkInDate"] = MainActivity.checkInDate.toString()
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
            CheckOutFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}