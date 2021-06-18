package com.example.marsrealestate.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marsrealestate.PhotoGridAdapter
import com.example.marsrealestate.R
import com.example.marsrealestate.models.ResultsFilter
import com.google.android.material.snackbar.Snackbar


import kotlinx.android.synthetic.main.fragment_overview.*
import kotlinx.android.synthetic.main.photo_grid_item.*

class OverviewFragment : Fragment() {

    private lateinit var overviewViewModel: OverviewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overviewViewModel = ViewModelProvider(this).get(OverviewViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PhotoGridAdapter(PhotoGridAdapter.MarsListener {
            Toast.makeText(context, "Price is ${it.price}", Toast.LENGTH_SHORT).show()
        })
        grid_recycler_view.adapter = adapter
        val manager = GridLayoutManager(activity, 2, RecyclerView.VERTICAL, false)
        grid_recycler_view.layoutManager = manager

        overviewViewModel.property.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.filter_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.buy_menu -> {
                overviewViewModel.getMarsRealEstateProperties(ResultsFilter.BUY)
                true
            }
            R.id.rent_menu -> {
                overviewViewModel.getMarsRealEstateProperties(ResultsFilter.RENT)
                true
            }
            R.id.show_all_menu -> {
                overviewViewModel.getMarsRealEstateProperties(ResultsFilter.ALL)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}