package com.example.newsapp.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.core.utils.Utils
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModels()
    private var _binding: FragmentDetailBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeState()

        _binding?.ivBack?.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    _binding?.let { binding ->
                        if (it.isLoading) {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.groupDetail.visibility = View.GONE
                        } else {
                            binding.progressBar.visibility = View.GONE
                            binding.groupDetail.visibility = View.VISIBLE
                        }

                        if (it.isFavorite) {
                            binding.ivBookmark.setImageDrawable(
                                AppCompatResources.getDrawable(
                                    requireContext(), R.drawable.baseline_bookmark_24
                                )
                            )
                        } else {
                            binding.ivBookmark.setImageDrawable(
                                AppCompatResources.getDrawable(
                                    requireContext(),
                                    R.drawable.baseline_bookmark_border_24
                                )
                            )
                        }

                        it.article?.let { article ->
                            Glide.with(requireContext())
                                .load(article.urlToImage)
                                .placeholder(com.example.core.R.drawable.rounded_image_bg)
                                .into(binding.ivCoverDetail)

                            binding.tvAuthorDetail.text =
                                if (article.author != "") "by ${article.author}" else "by ${article.sourceName}"
                            binding.tvDateDetail.text = Utils.formatDate(article.publishedAt)
                            binding.tvTitleDetail.text = article.title
                            binding.tvContentDetail.text = article.description
                            binding.tvUrl.text = article.url
                            binding.ivBookmark.setOnClickListener {
                                viewModel.setFavorite()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}