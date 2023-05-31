package com.pnguyen121.tippy

import android.animation.ArgbEvaluator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat

// Create const TAG to use with LOGS
private const val TAG = "MainActivity"
// Create const to show the initial tip percent
private const val INITIAL_TIP_PERCENT = 20
class MainActivity : AppCompatActivity() {
//    Declare member variables, etBaseAmount is the ID name I set.
//    Declare all of the variables that will be changing
    private lateinit var etBaseAmount: EditText
    private lateinit var seekBarTip: SeekBar
    private lateinit var tvTipPercent: TextView
    private lateinit var tvTipAmount: TextView
    private lateinit var tvTotalAmount: TextView
    private lateinit var tvTipDescription: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        All the private lateinit var above are these below
        etBaseAmount = findViewById(R.id.etBaseAmount)
        seekBarTip = findViewById(R.id.seekBarTip)
        tvTipPercent = findViewById(R.id.tvTipPercent)
        tvTipAmount = findViewById(R.id.tvTipAmount)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)
        tvTipDescription = findViewById(R.id.tvTipDescription)


//      Set the initial tip amount before any changes
//      moves the bar to 20
        seekBarTip.progress = INITIAL_TIP_PERCENT
//      Update percent shown to match
        tvTipPercent.text = "$INITIAL_TIP_PERCENT%"
//      Set initial tip description
        tvTipDescription.text = "Good"

//        Add a listener to SEEKBAR to know when it is being changed
//        We have to override methods that android studio helps with object is red then we go to ...
//        red light bulb and hit implement members and use members we need
        seekBarTip.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                Add a log statement, defined TAG at the top using a val
                Log.i(TAG, "onProgressChanged $progress")
//                Set the tvTipPercent text too whatever the progress number is after moving the seek bar
                tvTipPercent.text = "$progress%"
//                run the function everytime the seekBar is changed to update UI as its moving
                computeTipAndTotal()
//                Update the Tip Description text based on what the tip rating and color change using WHEN and RANGE
                val tipDescription = when (progress){
                    in 0..9 -> "Poor"
                    in 10..14 -> "Eh"
                    in 15..20 -> "Good"
                    in 20..25 -> "Great"
                    else -> "Amazing"
                }

//              set the text to whatever hits in the WHEN
                tvTipDescription.text = tipDescription
//              Need to update the color based on the tipPercent, uses math and RGB to change color
//               based on integer
                val color = ArgbEvaluator().evaluate(
                    progress.toFloat() / seekBarTip.max,
                    ContextCompat.getColor(this@MainActivity, R.color.color_worst_tip),
                    ContextCompat.getColor(this@MainActivity, R.color.color_best_tip),
                ) as Int
                tvTipDescription.setTextColor(color)
            }

//            Not using so leave empty
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
//            Not using so leave empty
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })

//        BASE AMOUNT Text Edit listener to know when its changed, TextWatcher
//        We have to override methods that android studio helps with object is red then we go to ...
//        red light bulb and hit implement members and use members we need
        etBaseAmount.addTextChangedListener(object: TextWatcher {
//            Not Using
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//            Not Using
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
//             Log Info to see if its registering, s is what user is typing and is a the parameter
                Log.i(TAG, "After Text Changed, Text Changing $s")

//              ComputeTipAndTotal Function Down Below, called after text is changed to update
                computeTipAndTotal()
            }

        })


    }

//    FUNCTION to do the math and update the UI
    private fun computeTipAndTotal() {
//    App crashes if you back space to no amount so need to do an if check
//    if base amount has is empty set total and tip amount to nothing and return
//    Return hits so the rest of the function wont be executed which caused the crash
        if(etBaseAmount.text.isEmpty()){
            tvTotalAmount.text = ""
            tvTipAmount.text = ""
            return
        }
//     1. Get the value of the base and tip percent so we can do math
        val baseAmount = etBaseAmount.text.toString().toDouble()
        val tipPercent = seekBarTip.progress
//     2. Do math on the tip and total
        val tipAmount = baseAmount * tipPercent / 100
        val totalAmount = baseAmount + tipAmount
//     3. Showcase the tip and total on the UI
//        %.2f is setting the format of the output we want, we only want 2 decimals
        tvTipAmount.text = "%.2f".format(tipAmount)
        tvTotalAmount.text = "%.2f".format(totalAmount)

    }
}