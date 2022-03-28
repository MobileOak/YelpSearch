package com.mobileoak.yelpsearch.network

import com.google.gson.annotations.SerializedName

class SearchResponseObject {
    @SerializedName("total")
    var totalResults: Int = 0
    @SerializedName("businesses")
    var listOfBusinesses = ArrayList<Business>()
}

class Business {
    @SerializedName("rating")
    var rating: Float = 0f
    @SerializedName("name")
    var name: String = ""
    @SerializedName("image_url")
    var imageUrl: String = ""
}