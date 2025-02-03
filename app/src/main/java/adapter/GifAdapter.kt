package adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gifsearch.R
import models.Gif

class GifAdapter(private val onItemClicked: (Gif) -> Unit) :
    ListAdapter<Gif, GifAdapter.GifViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Gif>() {
        override fun areItemsTheSame(oldItem: Gif, newItem: Gif): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Gif, newItem: Gif): Boolean = oldItem == newItem
    }

    inner class GifViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageViewGif: ImageView = itemView.findViewById(R.id.imageViewGif)
        fun bind(gif: Gif) {
            Glide.with(itemView.context)
                .load(gif.images.original.url)
                .into(imageViewGif)

            itemView.setOnClickListener { onItemClicked(gif) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gif, parent, false)
        return GifViewHolder(view)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
