package com.mobileoak.yelpsearch.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mobileoak.yelpsearch.R
import com.mobileoak.yelpsearch.viewmodel.MainViewModel
import com.mobileoak.yelpsearch.viewmodel.SearchState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var searchButton: Button
    private lateinit var locationEdit: EditText
    private lateinit var subjectEdit: EditText

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchButton = findViewById(R.id.searchButton)
        searchButton.setOnClickListener {
            val location = locationEdit.text.toString()
            val subject = subjectEdit.text.toString()
            viewModel.onSearchClicked(location, subject)
        }

        locationEdit = findViewById(R.id.editLocation)
        subjectEdit = findViewById(R.id.editSubject)

        viewModel.state.observe(this) { state ->
            when (state) {
                SearchState.ReadyForInput -> onReadyForInput()
                SearchState.Waiting -> onWaitingForNetwork()
                SearchState.ReadyToShowResults -> onReadyToShowResults()
                is SearchState.Error -> onError(state.title, state.message)
            }
        }

    }

    private fun onError(title: String, message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Ok", null)
            .show()
    }

    private fun onReadyToShowResults() {
        val resultsIntent = Intent(this, ResultsActivity::class.java)
        startActivity(resultsIntent)
    }

    private fun onWaitingForNetwork() {
        // TODO: show loading indicator
    }

    private fun onReadyForInput() {
        // nothing to do
    }

}