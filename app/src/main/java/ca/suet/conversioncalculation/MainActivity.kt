package ca.suet.conversioncalculation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import ca.suet.conversioncalculation.databinding.ActivityMainBinding
import java.math.RoundingMode
import java.text.DecimalFormat

class MainActivity : AppCompatActivity(),View.OnClickListener, TextWatcher,
    AdapterView.OnItemSelectedListener {
    lateinit var binding: ActivityMainBinding

    // a class for 2 slots
    class ConversionItem(){
        var multiplier : Float = 1f
        fun updateMultiplier(newMultiplier:Float){
            multiplier = newMultiplier
        }
    }

    // initiate 2 spots for the input & output
    private val leftSlot = ConversionItem()
    private val rightSlot = ConversionItem()
    private var input :Float = 0f
    private var output :Float = 0f

    // main function
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// using binding to retrieve by id
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

// using text watcher to update the display every changes
        binding.leftEt.addTextChangedListener(this)

// 2 spinners
        val leftSpinner = binding.leftSpinner
        val rightSpinner = binding.rightSpinner

// an array for the units and a adapter for adapting array
        val distanceUnits =arrayOf("cm","meter","km","inch","feet","mile")
        val arrayAdapter = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,  distanceUnits )

// set 2 spinners to the adapter with the distance units
        leftSpinner.adapter= arrayAdapter
        rightSpinner.adapter= arrayAdapter

// listeners for spinners to update the display
        leftSpinner.setOnItemSelectedListener(this)
        rightSpinner.setOnItemSelectedListener(this)
    }

    // a function to recalculate the output and update on the display
    fun updateDisplay(){
        var outputFloat : Float = (input * leftSlot.multiplier / rightSlot.multiplier).toFloat()
        var outputString = formatNumber(outputFloat)
        binding.rightTv.text= outputString
    }

    // a function to make the output show better
    // if the result is large enough, show integer only
    // otherwise, show more decimal places
    fun formatNumber(num: Float): String {
        if (num < 1) {
            val df = DecimalFormat("#.######")
            return df.format(num)
        }
        if (num<10) {
            val df = DecimalFormat("#.##")
            return df.format(num)
        }else{
            return num.toInt().toString()
        }
    }

    // get the updated input from the edit text and update the display
    override fun afterTextChanged(p0: Editable?) {
        Log.i("changeText","changing left text")

        val inputString = binding.leftEt.text.toString()

        if (!inputString.isNullOrBlank()){
            input = inputString.toFloat()
        }

        updateDisplay()
    }
    // get the updated unit from the spinners and update the display
    override fun onItemSelected(p0: AdapterView<*>?, p1: View? , p2: Int, p3: Long) {

        Log.i("selected", p0?.getItemAtPosition(p2).toString())

// using when to get the updated choice and corresponding multiplier
        var newSelectedUnit: Float =  when(p0?.getItemAtPosition(p2).toString()) {
            "cm" -> 1f
            "meter" -> 100f
            "km" -> 1000f
            "inch" -> 2.54f
            "feet" -> 30.48f
            "mile" -> 160934.4f
            else -> {1f}
        }

// to get which spinner is the user updating, set the new value to that multiplier
        when (p0?.id) {
            R.id.left_spinner-> leftSlot.updateMultiplier(newSelectedUnit)
            R.id.right_spinner-> rightSlot.updateMultiplier(newSelectedUnit)
            else -> Log.e("onClick", "Something went wrong")
        }

// finally, update the display
        updateDisplay()
    }


    // Not using the below functions
    override fun onNothingSelected(p0: AdapterView<*>?) {
//TODO("Not yet implemented")
    }

    override fun onClick(p0: View?) {
//TODO("Not yet implemented")
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//TODO("Not yet implemented")
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//TODO("Not yet implemented")
    }
}