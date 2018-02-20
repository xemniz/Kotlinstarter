package ru.xmn.kotlinstarter.features.mamsy.ui

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.item_city_weather.view.*
import kotlinx.android.synthetic.main.item_day_weather.view.*
import ru.xmn.common.adapter.LastAdapter
import ru.xmn.common.adapter.lastAdapterItems
import ru.xmn.common.extensions.provideViewModel
import ru.xmn.common.observeNonNull
import ru.xmn.kotlinstarter.R
import ru.xmn.kotlinstarter.features.mamsy.domain.CityData
import ru.xmn.kotlinstarter.features.mamsy.domain.WeatherComponent
import ru.xmn.kotlinstarter.features.mamsy.domain.WeatherDayData
import ru.xmn.kotlinstarter.features.mamsy.presentation.WeakWeatherDataState

class CityRecyclerItem(override val value: CityData, private val activity: AppCompatActivity, private val component: WeatherComponent) : LastAdapter.Item<CityData> {
    override fun bindOn(view: View, scopeOwner: LastAdapter.ScopeOwner) {
        val cityWeatherViewModel = activity.provideViewModel(value.name) { component.cityWeatherViewModel() }
        if (cityWeatherViewModel.cityWeather.value == null) {
            cityWeatherViewModel.loadWeekWeather(value)
        }

        view.apply {
            city_name.text = value.name
            day_list.layoutManager = LinearLayoutManager(activity)
            cityWeatherViewModel.cityWeather.observeNonNull(activity) {
                when (it) {
                    WeakWeatherDataState.Loading -> {
                    }
                    is WeakWeatherDataState.Success -> {
                        day_list.lastAdapterItems = it.data.days.map { WeatherDayRecyclerItem(it) }
                    }
                    is WeakWeatherDataState.Error -> {
                    }
                }
            }
        }

        scopeOwner.setScope(object : LastAdapter.Scope {
            override fun onClear() {
                cityWeatherViewModel.cityWeather.removeObservers(activity)
            }

        })
    }

    override fun sameItem(anotherItem: LastAdapter.Item<*>): Boolean {
        return (anotherItem as? CityRecyclerItem)?.value?.name == value.name
    }

    override fun layoutId() = R.layout.item_city_weather

}

class WeatherDayRecyclerItem(override val value: WeatherDayData) : LastAdapter.Item<WeatherDayData> {
    override fun bindOn(view: View, scopeOwner: LastAdapter.ScopeOwner) {
        view.apply {
            day_of_week.text = value.day
            weather_text.text = value.weather
            day_temperature.text = value.temperatureDay
            night_temperature.text = value.temperatureNight
        }
    }

    override fun layoutId() = R.layout.item_day_weather

    override fun sameItem(anotherItem: LastAdapter.Item<*>): Boolean {
        return (anotherItem as? WeatherDayRecyclerItem)?.value?.day == value.day
    }
}