package ru.xmn.kotlinstarter.features.weather.model


data class WeatherData(
        val city: City,
        val cod: String, //200
        val message: Double, //0.0261896
        val cnt: Int, //14
        val list: List<DayWeatherData>
)

data class City(
        val id: Int, //524901
        val name: String, //Moscow
        val coord: Coord,
        val country: String, //RU
        val population: Int //0
)

data class Coord(
        val lon: Double, //37.6156
        val lat: Double //55.7522
)

data class DayWeatherData(
        val dt: Int, //1507626000
        val temp: Temp,
        val pressure: Double, //1005.69
        val humidity: Int, //81
        val weather: List<Weather>,
        val speed: Double, //3.72
        val deg: Int, //211
        val clouds: Int //80
)

data class Weather(
        val id: Int, //803
        val main: String, //Clouds
        val description: String, //broken clouds
        val icon: String //04d
)

data class Temp(
        val day: Double, //8.25
        val min: Double, //6.86
        val max: Double, //8.25
        val night: Double, //6.87
        val eve: Double, //7.4
        val morn: Double //8.25
)