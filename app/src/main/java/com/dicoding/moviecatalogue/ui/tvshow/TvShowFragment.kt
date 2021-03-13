package com.dicoding.moviecatalogue.ui.tvshow

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
import com.dicoding.moviecatalogue.databinding.FragmentTvShowBinding
import com.dicoding.moviecatalogue.ui.detail.DetailActivity
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvShowFragment : Fragment(), MovieTvShowAdapter.AdapterCallback {

    private lateinit var binding: FragmentTvShowBinding
    private lateinit var movieTvShowAdapter: MovieTvShowAdapter
    private val viewModel: TvShowViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTvShowBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            movieTvShowAdapter = MovieTvShowAdapter(this)

            initObserver()

            binding.rvTvShow.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = movieTvShowAdapter
            }
        }
    }

    private fun initObserver() {
        viewModel.tvShow.observe(viewLifecycleOwner, { tvShows ->
            if (tvShows != null) {
                when (tvShows) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.lytError.visibility = View.GONE
                        movieTvShowAdapter.listData = tvShows.data ?: listOf()
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.lytError.visibility = View.VISIBLE
                        showSnackBar(tvShows.message ?: "Terjadi kesalahan")
                    }
                }
            }
        })
    }

    private fun showSnackBar(message: String) {
        val snackBar = Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
        snackBar.view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorError))
        snackBar.show()
    }

    override fun onItemClick(movieTvID: Int) {
        val intent = Intent(requireContext(), DetailActivity::class.java).apply {
            putExtra(DetailActivity.EXTRA_IS_MOVIE, false)
            putExtra(DetailActivity.EXTRA_MOVIE_TV_ID, movieTvID)
        }
        startActivity(intent)
    }

    override fun onShareClick(data: MovieTvShow) {
        if (activity != null) {
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder.from(requireActivity()).apply {
                setType(mimeType)
                setChooserTitle("Bagikan aplikasi ini sekarang.")
                setText(resources.getString(R.string.share_tv_show_text, data.title))
                startChooser()
            }
        }
    }
}