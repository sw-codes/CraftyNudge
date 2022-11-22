package com.swcode.craftynudge

import kotlin.random.Random

class FocalPoint {
    private var focalPoints: List<String> = listOf("Image", "Quote", "Image and Quote", "Your Choice", "None")

    fun chooseFocalPoint(): String {
        var randomNum = Random.nextInt(0, focalPoints.size)
        return focalPoints[randomNum]
    }
}