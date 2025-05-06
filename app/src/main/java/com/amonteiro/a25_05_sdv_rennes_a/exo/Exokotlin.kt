package com.amonteiro.a25_05_sdv_rennes_a.exo

import android.R.attr.text
import android.util.Log.v
import kotlin.random.Random

fun main() {

    println(boulangerie(1,2,3))
    println(boulangerie(nbb = 3, nbSand = 5))
    println(boulangerie())
}


fun boulangerie(nbc : Int = 0, nbSand : Int = 0, nbb:Int=0) : Double
    =  nbc * PRICE_CROISSANT + nbSand * PRICE_SANDWITCH + nbb * PRICE_BAGUETTE
