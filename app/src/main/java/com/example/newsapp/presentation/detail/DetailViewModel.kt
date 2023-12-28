package com.example.newsapp.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.models.Article
import com.example.core.domain.usecases.AddFavoriteArticle
import com.example.core.domain.usecases.CheckFavoriteArticle
import com.example.core.domain.usecases.RemoveFavoriteArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val checkFavoriteArticle: CheckFavoriteArticle,
    private val addFavoriteArticle: AddFavoriteArticle,
    private val removeArticle: RemoveFavoriteArticle,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state.asStateFlow()

    init {
        savedStateHandle.get<Article>("article")?.let { article ->
            _state.update {
                it.copy(
                    isLoading = true,
                    article = article,
                )
            }
        }
        checkIsFavorite()
    }

    private fun checkIsFavorite() {
        _state.value.article?.let {
            viewModelScope.launch {
                checkFavoriteArticle.invoke(it.title).collectLatest { result ->
                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            isFavorite = result,
                        )
                    }
                }
            }
        }
    }

    fun setFavorite() {
        viewModelScope.launch {
            _state.value.article?.let { article ->
                if (_state.value.isFavorite) {
                    removeArticle.invoke(article.title)
                    checkIsFavorite()
                } else {
                    addFavoriteArticle.invoke(article)
                    checkIsFavorite()
                }
            }
        }
    }
}

data class DetailState(
    val isLoading: Boolean = false,
    val isFavorite: Boolean = false,
    val article: Article? = null
)