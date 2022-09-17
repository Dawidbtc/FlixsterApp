package com.example.flixster

import org.json.JSONArray

data class Movie(
    val ID: Int,
    val title: String,
    val description: String,
    val picturePath: String,

){
    companion object {
        private const val imageUrl = "https://image.tmdb.org/t/p/w342"
        fun fetchItems(jArray: JSONArray): List<Movie> {
            val listOfMovies = mutableListOf<Movie>()
            for (i in 0 until jArray.length()) {
                val movie = jArray.getJSONObject(i)
                listOfMovies.add(
                    Movie(
                        movie.getInt("id"),
                        movie.getString("title"),
                        movie.getString("overview"),
                        imageUrl+movie.getString("poster_path")
                    )
                )
            }
            return listOfMovies
        }
    }
}