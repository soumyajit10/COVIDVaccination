package com.example.covidvaccination

 data class CenterRVModal (
     val centerName :String,
     val centerAddress :String,
     val centerFromTime : String,
     val CenterToTime : String,
     val fee_type :String,
     val age_limit :Int,
     var vaccineName : String,
     val availableCapacity :Int

     )