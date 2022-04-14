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


enum class Buttons {
    ONE, TWO, THREE
}

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var bindingActivity: ActivityMainBinding
    private var colorForButtonOne: Int? = null
    private var colorForButtonTwo: Int? = null
    private var colorForButtonThree: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingActivity = DataBindingUtil.setContentView(this, R.layout.activity_main)
        bindingActivity.setVariable(BR.onColorClick, this)

        initButtons()
        setColorListener()
    }

    private fun initButtons() {
        colorForButtonOne = ContextCompat.getColor(this, R.color.teal)
        colorForButtonTwo = ContextCompat.getColor(this, R.color.green)
        colorForButtonThree = ContextCompat.getColor(this, R.color.orange)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.color_one_button -> {
                setCheckedColorOnButton(view)
                bindingActivity.colorTwoButton.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.gray
                    )
                )
                bindingActivity.colorThreeButton.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.gray
                    )
                )
                setCheckedButtons(Buttons.ONE)
            }
            R.id.color_two_button -> {
                setCheckedColorOnButton(view)
                bindingActivity.colorOneButton.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.gray
                    )
                )
                bindingActivity.colorThreeButton.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.gray
                    )
                )
                setCheckedButtons(Buttons.TWO)
            }
            R.id.color_three_button -> {
                setCheckedColorOnButton(view)
                bindingActivity.colorOneButton.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.gray
                    )
                )
                bindingActivity.colorTwoButton.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.gray
                    )
                )
                setCheckedButtons(Buttons.THREE)
            }
        }
    }

    private fun setCheckedColorOnButton(view: View){
        view.setBackgroundColor(ContextCompat.getColor(this, R.color.light_gray))
    }

    private fun setColorListener() {
        val seekBar: GradientSeekBar = bindingActivity.gradientBar
        bindingActivity.colorWheel.colorChangeListener = { rgb: Int ->
            with(seekBar) {
                startColor = rgb
                endColor = rgb.alpha.plus(100)
                thumbColor = this.currentColorAlpha
            }
            changeSelectedColor(getHexColorFrom(rgb))
        }
    }

    private fun getHexColorFrom(rgb: Int): String {
        return String.format("#%02x%02x%02x", rgb.red, rgb.green, rgb.blue)
    }

    private fun setCheckedButtons(button: Buttons) {
        when (button) {
            Buttons.ONE -> {
                bindingActivity.colorTwoButton.isChecked = false
                bindingActivity.colorThreeButton.isChecked = false
                bindingActivity.colorOneButton.isChecked = true
                colorForButtonOne?.let { setColorWheelColor(it) }
            }
            Buttons.TWO -> {
                bindingActivity.colorOneButton.isChecked = false
                bindingActivity.colorThreeButton.isChecked = false
                bindingActivity.colorTwoButton.isChecked = true
                colorForButtonTwo?.let { setColorWheelColor(it) }
            }
            Buttons.THREE -> {
                bindingActivity.colorOneButton.isChecked = false
                bindingActivity.colorTwoButton.isChecked = false
                bindingActivity.colorThreeButton.isChecked = true
                colorForButtonThree?.let { setColorWheelColor(it) }
            }
        }
    }

    private fun setColorWheelColor(color: Int) {
        bindingActivity.colorWheel.rgb = Color.rgb(color.red, color.green, color.blue)
    }

    private fun changeSelectedColor(hex: String) {
        when {
            bindingActivity.colorOneButton.isChecked -> {
                bindingActivity.imgTeal.setColorFilter(
                    Color.parseColor(hex),
                    PorterDuff.Mode.SRC_ATOP
                )
                colorForButtonOne = Color.parseColor(hex)
            }
            bindingActivity.colorTwoButton.isChecked -> {
                bindingActivity.imgGreen.setColorFilter(
                    Color.parseColor(hex),
                    PorterDuff.Mode.SRC_ATOP
                )
                colorForButtonTwo = Color.parseColor(hex)
            }
            bindingActivity.colorThreeButton.isChecked -> {
                bindingActivity.imgOrange.setColorFilter(
                    Color.parseColor(hex),
                    PorterDuff.Mode.SRC_ATOP
                )
                colorForButtonThree = Color.parseColor(hex)
            }
        }
    }
}