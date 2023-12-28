package com.example.bookmark.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.bookmark.databinding.FragmentBookmarkBinding
import com.example.bookmark.utils.inject
import com.example.core.presentation.ArticleAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookmarkFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: BookmarkViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[BookmarkViewModel::class.java]
    }
    private var _binding: FragmentBookmarkBinding? = null
    private lateinit var articleAdapter: ArticleAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeState()
        setupRecyclerView()

        _binding?.ivBack?.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    articleAdapter.notifyItemRangeChanged(0, articleAdapter.itemCount)
                    articleAdapter.submitList(it.articles)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        articleAdapter = ArticleAdapter {
            findNavController().navigate(
                BookmarkFragmentDirections.actionBookmarkFragmentToDetailFragment(
                    it
                )
            )
        }
        _binding?.rvBookmark?.adapter = articleAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding?.rvBookmark?.adapter = null
        _binding = null
    }
}