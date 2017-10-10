package ru.xmn.kotlinstarter.screens.weather

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_weather.*
import ru.xmn.common.adapter.BaseAdapter
import ru.xmn.common.extensions.gone
import ru.xmn.common.extensions.visible
import ru.xmn.kotlinstarter.R

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
            adapter = BaseAdapter()
        }
    }

    private fun showValue(value: WeatherData) {
        loading.gone()
        (weatherRecyclerView.adapter as BaseAdapter).items = value.list.map { WeatherItem(it) }
    }

    private fun showError() {
        loading.gone()
    }

    private fun showLoading() {
        loading.visible()
    }
}

class WeatherItem(val dayWeatherData: DayWeatherData) : BaseAdapter.Item() {

    override fun layoutId() = R.layout.item_weather

    override fun bindOn(view: View) {

    }

    override fun <T : BaseAdapter.Item> compare(anotherItem: T): Boolean {
        dayWeatherData.dt == anotherItem.dayWeatherData.dt
    }
}

