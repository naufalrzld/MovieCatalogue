package com.dicoding.moviecatalogue.favorite.ui.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.moviecatalogue.R
import com.dicoding.moviecatalogue.core.domain.model.MovieTvShow
import com.dicoding.moviecatalogue.databinding.FragmentMovieBinding
import com.dicoding.moviecatalogue.core.ui.adapter.MovieTvShowAdapter
import com.dicoding.moviecatalogue.ui.detail.DetailActivity
import com.dicoding.moviecatalogue.ui.detail.DetailActivity.Companion.EXTRA_IS_MOVIE
import com.dicoding.moviecatalogue.ui.detail.DetailActivity.Companion.EXTRA_MOVIE_TV_ID
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFavoriteFragment : Fragment(), MovieTvShowAdapter.AdapterCallback {

    private lateinit var binding: FragmentMovieBinding
    private lateinit var movieTvShowAdapter: MovieTvShowAdapter
    private val viewModel: MovieFavoriteViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            movieTvShowAdapter = MovieTvShowAdapter(this)

            binding.progressBar.visibility = View.VISIBLE
            initObserver()

            binding.rvMovies.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = movieTvShowAdapter
            }
        }
    }

    private fun initObserver() {
        viewModel.movies.observe(viewLifecycleOwner, { movies ->
            binding.progressBar.visibility = View.GONE
            movieTvShowAdapter.listData = movies
            if (movies.isEmpty()) binding.lytError.visibility = View.VISIBLE
            else binding.lytError.visibility = View.GONE
        })
    }

    override fun onItemClick(movieTvID: Int) {
        val intent = Intent(requireContext(), DetailActivity::class.java).apply {
            putExtra(EXTRA_IS_MOVIE, true)
            putExtra(EXTRA_MOVIE_TV_ID, movieTvID)
        }
        startActivity(intent)
    }

    override fun onShareClick(data: MovieTvShow) {
        if (activity != null) {
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder.from(requireActivity()).apply {
                setType(mimeType)
                setChooserTitle("Bagikan aplikasi ini sekarang.")
                setText(resources.getString(R.string.share_movie_text, data.title))
                startChooser()
            }
        }
    }
}