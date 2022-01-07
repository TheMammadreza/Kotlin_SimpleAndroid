package com.mammadreza.simpleandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import java.lang.NumberFormatException

private const val INIT_PERCENT = 13

class MainActivity : AppCompatActivity() {
    private lateinit var etBase: EditText
    private lateinit var sbPercent: SeekBar
    private lateinit var tvPercentCaption: TextView
    private lateinit var tvTip: TextView
    private lateinit var tvSum: TextView

    private fun init() {
        etBase = findViewById(R.id.etBase)
        sbPercent = findViewById(R.id.sbPercent)
        tvPercentCaption = findViewById(R.id.tvPercentCaption)
        tvTip = findViewById(R.id.tvTip)
        tvSum = findViewById(R.id.tvSum)

        sbPercent.progress = INIT_PERCENT
        tvPercentCaption.text = getPercent(sbPercent.progress)
    }
    fun getPercent(value: Int): String {
        return "$value%"
    }
    private fun getCurrency(value: Double): String {
        return "%.2f".format(value)
    }
    private fun calcTipAndSum() {
        val baseValue = try {
            etBase.text.toString().toDouble()
        }
        catch (e: NumberFormatException) {
            0.0
        }
        val tipPercent = sbPercent.progress
        val tipValue = baseValue * tipPercent / 100
        val sumValue = baseValue + tipValue

        tvTip.text = getCurrency(tipValue)
        tvSum.text = getCurrency(sumValue)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        calcTipAndSum()

        sbPercent.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                tvPercentCaption.text = getPercent(p1)

                calcTipAndSum()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        etBase.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                calcTipAndSum()
            }

        })
    }
}