package com.example.bookmark.di

import androidx.lifecycle.ViewModel
import com.example.bookmark.presentation.BookmarkViewModel
import com.example.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.multibindings.IntoMap

@InstallIn(ViewModelComponent::class)
@Module
abstract class BookmarkFeatureModule {
    @Binds
    @IntoMap
    @ViewModelKey(BookmarkViewModel::class)
    internal abstract fun bookmarkViewModel(viewModel: BookmarkViewModel): ViewModel
}