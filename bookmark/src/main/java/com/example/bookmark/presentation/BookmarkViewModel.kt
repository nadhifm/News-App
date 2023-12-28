package com.example.bookmark.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.models.Article
import com.example.core.domain.usecases.GetBookmarkArticle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookmarkViewModel @Inject constructor(
    private val getBookmarkArticle: GetBookmarkArticle
) : ViewModel() {

    private val _state = MutableStateFlow(BookmarkState())
    val state: StateFlow<BookmarkState> = _state.asStateFlow()

    init {
        getArticles()
    }

    private fun getArticles() {
        viewModelScope.launch {
            getBookmarkArticle.invoke().collectLatest { result ->
                _state.update { state ->
                    state.copy(
                        isLoading = false,
                        articles = result,
                    )
                }
            }
        }
    }
}

data class BookmarkState(
    val isLoading: Boolean = false,
    val articles: List<Article> = listOf(),
)