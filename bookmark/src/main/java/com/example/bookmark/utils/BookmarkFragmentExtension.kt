package com.example.bookmark.utils

import com.example.bookmark.di.DaggerBookmarkComponent
import com.example.bookmark.presentation.BookmarkFragment
import com.example.core.di.DaggerDependencies
import dagger.hilt.android.EntryPointAccessors

internal fun BookmarkFragment.inject() {
    DaggerBookmarkComponent
        .builder()
        .context(requireContext())
        .appDependencies(
            EntryPointAccessors.fromApplication(
                requireContext(),
                DaggerDependencies::class.java
            )
        )
        .build()
        .inject(this)
}
