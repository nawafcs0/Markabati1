package com.mycode.carservice.model

data class GooglePlaces(
    val html_attributions: List<Any>,
    val next_page_token: String,
    val results: List<Result>,
    val status: String
)
