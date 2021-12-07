package id.web.rpgfantasy.protectcare51

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_history.*
import org.json.JSONObject
import kotlinx.android.synthetic.main.fragment_history.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [HistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryFragment : Fragment() {
    var histories:ArrayList<History> = GlobalData.history
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalData.history.clear()
        val q = Volley.newRequestQueue(activity)
        val url = "https://ubaya.fun/native/160419091/ProtectCare51/getHistory.php"
        var stringRequest = object : StringRequest(
            Request.Method.POST,url,
            Response.Listener {
                Log.d("ApiResult",it)
                val obj = JSONObject(it)
                if(obj.getString("result") == "OK")
                {
                    val data =obj.getJSONArray("data")
                    for (i in 0 until data.length()){
                        val histObj = data.getJSONObject(i)
                        val history = History(
                            histObj.getInt("id"),
                            histObj.getString("location"),
                            histObj.getString("checkin"),
                            histObj.getString("checkout"),
                            histObj.getInt("doses"),
                        )
                        histories.add(history)
                    }
                    updateList()
                    Log.d("CekisiArray",histories.toString())
                }
            },
            Response.ErrorListener {
                Log.d("ApiResult", it.message.toString())
            }
        ){
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["username"] = MainActivity.username
                return params
            }
        }
        q.add(stringRequest)
    }

    fun updateList(){
        val lm: LinearLayoutManager = LinearLayoutManager(activity)
            view?.historyRecyclerView?.let {
            it.layoutManager = lm
            it.setHasFixedSize(true)
            it.adapter = HistoryAdapter()
        }
    }

    override fun onResume() {
        super.onResume()
        historyRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HistoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}