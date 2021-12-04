package id.web.rpgfantasy.protectcare51

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_check_in.*
import org.json.JSONObject

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
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
//        var places:ArrayList<Place> = arrayListOf()
//
//        val q = Volley.newRequestQueue(context)
//        val url = "https://ubaya.fun/native/160419091/ProtectCare51/checkin.php"
//        val stringRequest = object: StringRequest(
//            Request.Method.POST, url,
//            Response.Listener {
//                Log.d("cekparams", it)
//                val obj = JSONObject(it)
//                if(obj.getString("result") == "OK") {
//                    val data = obj.getJSONObject("data")
//                    for(i in 0 until data.length()) {
//                        val obj = data.getJSONObject(i)
//                        val place = Place(
//                            obj.getString("id"),
//                            obj.getString("name")
//                        )
//                        places.add(place)
//                    }
//                }
//            },
//            Response.ErrorListener {
//                Log.d("cekparams", it.message.toString())
//            }
//        ){
//            override fun getParams() = hashMapOf(
//                "getPlaces" to "true"
//            )
//        }
//        q.add(stringRequest)



//        val adapter = ArrayAdapter(Context, android.R.layout.simple_list_item_1, places)
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        spinner.adapter = adapter

//        buttonCheckIn.setOnClickListener {
//            val q = Volley.newRequestQueue(context)
//            val url = "https://ubaya.fun/native/160419091/ProtectCare51/checkin.php"
//            val stringRequest = object: StringRequest(
//                Request.Method.POST, url,
//                Response.Listener {
//                    Log.d("cekparams", it)
//                },
//                Response.ErrorListener {
//                    Log.d("cekparams", it.message.toString())
//                }
//            ){
//                override fun getParams() = hashMapOf(
//                    "btnCheckIn" to "true",
//                    "code" to textInputEditCode.text.toString(),
//                    "placeName" to spinner.selectedItem.toString(),
//                    "username"
//                    "checkInDate"
//                )
//            }
//            q.add(stringRequest)
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_in, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CheckInFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CheckInFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}