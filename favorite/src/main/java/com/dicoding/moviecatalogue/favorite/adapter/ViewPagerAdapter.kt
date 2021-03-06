package com.dicoding.moviecatalogue.favorite.adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dicoding.moviecatalogue.R
import com.dicoding.moviecatalogue.favorite.ui.movie.MovieFavoriteFragment
import com.dicoding.moviecatalogue.favorite.ui.tvshow.TvShowFavoriteFragment

class ViewPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.movies, R.string.tv_show)
    }

    override fun getCount() = TAB_TITLES.size

    override fun getItem(position: Int): Fragment =
        when (position) {
            0 -> MovieFavoriteFragment()
            1 -> TvShowFavoriteFragment()
            else -> Fragment()
        }

    override fun getPageTitle(position: Int): CharSequence = context.resources.getString(TAB_TITLES[position])

}