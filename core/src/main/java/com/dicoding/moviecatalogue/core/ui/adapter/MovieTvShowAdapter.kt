package com.dicoding.moviecatalogue.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.moviecatalogue.core.BuildConfig
import com.dicoding.moviecatalogue.core.R
import com.dicoding.moviecatalogue.core.databinding.ItemMovieTvShowBinding
import com.dicoding.moviecatalogue.core.domain.model.MovieTvShow

class MovieTvShowAdapter(private val listener: AdapterCallback) : RecyclerView.Adapter<MovieTvShowAdapter.ViewHolder>() {

    var listData = listOf<MovieTvShow>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemMovieTvShowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data, listener)
    }

    override fun getItemCount() = listData.size

    class ViewHolder(private val binding: ItemMovieTvShowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MovieTvShow, listener: AdapterCallback) {
            with(binding) {
                Glide.with(root)
                    .load(BuildConfig.BASE_IMAGE_URL + data.posterPath)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                    .into(ivPoster)

                tvTitle.text = data.title
                tvReleaseDate.text = data.releaseDate
                tvDesc.text = data.overview

                imgShare.setOnClickListener {
                    listener.onShareClick(data)
                }

                this.root.setOnClickListener {
                    listener.onItemClick(data.id)
                }
            }
        }
    }

    interface AdapterCallback {
        fun onItemClick(movieTvID: Int)
        fun onShareClick(data: MovieTvShow)
    }
}