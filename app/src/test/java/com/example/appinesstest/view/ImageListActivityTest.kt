package com.example.appinesstest.view

import androidx.test.rule.ActivityTestRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ImageListActivityTest {
    @Mock
    private lateinit var imageListActivity: ImageListActivity

    @get:Rule
    val activityRule = ActivityTestRule(ImageListActivity::class.java)

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