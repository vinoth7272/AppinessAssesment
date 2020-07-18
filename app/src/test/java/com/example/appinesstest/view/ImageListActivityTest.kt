package com.example.appinesstest.view

import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ImageListActivityTest {
    @Mock
    private lateinit var imageListActivity: ImageListActivity

    @Before
    fun setUp() {
        imageListActivity =  mock(ImageListActivity::class.java)

    }

    @Test
    fun progressBarVisibility_Test() {
        doNothing().`when`(imageListActivity).setProgressBar()
        verify(imageListActivity, times(1)).setProgressBar()
    }
}