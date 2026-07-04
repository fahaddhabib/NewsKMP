package com.example.newskmp.di

import com.example.newskmp.AppViewModel
import com.example.newskmp.NewsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::NewsViewModel)

    viewModelOf(::AppViewModel)
}


val sharedUIModules = listOf(viewModelModule)