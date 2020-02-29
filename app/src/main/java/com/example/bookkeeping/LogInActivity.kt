package com.example.bookkeeping

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_log_in.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception

class LogInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        btnLogin.setOnClickListener {
            if((username.text.toString() == "") and (password.text.toString() == "")){
                Toast.makeText(this@LogInActivity, "Please Enter Username and Password.", Toast.LENGTH_LONG).show()
            }else{
                var preference=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
                var editor=preference.edit()
                editor.putString("uname",username.text.toString());
                editor.commit()
                var intent= Intent(this@LogInActivity,AdminDashboard::class.java)
                startActivity(intent)
                finish()

                var tempUser=username.text.toString()
                var tempPass = password.text.toString()
                callService(tempUser, tempPass)
            }
        }

        txtRegister.setOnClickListener {
            var i= Intent(this@LogInActivity, RegisterUser::class.java)
            startActivity(i)
        }
    }

    fun callService(tempUser:String , tempPass:String){
        try {
            var client= OkHttpClient()

            var formBody= FormBody.Builder()
                .add("user", tempUser)
                .add("pass", tempPass)
                .build()

            var req= Request.Builder()
                .url("http://10.0.2.2:80/CaseStudy/login.php")
                .post(formBody)
                .build()

            client.newCall(req).enqueue(object : Callback {

                override fun onFailure(call: Call, e: IOException) {
                    Log.v("exception", e.toString())
                }

                override fun onResponse(call: Call, response: Response){
                    response.use{
                        var str=response.body!!.string()
                        var js = JSONObject(str)
                        var flag= js.getInt("success")
                        var msg= js.getString("message")

                        Log.v("res",str)
                        Log.v("cd", flag.toString())
                        Log.v("ms",msg)

                        if(flag == 1){
                            Log.v("fs", flag.toString())
                            runOnUiThread{
                                /*var per=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
                                var editor=per.edit()
                                editor.putString("user",username.text.toString())
                                editor.commit()*/

                                Toast.makeText(this@LogInActivity,"Login Successful.!", Toast.LENGTH_LONG).show()
                                var i= Intent(this@LogInActivity,AdminDashboard::class.java)
                                startActivity(i)
                            }
                        }else{
                            Log.v("ff", flag.toString())
                            runOnUiThread{
                                Toast.makeText(this@LogInActivity,"Login Failed", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            })
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}