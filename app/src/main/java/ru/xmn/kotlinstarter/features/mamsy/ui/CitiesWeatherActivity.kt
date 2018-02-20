package ru.xmn.kotlinstarter.features.mamsy.ui

import android.arch.lifecycle.ViewModel
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import kotlinx.android.synthetic.main.activity_towns_weather.*
import ru.xmn.common.adapter.lastAdapterItems
import ru.xmn.common.extensions.provideViewModel
import ru.xmn.common.extensions.viewModelProvider
import ru.xmn.common.observeNonNull
import ru.xmn.kotlinstarter.R
import ru.xmn.kotlinstarter.application.App
import ru.xmn.kotlinstarter.features.mamsy.presentation.CitiesViewModel


class CitiesWeatherActivity : AppCompatActivity() {
    private lateinit var componentViewModel: ComponentViewModel
    private lateinit var citiesViewModel: CitiesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_towns_weather)
        setSupportActionBar(toolbar)
        componentViewModel = provideViewModel { ComponentViewModel() }
        citiesViewModel = provideViewModel { componentViewModel.component.citiesViewModel() }

        setupRecyclerView()
        citiesViewModel.cities.observeNonNull(this) {
            towns_list.lastAdapterItems = it.map { CityRecyclerItem(it, this, componentViewModel.component) }
        }
    }

    private fun setupRecyclerView() {
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(towns_list)
        towns_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }
}

class ComponentViewModel(): ViewModel(){
    val component = App.component.weatherComponent().build()
}