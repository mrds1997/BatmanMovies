package com.batmanapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batmanapp.R
import com.batmanapp.data.db.entities.MovieEntity
import com.batmanapp.data.remote.Resource
import com.batmanapp.models.Movie
import com.batmanapp.models.response.GetBatmanMovies
import com.batmanapp.ui.AppViewModel
import com.batmanapp.ui.activities.MovieDetailsActivity
import com.batmanapp.ui.adapter.MovieAdapter
import com.batmanapp.ui.customs.NoItem
import com.example.filmnettest.ui.customs.Loading
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movies.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var adapter: MovieAdapter? = null

    @Inject
    lateinit var movies: ArrayList<Movie>

    @Inject
    lateinit var movieEntities: ArrayList<MovieEntity>

    private var isLoading = false
    private var endOfList = false
    private var page = 0
    private var searchQueryWord: String? = null
    var loading: Loading? = null
    private var noMore: NoItem? = null

    private val viewModel: AppViewModel by viewModels()

    companion object {
        @JvmStatic
        fun newInstance(): MoviesFragment {
            return MoviesFragment()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerAdapter()

        //getMoviesList()
        subscribeObserver()

    }

    private fun getMoviesList() {
        isLoading = true
        viewLifecycleOwner.lifecycleScope.launch {
            page++
            viewModel.getMoviesList(page)
        }

    }

    private fun subscribeObserver() {
        viewModel.moviesList.observe(viewLifecycleOwner, Observer<Resource<GetBatmanMovies>> {
            isLoading = false
            loading?.dismiss()
            when (it) {
                is Resource.Success -> {
                    loadDetails(it.data)
                }
                is Resource.Failure -> {

                }
                else -> Any()
            }
        })

        viewModel.getMoviesFromEntity()?.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                loading?.dismiss()
                noMore?.dismiss()
                movieEntities.clear()
                movieEntities.addAll(it)
                adapter?.notifyDataSetChanged()
            } else {
                showNoMoreVideo()
            }
        })
    }

    private fun initRecyclerAdapter() {
        adapter = MovieAdapter(movieEntities, object : MovieAdapter.OnItemSelected {
            override fun onSelect(id: String?, title: String?) {
               showMovieDetailsActivity(id, title)
            }

        })
        //view?.rcMoviesList?.adapter = adapter


        val lm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        view?.rcMoviesList?.layoutManager = lm
        view?.rcMoviesList?.adapter = adapter
        view?.rcMoviesList?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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

        loading = Loading.newInstance(view?.layoutRoot)
        movies.clear()
        getMoviesList()
    }

    private fun showMovieDetailsActivity(id: String?, title: String?) {
        val intent = Intent(activity, MovieDetailsActivity::class.java)
        intent.putExtra("movieTitle", title)
        intent.putExtra("id", id)
        startActivity(intent)
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

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            movies?.forEach { movie ->
                if (viewModel.getMovieIdCount(movie.imdbId!!) == 0) {
                    viewModel.insertInMovieEntity(
                        MovieEntity(imdbId = movie.imdbId, title = movie.title!!, poster = movie.poster!!, year = movie.year!!)
                    )
                }
            }

        }

    }

    private fun showNoMoreVideo(){
        noMore = NoItem.newInstance(view?.layoutRoot)
        noMore!!.setImage(R.drawable.img_banner)
            .setTitle(getString(R.string.no_batman_movie))
            .show()
    }

}