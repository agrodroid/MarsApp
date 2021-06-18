package com.example.marsrealestate.models
import com.squareup.moshi.Json

data class Mars(
    val id: String,
    @Json(name = "img_src") val imgSrcUrl: String,
    val type: String,
    val price: Double
)

enum class ResultsFilter(val filterBy: String){
    BUY("buy"),
    RENT("rent"),
    ALL("all")
}