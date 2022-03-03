package com.cagatayinyurt.artbooktesting.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.cagatayinyurt.artbooktesting.R
import com.cagatayinyurt.artbooktesting.adapter.ImageRecyclerAdapter
import com.cagatayinyurt.artbooktesting.databinding.FragmentImageApiBinding
import com.cagatayinyurt.artbooktesting.util.Status
import com.cagatayinyurt.artbooktesting.viewmodel.ArtViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageApiFragment @Inject constructor(
    var imageRecyclerAdapter: ImageRecyclerAdapter
) : Fragment(R.layout.fragment_image_api) {

    lateinit var viewModel: ArtViewModel

    private var fragmentApiImageBinding: FragmentImageApiBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[ArtViewModel::class.java]

        val binding = FragmentImageApiBinding.bind(view)
        fragmentApiImageBinding = binding

        var job: Job? = null

        binding.searchText.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(1000)
                it?.let {
                    if (it.toString().isNotEmpty()) {
                        viewModel.searchForImage(it.toString())
                    }
                }
            }
        }

        binding.ImageRecyclerView.adapter = imageRecyclerAdapter
        binding.ImageRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        imageRecyclerAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setSelectedImage(it)
        }

        subscribeToObservers()
    }

    fun subscribeToObservers() {
        viewModel.imageList.observe(viewLifecycleOwner, Observer {
            when(it.status) {
                Status.SUCCESS -> {
                    val urls = it.data?.hits?.map { imageResult ->
                        imageResult.previewURL
                    }
                    imageRecyclerAdapter.images = urls ?: listOf()
                    fragmentApiImageBinding?.progressBar?.visibility = View.GONE
                }
                Status.ERROR -> {
                    Toast.makeText(
                        requireContext(),
                        it.message ?: "Error!",
                        Toast.LENGTH_LONG
                    ).show()
                    fragmentApiImageBinding?.progressBar?.visibility = View.GONE
                }
                Status.LOADING -> {
                    fragmentApiImageBinding?.progressBar?.visibility = View.VISIBLE
                }
            }
        })
    }
}