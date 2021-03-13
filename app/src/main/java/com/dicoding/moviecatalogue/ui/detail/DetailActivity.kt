package com.dicoding.moviecatalogue.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.moviecatalogue.BuildConfig
import com.dicoding.moviecatalogue.R
import com.dicoding.moviecatalogue.core.data.Resource
import com.dicoding.moviecatalogue.core.domain.model.MovieTvShow
import com.dicoding.moviecatalogue.databinding.ActivityDetailBinding
import com.dicoding.moviecatalogue.databinding.ContentDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_IS_MOVIE = "extra_is_movie"
        const val EXTRA_MOVIE_TV_ID = "extra_movie_tv_id"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var bindingDetail: ContentDetailBinding
    private val viewModel: DetailViewModel by viewModel()
    private var isMovie = false
    private var isFavorited = false
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        bindingDetail = binding.detailContent
        setContentView(binding.root)

        initData()

        supportActionBar?.apply {
            title = if (isMovie) getString(R.string.detail_movie) else getString(R.string.detail_tv_show)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun initData() {
        val extras = intent.extras
        if (extras != null) {
            isMovie = extras.getBoolean(EXTRA_IS_MOVIE, false)
            val movieTvID = extras.getInt(EXTRA_MOVIE_TV_ID, 0)
            viewModel.isMovie(isMovie)
            if (movieTvID != 0) {
                binding.progressBar.visibility = View.VISIBLE
                binding.rlDetailContent.visibility = View.INVISIBLE
                viewModel.setSelectedMovieTv(movieTvID)
                initObserver()
            }
        }
    }

    private fun initObserver() {
        viewModel.movieTvShow.observe(this, { data ->
            when (data) {
                is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rlDetailContent.visibility = View.VISIBLE
                    loadData(data.data)
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.lytError.visibility = View.VISIBLE
                    binding.tvMessage.text = data.message
                }
            }
        })
    }

    private fun loadData(data: MovieTvShow?) {
        bindingDetail.tvTitle.text = data?.title
        bindingDetail.tvReleaseDate.text = data?.releaseDate
        bindingDetail.tvCategory.text = data?.genres
        bindingDetail.tvOverview.text = data?.overview

        Glide.with(this)
            .load(BuildConfig.BASE_IMAGE_URL + data?.posterPath)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
            .into(bindingDetail.ivPoster)

        bindingDetail.imgShare.setOnClickListener {
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder.from(this).apply {
                setType(mimeType)
                setChooserTitle("Bagikan aplikasi ini sekarang.")
                setText(resources.getString(
                    if (isMovie) R.string.share_movie_text else R.string.share_tv_show_text,
                    data?.title
                ))
                startChooser()
            }
        }

        isFavorited = data?.favorited ?: false
        setFavoritedkState()
    }

    private fun setFavoritedkState() {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.action_favourite)
        if (isFavorited) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favourite_filled)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favourite_stroke)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu
        setFavoritedkState()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.action_favourite -> {
                viewModel.setFavorite()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}