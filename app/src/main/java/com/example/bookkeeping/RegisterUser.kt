package com.example.bookkeeping

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.Validators.and
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register_user.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception
import javax.security.auth.login.LoginException

class RegisterUser : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)
        btnRegister.setOnClickListener {
            if((reg_username.text.toString() == "") and (reg_uemail.text.toString() == "") and (reg_umobno.text.toString() == "") and (reg_upassword.text.toString() == "")){
                Toast.makeText(this@RegisterUser, "Required fields are missing.", Toast.LENGTH_LONG).show()
            } else{
                var regUser=reg_username.text.toString()
                var regEmail=reg_uemail.text.toString()
                var regMobno=reg_umobno.text.toString()
                var regPass=reg_upassword.text.toString()
                callService(regUser, regEmail, regMobno, regPass)
            }
        }
        txtRegister.setOnClickListener {
            var i= Intent(this@RegisterUser, LogInActivity::class.java)
            startActivity(i)
        }
    }

    fun callService(regUser:String, regEmail:String, regMobno:String, regPass:String){
        try{
            var client= OkHttpClient()

            var formBody=FormBody.Builder()
                .add("userName", regUser)
                .add("userEmail", regEmail)
                .add("userMobile", regMobno)
                .add("userPass", regPass)
                .build()

            var req=Request.Builder()
                .url("http://10.0.2.2:80/CaseStudy/register.php")
                .post(formBody)
                .build()

            client.newCall(req).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.v("exception", e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use{
                        Log.v("check","In onResponse")
                        if(!response.isSuccessful) throw IOException("Unexpected code $response")

                        var str=response.body!!.string()
                        Log.v("test",str)

                        val obj=JSONObject(str)
                        val flag=obj.getInt("success")
                        val status=obj.getString("message")

                        Log.v("flag",flag.toString())
                        Log.v("msg", status)
                        Log.v("res",response.toString())

                        if(flag == 1){
                            Log.v("fs", flag.toString())
                            runOnUiThread{
                                Toast.makeText(this@RegisterUser,"User Registered Successfully.", Toast.LENGTH_LONG).show()
                                var i= Intent(this@RegisterUser,LogInActivity::class.java)
                                startActivity(i)
                            }
                        }else{
                            Log.v("ff", flag.toString())
                            runOnUiThread{
                                Toast.makeText(this@RegisterUser,"Cannot Register.", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            })
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }
}
