package com.example.netwokingwithandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
//import org.json.JSONObject
//import kotlin.math.log

class MainActivity : AppCompatActivity() {

    val adapter = UserAdapter()
    val userList = arrayListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter.onItemClick = {
            val i = Intent(this, UserActivity::class.java)
            i.putExtra("ID", it)
            startActivity(i)
        }

        userSv.isSubmitButtonEnabled = true
        userSv.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    searchUser(newText)
                }
                return true
            }
        })
        userSv.setOnCloseListener {
            adapter.swapData(userList)
            adapter.notifyDataSetChanged()
            true
        }

        userRv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }


        GlobalScope.launch(Dispatchers.Main){
            val response = withContext(Dispatchers.IO){
                Client.api.getUsers()
            }
            if(response.isSuccessful){
                response.body()?.let {
                    userList.addAll(it)
                    adapter.swapData(it)
                }
            }
        }
    }

    fun searchUser(query:String){

        GlobalScope.launch(Dispatchers.Main){
            val response = withContext(Dispatchers.IO){
                Client.api.searchUser(query)
            }
            if(response.isSuccessful){
                response.body()?.let {
                    it.items?.let { it1 -> adapter.swapData(it1) }
                }
            }
        }
    }
}