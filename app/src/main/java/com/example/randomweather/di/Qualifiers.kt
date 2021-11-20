package com.example.randomweather.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IODispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiHost

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiId