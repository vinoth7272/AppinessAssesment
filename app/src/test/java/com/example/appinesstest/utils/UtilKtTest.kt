package com.example.appinesstest.utils

import android.content.Context
import android.widget.Toast
import com.example.appinesstest.view.ImageListActivity
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class UtilKtTest {

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var toast: Toast

    @Test
    fun showToast_test() {
        doNothing().`when`(context).shortToast("message")
        verify(context, times(1)).shortToast("Message")
    }

    @Test
    fun isConnectionTest(){
        doReturn(true).`when`(context).isConnectedToNetwork()
        verify(context).isConnectedToNetwork()
    }
}