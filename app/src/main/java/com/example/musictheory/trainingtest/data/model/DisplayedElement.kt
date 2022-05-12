package com.example.musictheory.trainingtest.data.model

import com.google.gson.annotations.SerializedName

data class DisplayedElement(
    @SerializedName("line_number_vertical")
    var lineNumVertical: Float = 0f,
    @SerializedName("sign_type")
    var signType: String = "",
    @SerializedName("horizontal_position")
    var horizontalPosition: String = ""
)