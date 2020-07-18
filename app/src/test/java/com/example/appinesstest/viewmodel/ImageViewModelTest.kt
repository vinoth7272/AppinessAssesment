package com.example.appinesstest.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.appinesstest.TestCoroutineRule
import com.example.appinesstest.model.FlickrPhoto
import com.example.appinesstest.model.Resource
import com.example.appinesstest.network.ApiService
import com.example.appinesstest.utils.Constant
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ImageViewModelTest {

    val API_KEY = "062a6c0c49e4de1d78497d13a7dbb360"
    val FORMAT_TYPE = "json"
    val METHOD_NAME = "flickr.photos.search"

    private lateinit var searchText: String

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var apiResponseObserver: Observer<Resource<List<FlickrPhoto>>>

    @Before
    fun setUp() {
        searchText = "Nature"
    }

    @Test
    fun getApiSuccessResponse() {
        testCoroutineRule.runBlockingTest {
            doReturn(emptyList<FlickrPhoto>())
                .`when`(apiService)
                .getFlickrImages(
                    searchText = searchText,
                    page = 1
                )
            val viewModel = ImageViewModel(apiService)
            viewModel.resource.observeForever(apiResponseObserver)
            verify(apiService, atLeast(1)).getFlickrImages(
                searchText = searchText,
                page = 1
            )
            verify(apiResponseObserver).onChanged(Resource.success(emptyList()))
            viewModel.resource.removeObserver(apiResponseObserver)
        }
    }

    @Test
    fun getApiErrorResponse_Test() {
        val API_KEY="062a6c0c49e4de1d78497d13a7dbb360"
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Data Mismatch error"
            doThrow(RuntimeException(errorMessage))
                .`when`(apiService)
                .getFlickrImages(
                    searchText = searchText,
                    page = 1
                )
            val viewModel = ImageViewModel(apiService)
            viewModel.resource.observeForever(apiResponseObserver)
            verify(apiService,atLeast(1)).getFlickrImages(
                searchText = searchText,
                page = 1
            )
            verify(apiResponseObserver).onChanged(
                Resource.error(
                    RuntimeException(errorMessage).toString(),
                    null
                )
            )
            viewModel.resource.removeObserver(apiResponseObserver)
        }
    }
}