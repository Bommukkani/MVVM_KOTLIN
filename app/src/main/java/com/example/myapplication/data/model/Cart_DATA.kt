package com.example.myapplication.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Cart_DATA {
    constructor(service: List<Datum>?, employee: List<Datum>?) {
        this.service = service
        this.employee = employee
    }

    var service: List<Datum>? = null
    var employee: List<Datum>? = null


}