package com.example.appinesstest.utils

import android.content.Context
import android.net.ConnectivityManager
import android.widget.ImageView
import android.widget.Toast
import com.example.appinesstest.R
import com.squareup.picasso.Picasso

fun ImageView.loadUrl(url: String?) {
    Picasso.get().load(url).fit()
        .placeholder(R.drawable.appiness)
        .into(this)
}

fun <T> ArrayList<T>.addData(list: List<T>): ArrayList<T> {
    for (element in list) {
        if (!this.contains(element)) {
            this.add(element)
        }
    }
    return this
}

fun Context.shortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

//To Check the internet connection
fun Context.isConnectedToNetwork(): Boolean {
    val connectivityManager: ConnectivityManager? =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    return connectivityManager?.activeNetworkInfo?.isConnectedOrConnecting ?: false
}


