package com.curiousca.kotlinvolley

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    private var volleyRequest: RequestQueue? = null
    private val url = "https://pixabay.com/api/?key=9900098-8e28a903a63da19e7d8c5ab7a&q=nude+female&image_type=photo&pretty=true"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        volleyRequest = Volley.newRequestQueue(this)

        getString(url)
    }

    

    private fun getString(Url: String){

        val stringRequest = StringRequest(Request.Method.GET, Url, Response.Listener {
            response: String ->
            try {
                Log.d("Response", response.toString())
            }catch (ex: Exception){
                ex.printStackTrace()
            }
        },
            Response.ErrorListener {
                error: VolleyError ->
                try {
                    Log.d("@@Error@@", error.toString())
                }catch (e: JSONException){
                    e.printStackTrace()
                }
            })
        volleyRequest!!.add(stringRequest)
    }
}
