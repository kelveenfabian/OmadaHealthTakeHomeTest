package com.example.omadahealthtakehometest.data.di

import com.example.omadahealthtakehometest.data.repository.FlickrRepositoryImpl
import com.example.omadahealthtakehometest.domain.repository.FlickrRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindFlickrRepository(
        flickrRepositoryImpl: FlickrRepositoryImpl,
    ): FlickrRepository
}