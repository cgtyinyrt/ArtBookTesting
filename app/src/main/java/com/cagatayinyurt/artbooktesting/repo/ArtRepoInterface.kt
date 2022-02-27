package com.cagatayinyurt.artbooktesting.repo

import androidx.lifecycle.LiveData
import com.cagatayinyurt.artbooktesting.model.Art
import com.cagatayinyurt.artbooktesting.model.ImageResponse
import com.cagatayinyurt.artbooktesting.util.Resource

interface ArtRepoInterface {

    suspend fun insertArt(art: Art)

    suspend fun deleteArt(art: Art)

    fun getArt() : LiveData<List<Art>>

    suspend fun searchImage(imageString: String) : Resource<ImageResponse>
}