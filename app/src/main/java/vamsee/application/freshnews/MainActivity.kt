package vamsee.application.freshnews

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import androidx.lifecycle.Observer
import com.android.volley.toolbox.JsonObjectRequest
import vamsee.application.freshnews.modal.News
import vamsee.application.freshnews.repository.Repository

class MainActivity : AppCompatActivity(), newItemClicked {

    private lateinit var mAdapter: NewsListAdapter
    private lateinit var viewModel: MainViewModel
    var newsArray = ArrayList<News>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        //fetchData()
        mAdapter = NewsListAdapter(this)
        setUpAdapter()
        recyclerView.adapter = mAdapter

    }

    private fun fetchData(){
        val url = "https://saurav.tech/NewsAPI/top-headlines/category/health/in.json"
        val jsonObject = JsonObjectRequest(Request.Method.GET, url, null,
                {
                    val newsJsonArray = it.getJSONArray("articles")
                    //val newsArray = ArrayList<News>()
                    for (i in 0 until newsJsonArray.length()){
                        val newsJsonObject = newsJsonArray.getJSONObject(i)
                        val news = News(
                                newsJsonObject.getString("title"),
                                newsJsonObject.getString("urlToImage"),
                                newsJsonObject.getString("author"),
                                newsJsonObject.getString("description"),
                                newsJsonObject.getString("url")
                        )

                        newsArray.add(news)
                    }

                    mAdapter.updateNews(newsArray)
                },
                {

                }
        )

        MySingleton.getInstance(this).addToRequestQueue(jsonObject)


    }

    fun setUpAdapter(){
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getNews()
        viewModel.news.observe(this, Observer {
            if(it.isSuccessful){
                newsArray = it.body()?.articles!!

                for (i in 0 until newsArray.size){
                    mAdapter.setData(newsArray[i])
                }
                //it.body()?.let { mAdapter?.setData(it) }
            }
            else{
                Log.d("Response", it.errorBody().toString())
                Toast.makeText(this, "error ${it.errorBody()}", Toast.LENGTH_SHORT).show()
            }
        })

    }

    override fun onItemClicked(item: News) {
//        Toast.makeText(this, "Item clicked $item", Toast.LENGTH_LONG).show()
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }
}