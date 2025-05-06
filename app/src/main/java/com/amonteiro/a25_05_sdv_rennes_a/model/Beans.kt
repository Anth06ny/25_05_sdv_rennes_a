package com.amonteiro.a25_05_sdv_rennes_a.model

import android.R.attr.name
import java.util.Random

fun main() {
    var t1 = ThermometerBean(min = -20, max = 50, value = 0)

    println("Température de ${t1?.value}") // 0

    //Cas qui marche
    t1.value = 10
    println("Température de ${t1.value}") // 10 attendu

    //Borne minimum
    t1.value = -30
    println("Température de ${t1.value}") // -20 attendu

    //Borne maximum
    t1.value = 100
    println("Température de ${t1.value}") // 50 attendu

    //Pour les plus rapides : Cas de départ
    t1 = ThermometerBean(min = -20, max = 50, value = -100)
    println("Température de ${t1.value}") // -20 attendu


}

data class PictureBean(val id:Int, val url: String, val title: String, val longText: String)

class RandomName {
    private val list = arrayListOf("Bobby", "bob", "toto")
    private var oldValue = ""

    fun add(name: String?) =
        if (!name.isNullOrBlank() && name !in list) {
            list.add(name)
        }
        else false

    fun next() = list.random()

    fun addAll(vararg names:String){
        for(name in names){
            add(name)
        }
    }

    fun nextDiff(): String {
        var name = next()
        while(name == oldValue) {
            name = next()
        }

        oldValue = name
        return oldValue
    }

    fun nextDiff2()=  list.filter { it != oldValue  }.random().also { oldValue = it }

    fun next2() = Pair(nextDiff(), nextDiff())

}

class ThermometerBean(var min: Int, var max: Int, value: Int) {

    var value = value.coerceIn(min, max)
        set(newValue) {
            field = newValue.coerceIn(min, max)
        }

    companion object {
        fun getCelsiusThermometer() = ThermometerBean(-30, 50, 0)

        fun getFahrenheitThermometer() = ThermometerBean(20, 120, 32)
    }

}

class PrintRandomIntBean(max: Int) {

    private val random = Random()

    init {
        println(random.nextInt(max))
        println(random.nextInt(max))
        println(random.nextInt(max))
    }

}

class HouseBean(var color: String, width: Int, length: Int) {
    var area = width * length
}

data class CarBean(var marque: String? = null, var model: String = "") {
    var color = ""
}