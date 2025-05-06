package com.amonteiro.a25_05_sdv_rennes_a.model

import com.amonteiro.a25_05_sdv_rennes_a.model.WeatherRepository.loadRandomUser
import com.amonteiro.a25_05_sdv_rennes_a.model.WeatherRepository.loadWeathers
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.jvm.java

//Utilisation
fun main() {


    //Lance la requête et met le corps de la réponse dans html
//    var html: String = WeatherRepository.sendGet("https://www.google.fr")
//    //Affiche l'HTML
//    println("html=$html")

    //WeatherRepository.loadWeathers("nice")

//    val user = WeatherRepository.loadRandomUser()
//    println("""
//        Il s'appelle ${user.name} pour le contacter :
//        Phone : ${user.coord?.phone ?: "-"}
//        Mail : ${user.coord?.mail ?: "-"}
//
//
//        -----------------------------
//    """.trimIndent())
//
//
//    for(u in WeatherRepository.loadRandomUsers()){
//        println("""
//        Il s'appelle ${u.name} pour le contacter :
//        Phone : ${u.coord?.phone ?: "-"}
//        Mail : ${u.coord?.mail ?: "-"}
//    """.trimIndent())
//    }

    val list = WeatherRepository.loadWeathers("nice")
    println(list)

    for( city in list){
        println("""
            Il fait ${city.main.temp} à ${city.name} (id=${city.id}) avec un vent de ${city.wind.speed} m/s
            -Description : ${city.weather.firstOrNull()?.description ?: "-"}
            -Icône : ${city.weather.firstOrNull()?.icon ?: "-"}
        """.trimIndent())
    }
}

object WeatherRepository {
    val client = OkHttpClient()
    val gson = Gson()

    fun loadWeathers(cityName : String): List<WeatherBean> {
       val json =  sendGet("https://api.openweathermap.org/data/2.5/find?q=$cityName&cnt=5&appid=b80967f0a6bd10d23e44848547b26550&units=metric&lang=fr")

        val res = gson.fromJson(json, WeatherResult::class.java)

        res.list.forEach {
            it.weather.forEach {
                it.icon = "https://openweathermap.org/img/wn/${it.icon}@4x.png"
            }
        }


        return res.list
    }

    fun loadRandomUser(): UserBean {
        val json =  sendGet("https://www.amonteiro.fr/api/randomuser")
        val user = gson.fromJson(json, UserBean::class.java)

        return user
    }

    fun loadRandomUsers(): Array<UserBean> {
        val json =  sendGet("https://www.amonteiro.fr/api/randomusers")
        val user = gson.fromJson(json, Array<UserBean>::class.java)

        return user
    }

    //Méthode qui prend en entrée une url, execute la requête
    //Retourne le code HTML/JSON reçu
    fun sendGet(url: String): String {
        println("url : $url")
        //Création de la requête
        val request = Request.Builder().url(url).build()
        //Execution de la requête
        return client.newCall(request).execute().use { //it:Response
            //use permet de fermer la réponse qu'il y ait ou non une exception
            //Analyse du code retour
            if (!it.isSuccessful) {
                throw Exception("Erreur serveur :${it.code}\n${it.body.string()}")
            }
            //Résultat de la requête
            it.body.string()
        }
    }
}

/* -------------------------------- */
// User
/* -------------------------------- */
data class UserBean(var name:String, var age:Int, var coord :  CoordBean? )
data class CoordBean(var phone:String?, var mail:String?)

/* -------------------------------- */
// Weather
/* -------------------------------- */
data class WeatherResult(var list : List<WeatherBean>)
data class WeatherBean(var id : Int, var name:String, var main:TempBean, var weather: List<DescriptionBean>, var wind: WindBean)
data class TempBean(var temp : Double)

data class DescriptionBean(
    var description: String,
    var icon: String,
)

data class WindBean(
    var speed: Double
)