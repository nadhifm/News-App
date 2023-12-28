package com.example.newsapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.models.Article
import com.example.core.domain.usecases.GetAllArticle
import com.example.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllArticle: GetAllArticle,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private var searchJob: Job? = null

    init {
        getArticles("")
    }

    fun setQuery(query: String) {
        if (query != _state.value.query) {
            _state.update {
                it.copy(
                    query = query,
                )
            }
            searchJob?.cancel()
            searchJob = viewModelScope.launch {
                delay(500L)
                getArticles(query)
            }
        }
    }

    private fun getArticles(query: String) {
        getAllArticle.invoke(query).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            articles = result.data ?: listOf(),
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.message ?: ""
                        )
                    }
                }

                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = true,
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun resetErrorMessage() {
        _state.update {
            it.copy(
                errorMessage = ""
            )
        }
    }
}

data class HomeState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val query: String = "",
    val articles: List<Article> = listOf(),
)