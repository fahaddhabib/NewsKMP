package com.example.newskmp.di

import com.example.newskmp.data.database.ArticleDao
import com.example.newskmp.data.database.DatabaseDriverFactory
import com.example.newskmp.data.remote.ApiService
import com.example.newskmp.data.remote.ApiServiceImpl
import com.example.newskmp.data.repository.NewsRepository
import com.example.newskmp.data.repository.NewsRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule = module {
    singleOf(::ApiServiceImpl) bind ApiService::class
}

val databaseModule = module {
    single { ArticleDao(get()) }
}

val repositoryModule = module {
    singleOf(::NewsRepositoryImpl) bind NewsRepository::class
}

val sharedModules = listOf(networkModule, databaseModule, repositoryModule)