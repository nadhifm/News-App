package com.example.newsapp.presentation.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.core.presentation.ArticleAdapter
import com.example.newsapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeState()
        setupRecyclerView()
        _binding?.etSearch?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                viewModel.setQuery(charSequence.toString())
            }

            override fun afterTextChanged(editable: Editable?) {
            }
        })
        _binding?.ivBookmark?.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToBookmarkFragment())
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    if (it.errorMessage != "") {
                        Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()
                        viewModel.resetErrorMessage()
                    }
                    if (it.isLoading) {
                        _binding?.progressBar?.visibility = View.VISIBLE
                        _binding?.rvArticle?.visibility = View.GONE
                    } else {
                        _binding?.progressBar?.visibility = View.GONE
                        _binding?.rvArticle?.visibility = View.VISIBLE
                    }
                    articleAdapter.notifyItemRangeChanged(0, articleAdapter.itemCount)
                    articleAdapter.submitList(it.articles)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        articleAdapter = ArticleAdapter {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                    it
                )
            )
        }
        _binding?.rvArticle?.adapter = articleAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding?.rvArticle?.adapter = null
        _binding = null
    }
}