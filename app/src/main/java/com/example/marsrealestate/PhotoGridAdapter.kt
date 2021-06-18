package com.example.marsrealestate

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marsrealestate.models.Mars
import kotlinx.android.synthetic.main.photo_grid_item.view.*

class PhotoGridAdapter(private val clickListener: MarsListener): ListAdapter<Mars, PhotoGridAdapter.PhotoViewHolder>(DiffUtilCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)
        val uri = currentItem.imgSrcUrl.toUri().buildUpon().scheme("https").build()
        holder.bind(currentItem, uri, clickListener)
    }

    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val loadedImageView: ImageView = itemView.loaded_image_view

        fun bind(currentItem: Mars, uri: Uri?, clickListener: MarsListener) {
            Glide.with(loadedImageView.context).load(uri).into(loadedImageView)
            itemView.setOnClickListener{clickListener(currentItem)}
            when(currentItem.type){
                "buy" -> itemView.buyRentImage.setImageResource(R.drawable.buy)
                "rent" -> itemView.buyRentImage.setImageResource(R.drawable.rent)
            }
        }

        companion object {
            fun from(parent: ViewGroup): PhotoViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val itemView = layoutInflater.inflate(R.layout.photo_grid_item, parent, false)
                return PhotoViewHolder(itemView)
            }
        }
    }

    class DiffUtilCallback: DiffUtil.ItemCallback<Mars>() {
        override fun areItemsTheSame(oldItem: Mars, newItem: Mars): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Mars, newItem: Mars): Boolean {
            return oldItem == newItem
        }

    }

    class MarsListener(val clickListener: (mars: Mars) -> Unit){
        operator fun invoke(currentItem: Mars) {
            clickListener(currentItem)
        }

    }

}