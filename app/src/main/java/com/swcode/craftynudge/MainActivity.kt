package com.swcode.craftynudge

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
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
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val colourChooser = ColourChooser()
    private val layerChooser = LayerChooser()

    private val job =  SupervisorJob()
    private val ioScope by lazy { CoroutineScope(job + Dispatchers.IO) }

    private var downloadedWord: String = ""

    private var focalPoints: List<String> = listOf("Image", "Quote", "Image and Quote", "Your Choice", "None")

    private var buttonCount = 2
    private var textViewLayersCount = 1
    private var buttonList: List<Button> = listOf()
    private var textViewLayersList: List<TextView> = listOf()

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

        wordDownloader()
        binding.buttonThemeChooser.setOnClickListener {
            binding.textViewTheme.text = downloadedWord.replaceFirstChar { it.uppercase() }
            wordDownloader()
        }

        binding.buttonFocalChooser.setOnClickListener {
            binding.textViewFocalPoint.text = chooseFocalPoint()
        }

        binding.buttonChooseLayer1.setOnClickListener {
            binding.textViewLayer1.text = layerChooser.chooseLayer()
        }

        binding.ivAddLayers.setOnClickListener {
//            val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//            val rowView: View = inflater.inflate(R.layout.layer_getter, null)
//            binding.llLayerList!!.addView(rowView, binding.llLayerList!!.childCount)
            val textMargin = resources.getDimension(R.dimen.text_margin)
            val tvLayoutParamSet = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            tvLayoutParamSet.setMargins(textMargin.toInt())

            val buttonMargin = resources.getDimension(R.dimen.button_layer_chooser_margin)
            val buttonHorizontalPadding = resources.getDimension(R.dimen.button_horizontal_padding).toInt()
            val buttonVerticalPadding = resources.getDimension(R.dimen.button_vertical_padding).toInt()
            val buttonLayoutParamSet = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            buttonLayoutParamSet.setMargins(buttonMargin.toInt())

            //create new button
            var button = Button(this)
            button.id = "$buttonCount".toInt() + 1
            button.text = "get layer $buttonCount"
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.primary_pink))
            button.setTextColor(ContextCompat.getColor(this, R.color.white))
            button.setPadding(buttonHorizontalPadding,buttonVerticalPadding,buttonHorizontalPadding,buttonVerticalPadding)
            button.layoutParams = buttonLayoutParamSet

//            buttonList.toMutableList().add(button)
//            binding.llLayerButtons.addView(button)

            //create new text view
            var textView = TextView(this)
            textView.id = "$textViewLayersCount".toInt() + 1
            textView.textSize = 22F
            textView.layoutParams = tvLayoutParamSet

//            textViewLayersList.toMutableList().add(textView)
//            binding.llLayerTextViews.addView(textView)

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

//            binding.llLayerButtons.addView(button)
//            binding.llLayerTextViews.addView(textViewll)
            binding.llLayerList.addView(linearLayout)

            buttonCount++
            textViewLayersCount++
        }
    }

    private fun wordDownloader() {
        var result = ""
        ioScope.launch {
            var connection: HttpURLConnection? = null
            try {
                val url = URL("https://random-word-api.herokuapp.com/word?number=1")
                connection = url.openConnection() as HttpURLConnection

                val httpResult: Int = connection.responseCode
                if (httpResult == HttpURLConnection.HTTP_OK) {
                    val inputStream = connection.inputStream
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    val stringBuilder = StringBuilder()
                    var line: String?
                    try {
                        while (reader.readLine().also {line = it} != null) {
                            stringBuilder.append(line + "\n")
//                            Log.i("stringbuilder line", line.toString())
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    } finally {
                        try {
                            inputStream.close()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
//                    result = stringBuilder.toString().substring(2, stringBuilder.toString().length - 3)
//                    Log.i("worddownloader result", result)
//                    downloadedWord = result
                    downloadedWord = stringBuilder.toString().substring(2, stringBuilder.toString().length - 3)
                } else {
//                    result = connection.responseMessage
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            } finally {
                connection?.disconnect()
            }
        }
    }

    private fun chooseFocalPoint(): String {
        var randomNum = Random.nextInt(0, focalPoints.size)
        return focalPoints[randomNum]
    }
}