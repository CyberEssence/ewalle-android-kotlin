package com.example.ewalle.presentation.home

import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.ewalle.R
import com.example.ewalle.data.model.home.Services
import com.example.ewalle.data.net.dpToPx
import com.example.ewalle.databinding.CardviewServiceBinding

class ServicesAdapter(
    private var items: List<Services> = listOf(),
    private val onClick: (Int) -> Unit
) :
    RecyclerView.Adapter<ServicesViewHolder>() {

    fun setListUsers(newItems: List<Services>) {
        val lastSize = items.size
        val newSize = newItems.size - 1
        items = newItems.toList()
        if (newSize > lastSize) {
            notifyItemRangeInserted(lastSize, newSize)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.cardview_service, parent, false)
        return ServicesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServicesViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            onClick(position)
        }
    }

    override fun getItemCount(): Int = items.size
}

class ServicesViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val binding = CardviewServiceBinding.bind(view)

    fun bind(service: Services) {
        binding.serviceName.text = service.nameService
        Glide.with(view).load(service.urlImage).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .apply(RequestOptions.circleCropTransform())
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.serviceImage.setImageResource(R.drawable.ic_airplane)
                    return true
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            }).into(binding.serviceImage)
    }
}

class ServicesItemDecoration :
    RecyclerView.ItemDecoration() {

    companion object {
        private const val OFFSET_HORISONTAL = 17
        private const val OFFSET_BOTTOM = 10
        private const val LAST_SERVISES = 5
    }

    override fun getItemOffsets(rect: Rect, v: View, parent: RecyclerView, s: RecyclerView.State) {
        parent.adapter?.let { adapter ->
            val childAdapterPosition = parent.getChildAdapterPosition(v)
                .let { if (it == RecyclerView.NO_POSITION) return else it }
            rect.left = OFFSET_HORISONTAL.dpToPx(v.context).toInt()
            rect.bottom = if (childAdapterPosition < adapter.itemCount - LAST_SERVISES) {
                OFFSET_BOTTOM.dpToPx(v.context).toInt()
            } else {
                0
            }
        }
    }
}