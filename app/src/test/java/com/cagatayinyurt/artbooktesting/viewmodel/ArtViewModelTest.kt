package com.cagatayinyurt.artbooktesting.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cagatayinyurt.artbooktesting.MainCoroutineRule
import com.cagatayinyurt.artbooktesting.getOrAwaitValueTest
import com.cagatayinyurt.artbooktesting.repo.FakeArtRepository
import com.cagatayinyurt.artbooktesting.util.Status
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ArtViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    
    private lateinit var viewModel: ArtViewModel
    
    @Before
    fun setup() {
        // Test Doubles - Fake Repo
        viewModel = ArtViewModel(FakeArtRepository())
    }

    @Test
    fun `insert art without year returns error`() {
        viewModel.makeArt("Starry Nights", "Van Gogh", "")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without name returns error`() {
        viewModel.makeArt("", "Van Gogh", "1900")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without artistName returns error`() {
        viewModel.makeArt("Starry Nights", "", "1900")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }
}