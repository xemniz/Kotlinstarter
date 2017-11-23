package ru.xmn.kotlinstarter.features.weather.presentation

import android.view.View
import kotlinx.android.synthetic.main.item_weather.view.*
import org.jetbrains.anko.toast
import ru.xmn.common.adapter.LastAdapter
import ru.xmn.kotlinstarter.R
import ru.xmn.kotlinstarter.features.weather.model.DayWeatherData
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class WeatherListItem(private val dayWeatherData: DayWeatherData) : LastAdapter.Item() {
    override fun compare(anotherItemValue: Any) = dayWeatherData.dt == (anotherItemValue as? DayWeatherData)?.dt

    override fun layoutId() = R.layout.item_weather

    override fun bindOn(view: View) {
        view.apply {
            val timestamp = Timestamp(dayWeatherData.dt.toLong() * 1000)
            val date = Date(timestamp.time)
            val formattedDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(date)
            dateValue.text = context.getString(R.string.dateValue, formattedDate)
            dayValue.text = context.getString(R.string.dayValue, dayWeatherData.temp.day)
            minValue.text = context.getString(R.string.minValue, dayWeatherData.temp.min)
            maxValue.text = context.getString(R.string.maxValue, dayWeatherData.temp.max)
            weatherValue.text = context.getString(R.string.weatherValue, dayWeatherData.weather[0].main)

            setOnClickListener { context.toast(formattedDate) }
        }
    }
}