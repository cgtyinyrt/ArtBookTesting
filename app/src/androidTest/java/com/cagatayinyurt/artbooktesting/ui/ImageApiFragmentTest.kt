package com.cagatayinyurt.artbooktesting.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.cagatayinyurt.artbooktesting.R
import com.cagatayinyurt.artbooktesting.adapter.ImageRecyclerAdapter
import com.cagatayinyurt.artbooktesting.getOrAwaitValue
import com.cagatayinyurt.artbooktesting.launchFragmentInHiltContainer
import com.cagatayinyurt.artbooktesting.repo.FakeArtRepositoryAndroid
import com.cagatayinyurt.artbooktesting.viewmodel.ArtViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ImageApiFragmentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory : ArtFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testSelectImage() {
        val navController = Mockito.mock(NavController::class.java)
        val selectedImageUrl = "cagatay.com"
        val testViewModel = ArtViewModel(FakeArtRepositoryAndroid())

        launchFragmentInHiltContainer<ImageApiFragment>(
            factory = fragmentFactory,
        ) {
            Navigation.setViewNavController(requireView(),navController)
//            viewModel = testViewModel
//            imageRecyclerAdapter.images = listOf(selectedImageUrl)
        }

        Espresso.onView(ViewMatchers.withId(R.id.ImageRecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageRecyclerAdapter.ImageViewHolder>(
                0,click()
            )
        )
        Mockito.verify(navController).popBackStack()
        assertThat(testViewModel.selectedImageURL.getOrAwaitValue()).isEqualTo(selectedImageUrl)
    }
}