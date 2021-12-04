package id.web.rpgfantasy.protectcare51

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject


class LoginActivity : AppCompatActivity()
{
    val USERNAME = "USERNAME"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //retrive saved History
//        var sharedFile = packageName
//        var shared: SharedPreferences = getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
//        var savedUsername = shared.getString(USERNAME, "")

        buttonLogin.setOnClickListener {
            val q = Volley.newRequestQueue(this)
            val url = "https://ubaya.fun/native/160419091/ProtectCare51/getUser.php"
            val stringRequest = object : StringRequest(Request.Method.POST, url, Response.Listener {
                Log.d("cek", it)
                val obj = JSONObject(it)
                if(obj.getString("result") == "OK"){
                    val objData = obj.getJSONObject("data")
                    val username = objData.getString("username")
//                    var editor: SharedPreferences.Editor = shared.edit()
//                    editor.putString(USERNAME,username)
//                    editor.apply()
                    startActivity(Intent(this, MainActivity::class.java))
                }
                else{
                    Toast.makeText(this, "Username or password is wrong!", Toast.LENGTH_SHORT).show()
                }

            },Response.ErrorListener {
                Toast.makeText(this, "${it.message.toString()}", Toast.LENGTH_SHORT).show()
            })
            {
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params["username"] = txtUsername.text.toString()
                    params["password"] = txtPassword.text.toString()
                    return params
                }
            }
            q.add(stringRequest)
        }
    }
}
