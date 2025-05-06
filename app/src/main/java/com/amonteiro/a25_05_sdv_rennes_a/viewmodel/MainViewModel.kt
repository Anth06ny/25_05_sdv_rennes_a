package com.amonteiro.a25_05_sdv_rennes_a.viewmodel

import androidx.lifecycle.ViewModel
import com.amonteiro.a25_05_sdv_rennes_a.model.PictureBean
import com.amonteiro.a25_05_sdv_rennes_a.model.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow

fun main() {
    val viewModel = MainViewModel()
    viewModel.loadWeathers("Nice")
    //Affichage de la liste (qui doit être remplie) contenue dans la donnée observable
    println("List : ${viewModel.dataList.value}")
}

class MainViewModel : ViewModel() {
    //MutableStateFlow est une donnée observable
    val dataList = MutableStateFlow(emptyList<PictureBean>())

    fun loadWeathers(cityName: String) {
        val list = WeatherRepository.loadWeathers(cityName)
        dataList.value = list.map { city ->
            PictureBean(
                id = city.id,
                url  = city.weather.firstOrNull()?.icon ?: "",
                title = city.name,
                longText = """
            Il fait ${city.main.temp} à ${city.name} (id=${city.id}) avec un vent de ${city.wind.speed} m/s
            -Description : ${city.weather.firstOrNull()?.description ?: "-"}
            -Icône : ${city.weather.firstOrNull()?.icon ?: "-"}
        """.trimIndent()
            )
        }
    }
}