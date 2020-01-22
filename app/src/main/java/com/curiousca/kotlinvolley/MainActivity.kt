package com.curiousca.kotlinvolley

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private var volleyRequest: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        volleyRequest = Volley.newRequestQueue(this)

        parseBigJsonObj(urlQuake)
        //parseJsonObject(url)
        //parseJsonArray(url2)
        //getString(url)
    }

    private fun parseBigJsonObj(Url: String) {
        val jsonBigRequest = JsonObjectRequest(Request.Method.GET, Url, null,
            Response.Listener { response: JSONObject ->
                try {

                    val metaObject = response.getJSONObject("metadata")
                    var title = metaObject.getString("title")
                    // val type = response.getString("type")
                    //Get features json array
                    val features: JSONArray = response.getJSONArray("features")
                    for (i in 0 until features.length()){
                        val propertyObj:JSONObject = features.getJSONObject(i).getJSONObject("properties")
                        var place: String = propertyObj.getString("place")
                        Log.d("Place: ", place)
                        //Geometry Object
                        val geometryObj: JSONObject = features.getJSONObject(i).getJSONObject("geometry")
                        val coordinateArray:JSONArray = geometryObj.getJSONArray("coordinates")
                            val firstCoordinate = coordinateArray.getDouble(0)
                            Log.d("First Coordinate: ", firstCoordinate.toString())
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error: VolleyError ->
                try {
                    Log.d("Error: ", error.toString())

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            })

        volleyRequest!!.add(jsonBigRequest)
    }

    //This is for a json object that has an array of objects
    private fun parseJsonObject(Url: String) {

        //val url ="https://pixabay.com/api/?key=9900098-8e28a903a63da19e7d8c5ab7a&q=nude+female&image_type=photo&pretty=true"

        val request = JsonObjectRequest(Request.Method.GET, Url, null,
            Response.Listener { response ->
                try {
                    val jsonArray: JSONArray = response.getJSONArray("hits")
                    for (i in 0 until jsonArray.length()) {
                        val hit: JSONObject = jsonArray.optJSONObject(i)
                        var creatorName: String = hit.getString("user")
                        var imageUrl: String = hit.getString("webformatURL")
                        var likecount: Int = hit.getInt("likes")
                        var imageTags: String = hit.getString("tags")

                        //Log.d("Array Entry " + i, jsonArray.toString())
                        Log.d("Array Entry $i", creatorName + "\n" + imageTags + "\n" + imageUrl)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error: VolleyError? ->
                try {
                    Log.d("XXX ERROR XXX", error.toString())

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            })
        volleyRequest!!.add(request)

    }

    //This is for a json Array of objects
    private fun parseJsonArray(Url: String) {

        val jsonArray = JsonArrayRequest(Request.Method.GET, Url, null,
            Response.Listener { response: JSONArray ->
                try {
                    Log.d("<---- Response ---->", response.toString())

                    for (i in 0 until response.length()) {
                        val userObj = response.getJSONObject(i)

                        val userName = userObj.getString("username")
                        val userMail = userObj.getString("email")

                        Log.d("UserName ", "$userName\nUserMail $userMail")
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error: VolleyError ->
                try {
                    Log.e("Error: ", error.toString())
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            })

        volleyRequest!!.add(jsonArray)

    }


    //This is a simple string request that returns the entire json array
    private fun getString(Url: String) {

        val stringRequest = StringRequest(Request.Method.GET, Url,
            Response.Listener { response: String ->
                try {
                    Log.d("Response", response.toString())
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            },
            Response.ErrorListener { error: VolleyError ->
                try {
                    Log.d("@@Error@@", error.toString())
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            })
        volleyRequest!!.add(stringRequest)
    }
}
