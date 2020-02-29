package com.example.bookkeeping

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_admin_dashboard.*

class AdminDashboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)
        var preference=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        var str=preference.getString("uname","Wrong")
        //textView2.text="Welcome:$str"

        btnManageAuthor.setOnClickListener {
            Toast.makeText(this@AdminDashboard, "Manage Author", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logout){
            var preference=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
            var editor=preference.edit()
            editor.clear()
            editor.commit()
            Toast.makeText(this,"Logged out Successfully!",Toast.LENGTH_LONG).show()
            var i= Intent(this@AdminDashboard,LogInActivity::class.java)
            startActivity(i)
            finish()
        }
        else{
            Toast.makeText(this,"Random Option!",Toast.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }
}
