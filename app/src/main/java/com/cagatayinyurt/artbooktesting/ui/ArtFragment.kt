package com.cagatayinyurt.artbooktesting.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cagatayinyurt.artbooktesting.R
import com.cagatayinyurt.artbooktesting.databinding.FragmentArtsBinding

class ArtFragment : Fragment(R.layout.fragment_arts) {

    private var fragmentArtsBinding: FragmentArtsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentArtsBinding.bind(view)
        fragmentArtsBinding = binding

        binding.fab.setOnClickListener {
            findNavController().navigate(
                ArtFragmentDirections.actionArtFragmentToArtDetailFragment())
        }
    }

    override fun onDestroyView() {
        fragmentArtsBinding = null
        super.onDestroyView()
    }
}