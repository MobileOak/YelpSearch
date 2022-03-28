package com.mobileoak.yelpsearch.model

import com.mobileoak.yelpsearch.network.Business
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BusinessRepository @Inject constructor() {
    var listOfBusinesses = emptyList<Business>()
}