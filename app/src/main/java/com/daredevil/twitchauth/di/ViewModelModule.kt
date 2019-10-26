package com.daredevil.twitchauth.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.daredevil.twitchauth.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindTokenViewModel(tokenViewModel: MainViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}