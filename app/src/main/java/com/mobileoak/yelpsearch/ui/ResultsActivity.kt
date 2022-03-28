package com.mobileoak.yelpsearch.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobileoak.yelpsearch.R
import com.mobileoak.yelpsearch.network.Business
import com.mobileoak.yelpsearch.viewmodel.ResultsState
import com.mobileoak.yelpsearch.viewmodel.ResultsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: BusinessAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var loadingText: TextView

    private val viewModel: ResultsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        viewManager = LinearLayoutManager(this)
        viewAdapter = BusinessAdapter()
        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        loadingText = findViewById(R.id.loading_text)

        viewModel.state.observe(this) { state ->
            when(state) {
                ResultsState.Empty -> onEmpty()
                is ResultsState.Loaded -> onDataLoaded(state.data)
            }
        }
    }

    private fun onDataLoaded(data: List<Business>) {
        recyclerView.visibility = View.VISIBLE
        loadingText.visibility = View.GONE

        viewAdapter.updateData(data)
        viewAdapter.notifyDataSetChanged()
    }

    private fun onEmpty() {
        recyclerView.visibility = View.GONE
        loadingText.visibility = View.VISIBLE
    }


}