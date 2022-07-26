package com.batmanapp.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batmanapp.R
import com.batmanapp.Utils
import com.batmanapp.Utils.Companion.handleApiError
import com.batmanapp.data.db.entities.MovieEntity
import com.batmanapp.data.remote.Resource
import com.batmanapp.models.Movie
import com.batmanapp.models.response.GetBatmanMovies
import com.batmanapp.ui.AppViewModel
import com.batmanapp.ui.adapter.MovieAdapter
import com.batmanapp.ui.customs.NoItem
import com.example.filmnettest.ui.customs.Loading
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_movies.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var adapter: MovieAdapter? = null

    private var isLoading = false
    private var endOfList = false
    private var page = 0
    private var searchQueryWord: String? = null
    var loading: Loading? = null
    private var noMore: NoItem? = null

    @Inject
    lateinit var movies: ArrayList<Movie>

    @Inject
    lateinit var movieEntities: ArrayList<MovieEntity>

    private val viewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        subscribeObserver()

        initRecyclerAdapter()
    }


    private fun subscribeObserver() {
        viewModel.moviesList.observe(this, Observer<Resource<GetBatmanMovies>> {
            isLoading = false
            loading?.dismiss()
            when (it) {
                is Resource.Success -> {
                    loadDetails(it.data)
                }
                is Resource.Failure -> {
                    handleApiError(it)
                }
                else -> Any()
            }
        })

        viewModel.getMoviesFromEntity()?.observe(this, Observer {
            if (it.isNotEmpty()) {
                loadDetailsFromDb(it)
            } else {
                showNoMoreVideo()
            }
        })
    }

    private fun loadDetailsFromDb(it: List<MovieEntity>?) {
        loading?.dismiss()
        noMore?.dismiss()
        movieEntities.clear()
        movieEntities.addAll(it!!)
        adapter?.notifyDataSetChanged()
    }

    private fun loadDetails(response: GetBatmanMovies?) {
        if (response != null) {
            if (response.movies != null) {
                if (response.movies != null && response.movies.isNotEmpty()) {
                    insertInDatabase(response.movies)
                }

                if (response.movies.size == 0) {
                    endOfList = true
                }
            }
        }
    }

    private fun insertInDatabase(movies: ArrayList<Movie>?) {

        lifecycleScope.launch(Dispatchers.IO) {
            movies?.forEach { movie ->
                if (viewModel.getMovieIdCount(movie.imdbId!!) == 0) {
                    viewModel.insertInMovieEntity(
                        MovieEntity(imdbId = movie.imdbId, title = movie.title!!, poster = movie.poster!!, year = movie.year!!)
                    )
                }
            }

        }

    }


    private fun initRecyclerAdapter() {
        adapter = MovieAdapter(movieEntities, object : MovieAdapter.OnItemSelected {
            override fun onSelect(id: String?, title: String?) {
                showMovieDetailsActivity(id, title)
            }

        })

        val lm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        rcMoviesList?.layoutManager = lm
        rcMoviesList?.adapter = adapter
        rcMoviesList?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val visibleItemCount: Int = lm.childCount
                    val totalItemCount: Int = lm.itemCount
                    val pastVisibleItems: Int = lm.findFirstVisibleItemPosition()

                    if (!isLoading && !endOfList) {
                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                            loading?.startLazy()
                            getMoviesList()
                        }
                    }
                }
            }
        })

        loading = Loading.newInstance(layoutRoot)
        movies.clear()
        getMoviesList()
    }


    private fun showMovieDetailsActivity(id: String?, title: String?) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra("movieTitle", title)
        intent.putExtra("id", id)
        startActivity(intent)
    }


    private fun getMoviesList() {
        isLoading = true
        lifecycleScope.launch {
            page++
            viewModel.getMoviesList(page)
        }

    }

    private fun showNoMoreVideo(){
        noMore = NoItem.newInstance(layoutRoot)
        noMore!!.setImage(R.drawable.pic_no_more)
            .setTitle(getString(R.string.no_batman_movie))
            .show()
    }


}