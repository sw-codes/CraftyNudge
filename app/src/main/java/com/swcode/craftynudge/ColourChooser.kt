package com.swcode.craftynudge

import kotlin.random.Random

class ColourChooser {
    private val colourHexValues: List<String> = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F")
    private var chosenColourString = ""
    private val colourRelationships: List<String> = listOf("Monochrome", "Complimentary", "Split Complementary", "Analogous", "Triad", "Your Choice")

    fun chooseColour(): String {
        chosenColourString = ""
        for (i in 1..6) {
            var randomNumber: Int = Random.nextInt(0, colourHexValues.size)
            chosenColourString += colourHexValues[randomNumber]
        }
        return chosenColourString
    }

    fun chooseColourRelationship(): String {
        val randomNumber: Int = Random.nextInt(0, colourRelationships.size)
        return colourRelationships[randomNumber]
    }
}