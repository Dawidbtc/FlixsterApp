package com.example.flixster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

const val loc = "MainActivity:"
class MainActivity : AppCompatActivity() {
    private val movies = mutableListOf<Movie>()
    private lateinit var ViewMovies : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewMovies = findViewById(R.id.ViewMovie)
        val MovieAdapter = MovieAdapter(this, movies)
        ViewMovies.adapter = MovieAdapter
        ViewMovies.layoutManager = LinearLayoutManager(this)
        val httpClient = AsyncHttpClient()
        httpClient.get("https://api.themoviedb.org/3/movie/now_playing?api_key=4187271d66c92133710ef700f22a178a", object: JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(loc, "onFailure $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.i(loc, "onSuccess: JSON data $json")
                try {
                    val jArray = json.jsonObject.getJSONArray("results")
                    movies.addAll(Movie.fetchItems(jArray))
                    MovieAdapter.notifyDataSetChanged()
                }catch(error: JSONException){
                    Log.e(loc, "Encountered JSONException $error")
                }
            }


        })
    }
}