package com.aleksa.colorpickertest

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.databinding.DataBindingUtil
import com.aleksa.colorpickertest.databinding.ActivityMainBinding
import com.apandroid.colorwheel.ColorWheel
import com.apandroid.colorwheel.gradientseekbar.GradientSeekBar
import com.apandroid.colorwheel.gradientseekbar.currentColorAlpha


enum class Colors {
    TEAL, GREEN, ORANGE
}

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var bindingActivity: ActivityMainBinding
    private lateinit var colorWheel: ColorWheel
    private lateinit var gradientSeekBar: GradientSeekBar
    private lateinit var colorTeal: RadioButton
    private lateinit var colorGreen: RadioButton
    private lateinit var colorOrange: RadioButton
    private lateinit var imgTeal: ImageView
    private lateinit var imgGreen: ImageView
    private lateinit var imgOrange: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingActivity = DataBindingUtil.setContentView(this, R.layout.activity_main)
        bindingActivity.setVariable(BR.onColorClick, this)

        initViews()
        setColorListener()
    }

    private fun initViews() {
        colorWheel = findViewById(R.id.colorWheel)
        gradientSeekBar = findViewById(R.id.gradient_bar)
        colorTeal = findViewById(R.id.radioTeal)
        colorGreen = findViewById(R.id.radioGreen)
        colorOrange = findViewById(R.id.radioOrange)
        imgTeal = findViewById(R.id.img_teal)
        imgGreen = findViewById(R.id.img_green)
        imgOrange = findViewById(R.id.img_orange)
    }

    private fun setColorListener() {
        colorWheel.colorChangeListener = { rgb: Int ->
            gradientSeekBar.startColor = rgb
            gradientSeekBar.endColor = rgb.alpha.plus(100)
            gradientSeekBar.thumbColor = gradientSeekBar.currentColorAlpha
            val hex = String.format("#%02x%02x%02x", rgb.red, rgb.green, rgb.blue)
            changeSelectedColor(hex)
        }
    }

    private fun changeSelectedColor(hex: String) {
        when {
            colorTeal.isChecked -> {
                imgTeal.setColorFilter(Color.parseColor(hex), PorterDuff.Mode.SRC_ATOP)
            }
            colorGreen.isChecked -> {
                imgGreen.setColorFilter(Color.parseColor(hex), PorterDuff.Mode.SRC_ATOP)
            }
            colorOrange.isChecked -> {
                imgOrange.setColorFilter(Color.parseColor(hex), PorterDuff.Mode.SRC_ATOP)
            }
        }
    }

    private fun setCheckedButtons(color: Colors) {
        when (color) {
            Colors.TEAL -> {
                colorGreen.isChecked = false
                colorOrange.isChecked = false
                colorGreen.setBackgroundColor(ContextCompat.getColor(this, R.color.gray))
                colorOrange.setBackgroundColor(ContextCompat.getColor(this, R.color.gray))
                colorTeal.isChecked = true
            }
            Colors.GREEN -> {
                colorTeal.isChecked = false
                colorOrange.isChecked = false
                colorTeal.setBackgroundColor(ContextCompat.getColor(this, R.color.gray))
                colorOrange.setBackgroundColor(ContextCompat.getColor(this, R.color.gray))
                colorGreen.isChecked = true
            }
            Colors.ORANGE -> {
                colorTeal.isChecked = false
                colorGreen.isChecked = false
                colorTeal.setBackgroundColor(ContextCompat.getColor(this, R.color.gray))
                colorGreen.setBackgroundColor(ContextCompat.getColor(this, R.color.gray))
                colorOrange.isChecked = true
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.radioTeal -> {
                colorWheel.rgb = Color.rgb(0, 194, 163)
                view.setBackgroundColor(ContextCompat.getColor(this, R.color.light_gray))
                setCheckedButtons(Colors.TEAL)
            }
            R.id.radioGreen -> {
                colorWheel.rgb = Color.rgb(75, 165, 79)
                view.setBackgroundColor(ContextCompat.getColor(this, R.color.light_gray))
                setCheckedButtons(Colors.GREEN)
            }
            R.id.radioOrange -> {
                colorWheel.rgb = Color.rgb(255, 97, 0)
                view.setBackgroundColor(ContextCompat.getColor(this, R.color.light_gray))
                setCheckedButtons(Colors.ORANGE)
            }
        }
    }
}