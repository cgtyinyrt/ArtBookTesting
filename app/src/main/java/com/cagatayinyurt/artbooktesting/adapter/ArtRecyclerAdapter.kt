package com.cagatayinyurt.artbooktesting.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.cagatayinyurt.artbooktesting.databinding.ArtRowBinding
import com.cagatayinyurt.artbooktesting.model.Art
import javax.inject.Inject

class ArtRecyclerAdapter @Inject constructor(
    val glide: RequestManager
) : RecyclerView.Adapter<ArtRecyclerAdapter.ArtViewHolder>() {

    inner class ArtViewHolder(
        val binding: ArtRowBinding
    ) : RecyclerView.ViewHolder(binding.root)

    private var diffUtil = object : DiffUtil.ItemCallback<Art>() {
        override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var arts: List<Art>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArtViewHolder {
        val binding =
            ArtRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ArtViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtViewHolder, position: Int) {
        val imageView = holder.binding.ivArtRow
        val nameText = holder.binding.artRowNameText
        val artistNameText = holder.binding.artRowArtistNameText
        val yearText = holder.binding.artRowYearText
        val art = arts[position]

        holder.itemView.apply {
            nameText.text = "Name: ${art.name}"
            artistNameText.text = "Artist Name: ${art.artistName}"
            yearText.text = "Year: ${art.year}"
            glide.load(art.imageUrl).into(imageView)
        }
    }

    override fun getItemCount(): Int {
        return arts.size
    }
}