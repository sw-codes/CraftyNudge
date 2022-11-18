package com.swcode.craftynudge

import kotlin.random.Random

class LayerChooser {
    private val layerOptions: List<String> = listOf("Stamping", "Ink", "Paint", "Tissue Paper", "Gelatos",
        "Watercolour", "Stenciling", "Doodling", "Washi Tape", "Your Choice")

    fun chooseLayer(): String {
        val randomNumber: Int = Random.nextInt(0, layerOptions.size)
        return layerOptions[randomNumber]
    }
}