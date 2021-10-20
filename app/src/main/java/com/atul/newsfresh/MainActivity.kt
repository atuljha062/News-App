package com.atul.newsfresh

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.atul.newsfresh.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NewsOnClick {

    private lateinit var binding:ActivityMainBinding
    private lateinit var adapter: NewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        adapter = NewsAdapter(this)
        binding.recyclerView.adapter =adapter
    }

    private fun fetchData() {
        val url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=efad20af7be34e9e893ca4fb48d2ff35"

        val jsonObjectRequest = object: JsonObjectRequest(Request.Method.GET,url,null,
            Response.Listener { response ->
                val articlesArray = response.getJSONArray("articles")
                val list = ArrayList<News>()
                for (i in 0 until articlesArray.length() ){
                    val arrayObject = articlesArray.getJSONObject(i)
                    val urlToImage = arrayObject.getString("urlToImage")
                    val title = arrayObject.getString("title")
                    val author = arrayObject.getString("author")
                    val url = arrayObject.getString("url")

                    list.add(News(urlToImage,title,author,url))
                }
                adapter.updateNews(list)

            },Response.ErrorListener { error ->
                Toast.makeText(this,"Error Occured: $error",Toast.LENGTH_SHORT).show()
            }){
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {
        val builder = CustomTabsIntent.Builder()
        val customTabIntent = builder.build()
        customTabIntent.launchUrl(this,Uri.parse(item.url))
    }
}