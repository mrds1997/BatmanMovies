package com.batmanapp.ui
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.batmanapp.data.db.entities.MovieDetailsEntity
import com.batmanapp.data.db.entities.MovieEntity
import com.batmanapp.data.remote.Resource
import com.batmanapp.data.repositories.Repository
import com.batmanapp.models.response.GetBatmanMovies
import com.batmanapp.models.response.MovieDetailsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class AppViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _moviesList = MutableLiveData<Resource<GetBatmanMovies>>()
    val moviesList: LiveData<Resource<GetBatmanMovies>>
    get() = _moviesList

    suspend fun getMoviesList(page: Int) {
        _moviesList.value = repository.getBatmanMoviesList(page)
    }


    private val _moviesDetails = MutableLiveData<Resource<MovieDetailsResponse>>()
    val moviesDetails: LiveData<Resource<MovieDetailsResponse>>
        get() = _moviesDetails

    suspend fun getMovieDetails(imdbId: String){
        _moviesDetails.value = repository.getBatmanMovieDetails(imdbId)
    }


    fun getMovieIdCount(videoId: String) = repository.getMovieIdCount(videoId)

    fun getMoviesFromEntity() = repository.getMoviesFromEntity()

    suspend fun insertInMovieEntity(movieEntity: MovieEntity) = withContext(Dispatchers.IO) {
        repository.insertInMovieEntity(movieEntity)
    }

    suspend fun deleteVideoFromDb(movieEntity: MovieEntity) = withContext(Dispatchers.IO){
        repository.deleteVideo(movieEntity)
    }


    fun getMovieDetailsFromEntity(imdbId: String) = repository.getMovieDetailsFromEntity(imdbId)

    suspend fun insertInMovieDetailsEntity(movieDetailsEntity: MovieDetailsEntity) = withContext(Dispatchers.IO) {
        repository.insertInMovieDetailsEntity(movieDetailsEntity)
    }


    fun getMovieDetailsIdCount(imdbID: String) = repository.getMovieDetailsIdCount(imdbID)

}