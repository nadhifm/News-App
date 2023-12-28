package com.example.bookmark.di

import android.content.Context
import com.example.bookmark.presentation.BookmarkFragment
import com.example.core.di.DaggerDependencies
import com.example.core.di.ViewModelModule
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [DaggerDependencies::class],
    modules = [
        ViewModelModule::class,
        BookmarkFeatureModule::class
    ]
)
interface BookmarkComponent {

    fun inject(bookmarkFragment: BookmarkFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(daggerDependencies: DaggerDependencies): Builder
        fun build(): BookmarkComponent
    }
}
