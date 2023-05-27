package com.pnguyen121.tippy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
//    Declare member variables, etBaseAmount is the ID name I set.
//    Declare all of the variables that will be changing
    private lateinit var etBaseAmount: EditText
    private lateinit var seekBarTip: SeekBar
    private lateinit var tvTipPercent: TextView
    private lateinit var tvTipAmount: TextView
    private lateinit var tvTotalAmount: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        All the private lateinit var above are these below
        etBaseAmount = findViewById(R.id.etBaseAmount)
        seekBarTip = findViewById(R.id.seekBarTip)
        tvTipPercent = findViewById(R.id.tvTipPercent)
        tvTipAmount = findViewById(R.id.tvTipAmount)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)

//        Add a listener to seekbar to know when it is being changed
        seekBarTip.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                Add a log statement, defined TAG at the top using a val
                Log.i(TAG, "onProgressChanged $progress")
//                Set the tvTipPercent text too whatever the progress number is after moving the seek bar
                tvTipPercent.text = "$progress%"
            }

//            Not using so leave empty
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
//            Not using so leave empty
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })
    }
}