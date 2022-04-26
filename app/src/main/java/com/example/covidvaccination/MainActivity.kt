package com.example.covidvaccination

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.VoiceInteractor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var pinCodeET :EditText
    private  lateinit var searchBtn : Button
    lateinit var centerRecyclerView :RecyclerView
    lateinit var  progress:ProgressBar
    lateinit var centerList : List<CenterRVModal>
    lateinit var  centerRvAdapter: CenterRvAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pinCodeET = findViewById(R.id.idEditTextPinCode)
        searchBtn = findViewById(R.id.idBtnSearch)
        progress = findViewById(R.id.idProgress)
        centerRecyclerView = findViewById(R.id.idRecyclerCenters)
        centerList = ArrayList<CenterRVModal>()


        searchBtn.setOnClickListener {
            val pinCode = pinCodeET.text.toString()
            if (pinCode.length != 6){
                Toast.makeText(this,"Enter a Valid PinCode",Toast.LENGTH_SHORT).show()
            }else{
                (centerList as ArrayList<CenterRVModal>).clear()
                val  c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)
                val datePicker = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                  // progress.visibility = View.VISIBLE
                    progress.setVisibility(View.VISIBLE)
                    val dateStr :String = """$dayOfMonth-${month+1}-$year"""
                    getAppointmentDetails(pinCode,dateStr)

                },year,month,day
                )
                datePicker.show()
            }
        }
    }

    private fun getAppointmentDetails(pinCode:String,date :String){
        val url = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByPin?pincode="+pinCode+"&date="+date
        val queue =Volley.newRequestQueue(this)
        val request = JsonObjectRequest(Request.Method.GET,url,null,{
            response ->
            progress.visibility = View.GONE
            try {
                val centerArray =  response.getJSONArray("centers")
                if (centerArray.length().equals(0)){
                    Toast.makeText(this,"No Vaccination Center Found on this date",Toast.LENGTH_SHORT).show()
                }
                for (i in 0 until centerArray.length()){
                    val centerObject = centerArray.getJSONObject(i)
                    val centerName = centerObject.getString("name")
                    val centerLocation = centerObject.getString("address")
                    val centerFromTime = centerObject.getString("from")
                    val centerToTime = centerObject.getString("to")
                    val feeType = centerObject.getString("fee_type")
                    val sessionObject =centerObject.getJSONArray("sessions").getJSONObject(0)
                    val availableCapacity : Int = sessionObject.getInt("available_capacity")
                    val ageLimit : Int = sessionObject.getInt("min_age_limit")
                    val vaccineName : String = sessionObject.getString("vaccine")

                    val center = CenterRVModal(centerName,centerLocation,centerFromTime,centerToTime,feeType,ageLimit,vaccineName,availableCapacity)
                    centerList = centerList + center


                }
                centerRvAdapter = CenterRvAdapter(centerList)
                centerRecyclerView.layoutManager = LinearLayoutManager(this)
                centerRecyclerView.adapter = centerRvAdapter

            }catch (e:JSONException){
                progress.visibility = View.GONE
                e.printStackTrace()
            }

        },
            {

              error->
                progress.visibility = View.GONE
              Toast.makeText(this,"Failed to get data",Toast.LENGTH_SHORT).show()
        })
        queue.add(request)

    }
}