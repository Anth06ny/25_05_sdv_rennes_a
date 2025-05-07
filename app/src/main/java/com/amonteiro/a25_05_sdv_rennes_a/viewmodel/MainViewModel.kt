package com.amonteiro.a25_05_sdv_rennes_a.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amonteiro.a25_05_sdv_rennes_a.model.PictureBean
import com.amonteiro.a25_05_sdv_rennes_a.model.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

fun main() {
    val viewModel = MainViewModel()
    viewModel.loadWeathers("")
    viewModel.loadWeathers("Paris")
    //attente
    while (viewModel.runInProgress.value) {
        Thread.sleep(500)
    }
    //Affichage de la liste et du message d'erreur
    println("List : ${viewModel.dataList.value}")
    println("ErrorMessage : ${viewModel.errorMessage.value}")
}

class MainViewModel : ViewModel() {
    //MutableStateFlow est une donnée observable
    val dataList = MutableStateFlow(emptyList<PictureBean>())
    val runInProgress = MutableStateFlow(false)
    val errorMessage = MutableStateFlow("")

    fun loadWeathers(cityName: String) {
        runInProgress.value = true
        errorMessage.value = ""

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val list = WeatherRepository.loadWeathers(cityName)
                dataList.value = list.map { city ->
                    PictureBean(
                        id = city.id,
                        url = city.weather.firstOrNull()?.icon ?: "",
                        title = city.name,
                        longText = """
            Il fait ${city.main.temp} à ${city.name} (id=${city.id}) avec un vent de ${city.wind.speed} m/s
            -Description : ${city.weather.firstOrNull()?.description ?: "-"}
            -Icône : ${city.weather.firstOrNull()?.icon ?: "-"}
        """.trimIndent()
                    )
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                errorMessage.value = e.message ?: "Une erreur est survenue"
            }
            finally {
                runInProgress.value = false
            }
        }


    }
}