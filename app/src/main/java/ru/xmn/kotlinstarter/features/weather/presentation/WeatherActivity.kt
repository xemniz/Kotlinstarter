package ru.xmn.kotlinstarter.features.weather.presentation

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_weather.*
import org.jetbrains.anko.toast
import ru.xmn.common.adapter.LastAdapter
import ru.xmn.common.extensions.gone
import ru.xmn.common.extensions.visible
import ru.xmn.kotlinstarter.R
import ru.xmn.kotlinstarter.features.weather.model.WeatherData

class WeatherActivity : AppCompatActivity() {
    lateinit var weatherViewModel: WeatherViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        setupToolbar()
        setupViewModel()
        setupRecyclerView()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun setupViewModel() {
        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)
        weatherViewModel.weatherLiveData.observe(this, Observer {
            when (it) {
                is WeatherState.Loading -> showLoading()
                is WeatherState.Success -> showValue(it.value)
                is WeatherState.Error -> showError()
            }
        })
    }

    fun setupRecyclerView() {
        weatherRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@WeatherActivity)
            adapter = LastAdapter()
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun showValue(value: WeatherData) {
        supportActionBar?.title = value.city.name
        loading.gone()
        (weatherRecyclerView.adapter as LastAdapter).items = value.list.map { WeatherListItem(it) }
    }

    private fun showError() {
        toast("error")
        loading.gone()
    }

    private fun showLoading() {
        loading.visible()
    }
}

