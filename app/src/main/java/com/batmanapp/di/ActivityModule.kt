package com.batmanapp.di

import com.batmanapp.data.db.entities.MovieEntity
import com.batmanapp.models.Movie
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @ActivityScoped
    @Provides
    fun provideMovieArrayListInstance() = ArrayList<Movie>()

    @ActivityScoped
    @Provides
    fun provideVideoArrayListInstance() = ArrayList<MovieEntity>()
}