package com.example.appinesstest.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.appinesstest.R
import com.example.appinesstest.model.FlickrPhoto
import com.example.appinesstest.utils.loadUrl
import kotlinx.android.synthetic.main.list_item.view.*


class ImageListAdapter :
    RecyclerView.Adapter<ImageListAdapter.ImageViewHolder>() {
    var flickrImageList = ArrayList<FlickrPhoto>()
    fun setData(flickrPhotoList: ArrayList<FlickrPhoto>) {
        flickrImageList = flickrPhotoList
    }

    class ImageViewHolder(itemView: View) : ViewHolder(itemView) {
        fun bind(photo: FlickrPhoto) {
            with(photo) {
                itemView.tvName.text = title
                val imageUrl = "https://farm$farm.staticflickr.com/$server/${id}_${secret}_m.j\n" +
                        "pg"
                itemView.ivImage.loadUrl(imageUrl)
            }
        }

    }

    override fun getItemCount(): Int {
        return flickrImageList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item, parent, false)
        return ImageViewHolder(view)
    }


    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        flickrImageList[position].let {
            holder.bind(it)
        }

    }
}