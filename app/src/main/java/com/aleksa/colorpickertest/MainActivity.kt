package com.aleksa.colorpickertest

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.databinding.DataBindingUtil
import com.aleksa.colorpickertest.databinding.ActivityMainBinding
import com.apandroid.colorwheel.gradientseekbar.GradientSeekBar
import com.apandroid.colorwheel.gradientseekbar.currentColorAlpha


enum class Colors {
    TEAL, GREEN, ORANGE
}

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var bindingActivity: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingActivity = DataBindingUtil.setContentView(this, R.layout.activity_main)
        bindingActivity.setVariable(BR.onColorClick, this)

        setColorListener()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.color_teal_button -> {
                view.setBackgroundColor(ContextCompat.getColor(this, R.color.light_gray))
                bindingActivity.colorGreenButton.setBackgroundColor(ContextCompat.getColor(this, R.color.gray))
                bindingActivity.colorOrangeButton.setBackgroundColor(ContextCompat.getColor(this, R.color.gray))
                setCheckedButtons(Colors.TEAL)
            }
            R.id.color_green_button -> {
                view.setBackgroundColor(ContextCompat.getColor(this, R.color.light_gray))
                bindingActivity.colorTealButton.setBackgroundColor(ContextCompat.getColor(this, R.color.gray))
                bindingActivity.colorOrangeButton.setBackgroundColor(ContextCompat.getColor(this, R.color.gray))
                setCheckedButtons(Colors.GREEN)
            }
            R.id.color_orange_button -> {
                view.setBackgroundColor(ContextCompat.getColor(this, R.color.light_gray))
                bindingActivity.colorTealButton.setBackgroundColor(ContextCompat.getColor(this, R.color.gray))
                bindingActivity.colorGreenButton.setBackgroundColor(ContextCompat.getColor(this, R.color.gray))
                setCheckedButtons(Colors.ORANGE)
            }
        }
    }

    private fun setColorListener() {
        val seekBar: GradientSeekBar = bindingActivity.gradientBar
        bindingActivity.colorWheel.colorChangeListener = { rgb: Int ->
            with(seekBar){
                startColor = rgb
                endColor = rgb.alpha.plus(100)
                thumbColor = this.currentColorAlpha
            }
            changeSelectedColor(getHexColorFrom(rgb))
        }
    }

    private fun getHexColorFrom(rgb: Int) : String{
        return String.format("#%02x%02x%02x", rgb.red, rgb.green, rgb.blue)
    }

    private fun setCheckedButtons(color: Colors) {
        when (color) {
            Colors.TEAL -> {
                bindingActivity.colorGreenButton.isChecked = false
                bindingActivity.colorOrangeButton.isChecked = false
                bindingActivity.colorTealButton.isChecked = true
                setColorWheelColor(ContextCompat.getColor(this, R.color.teal))
            }
            Colors.GREEN -> {
                bindingActivity.colorTealButton.isChecked = false
                bindingActivity.colorOrangeButton.isChecked = false
                bindingActivity.colorGreenButton.isChecked = true
                setColorWheelColor(ContextCompat.getColor(this, R.color.green))
            }
            Colors.ORANGE -> {
                bindingActivity.colorTealButton.isChecked = false
                bindingActivity.colorGreenButton.isChecked = false
                bindingActivity.colorOrangeButton.isChecked = true
                setColorWheelColor(ContextCompat.getColor(this, R.color.orange))
            }
        }
    }

    private fun setColorWheelColor(color: Int) {
        bindingActivity.colorWheel.rgb = Color.rgb(color.red, color.green, color.blue)
    }

    private fun changeSelectedColor(hex: String) {
        when {
            bindingActivity.colorTealButton.isChecked -> {
                bindingActivity.imgTeal.setColorFilter(Color.parseColor(hex), PorterDuff.Mode.SRC_ATOP)
            }
            bindingActivity.colorGreenButton.isChecked -> {
                bindingActivity.imgGreen.setColorFilter(Color.parseColor(hex), PorterDuff.Mode.SRC_ATOP)
            }
            bindingActivity.colorOrangeButton.isChecked -> {
                bindingActivity.imgOrange.setColorFilter(Color.parseColor(hex), PorterDuff.Mode.SRC_ATOP)
            }
        }
    }
}