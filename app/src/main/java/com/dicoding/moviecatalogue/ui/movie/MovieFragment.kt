package com.dicoding.moviecatalogue.ui.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.moviecatalogue.R
import com.dicoding.moviecatalogue.core.data.Resource
import com.dicoding.moviecatalogue.core.domain.model.MovieTvShow
import com.dicoding.moviecatalogue.core.ui.adapter.MovieTvShowAdapter
import com.dicoding.moviecatalogue.databinding.FragmentMovieBinding
import com.dicoding.moviecatalogue.ui.detail.DetailActivity
import com.dicoding.moviecatalogue.ui.detail.DetailActivity.Companion.EXTRA_IS_MOVIE
import com.dicoding.moviecatalogue.ui.detail.DetailActivity.Companion.EXTRA_MOVIE_TV_ID
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment : Fragment(), MovieTvShowAdapter.AdapterCallback {

    private lateinit var binding: FragmentMovieBinding
    private lateinit var movieTvShowAdapter: MovieTvShowAdapter
    private val viewModel: MovieViewModel by viewModel()

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
            if (movies != null) {
                when (movies) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.lytError.visibility = View.GONE
                        movieTvShowAdapter.listData = movies.data ?: listOf()
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.lytError.visibility = View.VISIBLE
                        showSnackBar(movies.message ?: "Terjadi kesalahan")
                    }
                }
            }
        })
    }

    private fun showSnackBar(message: String) {
        val snackBar = Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
        snackBar.view.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.colorError
            )
        )
        snackBar.show()
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
