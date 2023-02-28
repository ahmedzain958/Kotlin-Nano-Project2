package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import okhttp3.OkHttpClient
import okhttp3.Request

class MainFragment : Fragment() {

    lateinit var viewModel: MainViewModel
    private lateinit var adapter: AsteroidAdapter
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(
            this,
            MainViewModel.Factory(requireActivity().application)
        )[MainViewModel::class.java]

        binding.viewModel = viewModel
        setHasOptionsMenu(true)

        adapter = AsteroidAdapter(AsteroidClickListener { asteroid ->
            view?.findNavController()?.navigate(MainFragmentDirections.actionShowDetail(asteroid))
        })
        binding.asteroidRecycler.adapter = adapter
        setObservers()
        return binding.root
    }

    private fun setObservers() {
        viewModel.asteroidList.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it) {
                    binding.asteroidRecycler.scrollToPosition(0)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_week_menu -> {
                viewModel.updateFilter(FILTER_TYPE.WEEK)
                return true
            }
            R.id.show_today_menu -> {
                viewModel.updateFilter(FILTER_TYPE.TODAY)
                return true
            }
            R.id.show_saved_menu -> {
                viewModel.updateFilter(FILTER_TYPE.SAVE)
                return true
            }
        }
        return true
    }
}
