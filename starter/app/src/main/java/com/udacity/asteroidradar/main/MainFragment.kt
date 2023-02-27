package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import okhttp3.OkHttpClient
import okhttp3.Request

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    private lateinit var adapter: AsteroidAdapter
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = AsteroidAdapter(AsteroidClickListener { asteroid ->
//            viewModel.onAsteroidClicked(asteroid)
        })
        binding.asteroidRecycler.adapter = adapter

        setObservers()


       /* val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://api.nasa.gov/neo/rest/v1/feed?start_date=2020-02-07&end_date=2020-02-07&api_key=JMDo2pLHkMXDfkSr04w6ZMAgvvUHQeVGUviy5SvR")
            .build()

        val response = client.newCall(request).execute()
        val responseBody = response.body?.string()*/
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

    private fun setObservers() {
        viewModel.asteroidList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.asteroidsList = (it) }
        })

        /*viewModel.pictureOfDay.observe(viewLifecycleOwner, Observer { pictureOfDay ->
            pictureOfDay?.let {
                binding.activityMainImageOfTheDay.contentDescription = pictureOfDay.title
                Glide.with(binding.activityMainImageOfTheDay.context)
                    .load(pictureOfDay.url)
                    .into(binding.activityMainImageOfTheDay)
            }
        })*/
    }

    private fun setupAdapter() {

    }
}
