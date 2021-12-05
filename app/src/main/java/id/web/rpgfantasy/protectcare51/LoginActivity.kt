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
    companion object{
        val EXTRA_USERNAME = "USERNAME"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        //retrive saved username
        var sharedFile = packageName
        var shared: SharedPreferences = getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
        var savedUsername = shared.getString(EXTRA_USERNAME, "")

        savedUsername?.let {
            if (it.isNotEmpty()){
                startActivity(Intent(this, MainActivity::class.java).apply {
                    putExtra(EXTRA_USERNAME,it)
                })
                finish()
            }
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonLogin.setOnClickListener {
            val username = txtUsername.text.toString()
            val password = txtPassword.text.toString()
            Login(username, password,shared)
        }
    }

    fun Login(username:String, password:String?, shared:SharedPreferences){
        val q = Volley.newRequestQueue(this)
        val url = "https://ubaya.fun/native/160419091/ProtectCare51/getUser.php"
        val stringRequest = object : StringRequest(Request.Method.POST, url, Response.Listener {
            Log.d("cek", it)
            val obj = JSONObject(it)
            if(obj.getString("result") == "OK"){
                var editor: SharedPreferences.Editor = shared.edit()
                    editor.putString(EXTRA_USERNAME,username)
                    editor.apply()
                startActivity(Intent(this, MainActivity::class.java).apply {
                    putExtra(EXTRA_USERNAME,username)
                })
                finish()
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
                params["username"] = username
                password?.let {
                    params["password"] = it
                }
                return params
            }
        }
        q.add(stringRequest)
    }
}

