package com.amonteiro.a25_05_sdv_rennes_a.exo

import com.amonteiro.a25_05_sdv_rennes_a.model.CarBean

class MyLiveData(value : CarBean) {

    var value = value
        get() = field
        set(newValue) {
            field = newValue
            action(newValue)
        }

    var action :(CarBean)->Unit = {}
        get() = field
        set(newValue) {
            field = newValue
            action(value)
        }
}

fun main() {
    var data = MyLiveData(CarBean("toto"))
    data.value = CarBean("tata")
    data.value.marque = "aaa"

    data.action = {
        println(it)
    }

    data.value = CarBean("bob")
}

fun exo1() {
    //Déclaration
    val lower: (String) -> Unit = { it: String -> println(it.lowercase()) }
    val lower2 = { it: String -> println(it.lowercase()) }
    val lower3: (String) -> Unit = { it -> println(it.lowercase()) }
    val lower4: (String) -> Unit = { println(it.lowercase()) }

    arrayListOf("").joinToString()

    //Appel
    lower("Coucou")

//    hour : prenant un nombre de minutes et retournant le nombre d’heures équivalentes
    val hour :(Int)-> Int = {it/60}
    println(hour(123))
//    max : prenant 2 entiers et retournant le plus grand (Math.max())
    val max = {a:Int, b:Int-> Math.max(a,b)}
//    reverse : retourne le texte à l’envers toto -> otot (.reversed())
    val reverse: (String) -> String = { it.reversed() }

    var minToMinHour : ((Int?) ->Pair<Int,Int>?)? = { if(it==null) null else Pair(it/60, it%60)}

    minToMinHour?.invoke(123)
    minToMinHour = null


}