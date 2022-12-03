package com.swcode.craftynudge

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.setMargins
import com.swcode.craftynudge.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val colourChooser = ColourChooser()
    private val layerChooser = LayerChooser()
    private val focalPointChooser = FocalPoint()
    private val themeChooser = Theme()

    private var buttonCount = 2
    private var textViewLayersCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonChooseColour.setOnClickListener {
            val chosenColour = colourChooser.chooseColour()
            binding.textViewChosenColour.text = "#$chosenColour"
            binding.imageViewColourBox.setBackgroundColor(Color.parseColor("#$chosenColour"))
        }

        binding.buttonChooseColourRel.setOnClickListener {
            binding.textViewChosenRel.text = colourChooser.chooseColourRelationship()
        }

        binding.buttonThemeChooser.setOnClickListener {
            binding.textViewTheme.text = themeChooser.getTheme()
        }

        binding.buttonFocalChooser.setOnClickListener {
            binding.textViewFocalPoint.text = focalPointChooser.chooseFocalPoint()
        }

        binding.buttonChooseLayer1.setOnClickListener {
            binding.textViewLayer1.text = layerChooser.chooseLayer()
        }

        binding.ivAddLayers.setOnClickListener {
            val textMargin = resources.getDimension(R.dimen.text_margin)
            val tvLayoutParamSet = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            tvLayoutParamSet.setMargins(textMargin.toInt())

            val buttonMargin = resources.getDimension(R.dimen.button_layer_chooser_margin)
            val buttonLayoutParamSet = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            buttonLayoutParamSet.setMargins(buttonMargin.toInt())

            //create new button
            var button = Button(this)
            button.id = "$buttonCount".toInt() + 1
            button.text = "Get Layer $buttonCount"
            button.setTextColor(ContextCompat.getColor(this, R.color.white))
            button.setBackgroundResource(R.drawable.button_round_corners)
            button.textSize = 16F
            button.layoutParams = buttonLayoutParamSet

            //create new text view
            var textView = TextView(this)
            textView.id = "$textViewLayersCount".toInt() + 1
            textView.textSize = 22F
            textView.layoutParams = tvLayoutParamSet

            button.setOnClickListener {
                textView.text = layerChooser.chooseLayer()
            }

            //linear layout to hold new button
            var buttonll = LinearLayout(this)
            buttonll.orientation = LinearLayout.VERTICAL
            buttonll.gravity = 1
            buttonll.layoutParams = LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1F
            )

            //linear layout to hold new text view
            var textViewll = LinearLayout(this)
            textViewll.orientation = LinearLayout.VERTICAL
            textViewll.gravity = 3
            textViewll.layoutParams = LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1F
            )

            //linear layout to hold two linear layouts
            var linearLayout = LinearLayout(this)
            linearLayout.orientation = LinearLayout.HORIZONTAL
            linearLayout.weightSum = 2F
            linearLayout.gravity = 11
            linearLayout.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            buttonll.addView(button)
            linearLayout.addView(buttonll)

            textViewll.addView((textView))
            linearLayout.addView(textViewll)

            binding.llLayerList.addView(linearLayout)

            buttonCount++
            textViewLayersCount++
        }
    }
}