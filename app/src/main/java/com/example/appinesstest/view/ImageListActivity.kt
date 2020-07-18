package com.example.appinesstest.view

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appinesstest.R
import com.example.appinesstest.model.Status
import com.example.appinesstest.network.ApiService
import com.example.appinesstest.utils.Constant.flickrPhotosList
import com.example.appinesstest.utils.PaginationScrollListener
import com.example.appinesstest.utils.addData
import com.example.appinesstest.utils.isConnectedToNetwork
import com.example.appinesstest.utils.shortToast
import com.example.appinesstest.viewmodel.ImageViewModel
import com.example.appinesstest.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import java.util.*


class ImageListActivity : AppCompatActivity() {

    private var searchText: String = "Nature"
    private lateinit var gridLayoutManager: GridLayoutManager
    val imageViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory(apiService)).get(ImageViewModel::class.java)
    }
    private val pagedPhotoListAdapter = ImageListAdapter()
    private lateinit var searchView: SearchView
    private val apiService: ApiService by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpToolbar()
        setAdapter()
        setProgressBar()
        checkNetworkAndCall()
        setScrollListener()
        setLoadObserver()
    }

    private fun checkNetworkAndCall() {
        if (isConnectedToNetwork()) {
            imageViewModel.fetchFlickr(searchText)
        } else
            shortToast(getString(R.string.network_error))
    }

    private fun setScrollListener() {
        recycler_view.addOnScrollListener(object : PaginationScrollListener(gridLayoutManager) {
            override fun loadMoreItems() {
                imageViewModel.fetchFlickr(searchText)
            }
        })
    }

    private fun setLoadObserver() {
        imageViewModel.resource.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    imageViewModel.isFirstPage = false
                    it.data?.let { it1 -> flickrPhotosList.addData(it1) }
                    pagedPhotoListAdapter.setData(flickrPhotosList)
                    pagedPhotoListAdapter.notifyDataSetChanged()
                }
                Status.LOADING -> {
                    shortToast("Loading")
                }
                Status.ERROR -> {
                    it.message?.let { it1 -> shortToast(it1) }
                }
            }
        })
    }

    fun setProgressBar() {
        progressBar.visibility = View.VISIBLE
        imageViewModel.isFirstPage = true

    }

    private fun setAdapter() {
        pagedPhotoListAdapter.setData(flickrPhotosList)
        gridLayoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        recycler_view.layoutManager = gridLayoutManager
        recycler_view.adapter = pagedPhotoListAdapter
    }

    private fun setUpToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Nature".toUpperCase(Locale.getDefault())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        setSearchView(menu)
        return true
    }

    private fun setSearchView(menu: Menu) {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.action_search).actionView as SearchView
        val searchEditText = searchView.findViewById<View>(R.id.search_src_text) as EditText
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.maxWidth = Int.MAX_VALUE
        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                refreshSearchView(searchEditText)
            }
            false
        }
    }

    private fun refreshSearchView(searchEditText: EditText) {
        searchText = searchEditText.text.toString().trim()
        supportActionBar!!.title = searchText.toUpperCase(Locale.getDefault())
        flickrPhotosList.clear()
        setProgressBar()
        this.checkNetworkAndCall()
        searchView.onActionViewCollapsed()
        searchView.setQuery("", false)
        searchView.clearFocus()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        return if (id == R.id.action_search) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (!searchView.isIconified) {
            searchView.isIconified = true
            return
        }
        super.onBackPressed()
    }
}
