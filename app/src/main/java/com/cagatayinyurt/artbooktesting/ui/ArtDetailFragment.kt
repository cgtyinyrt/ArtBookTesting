package com.cagatayinyurt.artbooktesting.ui

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cagatayinyurt.artbooktesting.R
import com.cagatayinyurt.artbooktesting.databinding.FragmentArtDetailBinding

class ArtDetailFragment : Fragment(R.layout.fragment_art_detail) {

    private var artDetailBinding: FragmentArtDetailBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentArtDetailBinding.bind(view)
        artDetailBinding = binding

        binding.artImageView.setOnClickListener {
            findNavController().navigate(
                ArtDetailFragmentDirections.actionArtDetailFragmentToImageApiFragment())
        }

        val callBack = object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callBack)
    }

    override fun onDestroyView() {
        artDetailBinding = null
        super.onDestroyView()
    }
}