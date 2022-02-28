package com.cagatayinyurt.artbooktesting.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cagatayinyurt.artbooktesting.R
import com.cagatayinyurt.artbooktesting.adapter.ImageRecyclerAdapter
import com.cagatayinyurt.artbooktesting.viewmodel.ArtViewModel
import javax.inject.Inject

class ImageApiFragment @Inject constructor(
    private val imageRecyclerAdapter: ImageRecyclerAdapter
) : Fragment(R.layout.fragment_image_api) {

    lateinit var viewModel: ArtViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[ArtViewModel::class.java]
    }
}