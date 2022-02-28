package com.cagatayinyurt.artbooktesting.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.cagatayinyurt.artbooktesting.R
import com.cagatayinyurt.artbooktesting.databinding.FragmentArtDetailBinding
import com.cagatayinyurt.artbooktesting.util.Status
import com.cagatayinyurt.artbooktesting.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtDetailFragment @Inject constructor(
    val glide: RequestManager
) : Fragment(R.layout.fragment_art_detail) {

    private var artDetailBinding: FragmentArtDetailBinding? = null
    lateinit var viewModel : ArtViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[ArtViewModel::class.java]

        val binding = FragmentArtDetailBinding.bind(view)
        artDetailBinding = binding

        subscribeToObservers()

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

        binding.saveButton.setOnClickListener {
            viewModel.makeArt(
                binding.edtNameText.text.toString(),
                binding.edtArtistText.text.toString(),
                binding.edtYearText.text.toString())
        }
    }

    private fun subscribeToObservers() {
        viewModel.selectedImageURL.observe(viewLifecycleOwner, Observer { url ->
            artDetailBinding?.let {
                glide.load(url).into(it.artImageView)
            }
        })

        viewModel.insertArtMessage.observe(viewLifecycleOwner, Observer {
            when(it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                    viewModel.resetInsertArtMsg()
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    Toast.makeText(
                        requireContext(),
                        it.message ?: "Error!",
                        Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onDestroyView() {
        artDetailBinding = null
        super.onDestroyView()
    }
}