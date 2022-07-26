package com.batmanapp.ui.activities

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.batmanapp.R
import com.batmanapp.Utils
import com.batmanapp.Utils.Companion.handleApiError
import com.batmanapp.data.db.entities.MovieDetailsEntity
import com.batmanapp.data.remote.Resource
import com.batmanapp.models.response.MovieDetailsResponse
import com.batmanapp.ui.AppViewModel
import com.batmanapp.ui.customs.NoItem
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.activity_movie_detail.layoutRoot
import kotlinx.android.synthetic.main.adapter_genre.view.*
import kotlinx.android.synthetic.main.header.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {

    private var id: String? = null

    private var movieTitle: String? = null


    private var noMore: NoItem? = null

    @Inject
    lateinit var glide: RequestManager

    private val viewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        id = intent?.getStringExtra("id")
        movieTitle = intent?.getStringExtra("movieTitle")

        initHeader()

        getMovieDetails()
        subscribeObserver()

    }


    private fun initHeader() {
        tvHeaderTitle.text = movieTitle
        imgBack.setOnClickListener {
            super.onBackPressed()
        }
    }

    private fun getMovieDetails() {
        lifecycleScope.launch {
            viewModel.getMovieDetails(id!!)
        }
    }

    private fun subscribeObserver() {
        viewModel.moviesDetails.observe(this, Observer {
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

        viewModel.getMovieDetailsFromEntity(id!!)?.observe(this, Observer {
            if (it != null) {
                loadDetailsFromDb(it)
            } else {
                showNoMoreDetails()
            }
        })
    }

    private fun loadDetailsFromDb(movieDetailsEntity: MovieDetailsEntity) {
        noMore?.dismiss()
        imgPoster.visibility = View.VISIBLE
        glide.load(movieDetailsEntity.poster).into(imgPoster)
        tvCountry.text = getString(R.string.country, movieDetailsEntity.country)
        tvYear.text = getString(R.string.year, movieDetailsEntity.year)
        tvActor.text = getString(R.string.actors, movieDetailsEntity.actors)
        tvMaxRate.text = getString(R.string.ten)
        imgImdb.visibility = View.VISIBLE
        tvImdbRate.text = getString(R.string.imdb_rate, movieDetailsEntity.imdbRate)
        val genres = movieDetailsEntity.genre.split(", ")
        layoutGenre.removeAllViews()
        genres.forEach {
            val viewGenre =
                layoutInflater.inflate(R.layout.adapter_genre, layoutGenre, false)
            viewGenre.tvGenre.text = it
            layoutGenre.addView(viewGenre)
        }
    }

    private fun showNoMoreDetails(){
        noMore = NoItem.newInstance(layoutRoot)
        noMore!!.setImage(R.drawable.pic_no_more)
            .setTitle(getString(R.string.no_batman_details))
            .show()
    }

    private fun loadDetails(response: MovieDetailsResponse?) {

        if (response != null) {
            insertInDatabase(response)
        }
    }

    private fun insertInDatabase(response: MovieDetailsResponse?) {

        lifecycleScope.launch(Dispatchers.IO) {
            if (viewModel.getMovieDetailsIdCount(response!!.imdbId) == 0) {
                viewModel.insertInMovieDetailsEntity(
                    MovieDetailsEntity(
                        imdbId = response.imdbId,
                        poster = response.poster,
                        country = response.country,
                        year = response.year,
                        actors = response.actors,
                        genre = response.genre,
                        imdbRate = response.imdbRating
                    )
                )
            }
        }
    }
}